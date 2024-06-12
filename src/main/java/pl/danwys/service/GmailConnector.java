package pl.danwys.service;


import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.*;
import pl.danwys.entity.TimeSeriesSupplier;
import pl.danwys.repository.TimeSeriesSupplierRepository;

import java.security.GeneralSecurityException;
import java.util.*;

@Service
public class GmailConnector implements MailboxConnector {
    private final AttachmentLoader csvLoader;
    private final TimeSeriesSupplierRepository timeSeriesSupplierRepository;
    private final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    public GmailConnector(AttachmentLoader csvLoader,
                          TimeSeriesSupplierRepository timeSeriesSupplierRepository) {
        this.csvLoader = csvLoader;
        this.timeSeriesSupplierRepository = timeSeriesSupplierRepository;
    }

    @Override
    public void loadAttachmentsContents() {
        try {
            NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            Gmail gmailService = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                    .setApplicationName("Email-Attachment-Loader")
                    .build();

            // retrieves list of message and thread ids
            List<Message> inboxMessagesIds = getMessagesIds(gmailService);
            if (inboxMessagesIds.isEmpty()) return;
            ExtractFromMessages(gmailService, inboxMessagesIds);
        } catch (GeneralSecurityException e) {
            // TODO logging
        } catch (IOException e) {
            // TODO logging

        }
    }

    // Creates an authorized Credential object.
    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        String TOKENS_DIRECTORY_PATH = "tokens";
        List<String> SCOPES = Collections.singletonList(GmailScopes.MAIL_GOOGLE_COM);
        String CREDENTIALS_FILE_PATH = "/credentials.json";
        try (InputStream in = GmailConnector.class.getResourceAsStream(CREDENTIALS_FILE_PATH)) {
            if (in == null) {
                throw new FileNotFoundException("Credentials not found in: " + CREDENTIALS_FILE_PATH);
            }
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
            GoogleAuthorizationCodeFlow flow =
                    new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                            .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                            .setAccessType("offline")
                            .build();
            LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
            return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        }
    }

    private List<Message> getMessagesIds(Gmail gmailService) {
        try {
            return gmailService
                    .users()
                    .messages()
                    .list("me")
                    .setLabelIds(List.of("INBOX"))
                    .execute()
                    .getMessages();
        } catch (IOException e) {
            throw new RuntimeException(e); //TODO logging
        }
    }

    private void ExtractFromMessages(Gmail gmailService, List<Message> inboxMessagesIds) {
        Set<TimeSeriesSupplier> timeSeriesSuppliers = Set.copyOf(timeSeriesSupplierRepository.findAll());

        for (Message messageId : inboxMessagesIds) {
            // retrieves actual message with contents
            Message messageContents = getMessageContents(gmailService, messageId.getId());

            LocalDateTime receivedDateGMT = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(messageContents.getInternalDate()),
                    ZoneId.of("Etc/GMT")
            );

            String senderEmail = getSenderEmail(messageContents.getPayload().getHeaders());
            TimeSeriesSupplier timeSeriesSupplier = getTimeSeriesSupplierByEmail(timeSeriesSuppliers, senderEmail);
            if (senderEmail == null || senderEmail.isEmpty() || timeSeriesSupplier == null) {
                continue; // ignore if supplier is not approved / TODO notify admin of potential new supplier
            }

            List<MessagePart> messageParts = messageContents
                    .getPayload()
                    .getParts();

            processMessageParts(gmailService, messageParts, receivedDateGMT, timeSeriesSupplier);
            // TODO remove label INBOX, add label "Processed" to message
        }
    }

    private TimeSeriesSupplier getTimeSeriesSupplierByEmail(Collection<TimeSeriesSupplier> timeSeriesSuppliers, String email) {
        if (timeSeriesSuppliers == null || email == null || email.isEmpty()) return null;
        for (TimeSeriesSupplier timeSeriesSupplier : timeSeriesSuppliers) {
            if (timeSeriesSupplier.getEmail().equals(email)) {
                return timeSeriesSupplier;
            }
        }
        return null;
    }

    private Message getMessageContents(Gmail gmailService, String messageId) {
        try {
            return gmailService
                    .users()
                    .messages()
                    .get("me", messageId)
                    .execute();
        } catch (IOException e) {
            throw new RuntimeException(e); //TODO logging
        }
    }

    private String getSenderEmail(List<MessagePartHeader> headers) {
        String senderEmail = null;
        for (MessagePartHeader header : headers) {
            if (header.getName().equals("From")) {
                senderEmail = header.getValue();
                // sender values are like "Google <google@no-reply.com> - extracting email address
                senderEmail = senderEmail.substring(senderEmail.indexOf("<") + 1, senderEmail.indexOf(">"));
                break;
            }
        }
        return senderEmail;
    }

    private void processMessageParts(Gmail gmailService, List<MessagePart> messageParts,
                                     LocalDateTime dateReceived, TimeSeriesSupplier timeSeriesSupplier) {
        for (MessagePart messagePart : messageParts) {
            String attachmentName = messagePart.getFilename();
            if (attachmentName == null || attachmentName.isEmpty()) continue;
            boolean hasAllowedFileExtension = attachmentName.endsWith(".csv"); // TODO change to 'list of allowed extensions'
            if (!hasAllowedFileExtension) continue;
            MessagePartBody attachmentPart = getMessageAttachmentContents(gmailService, messagePart);
            String attachmentContentsBase64 = attachmentPart.getData();
            byte[] attachmentContentsByteArray = Base64.getDecoder().decode(attachmentContentsBase64);
            if (attachmentName.endsWith(".csv")) { // TODO change to strategy pattern for easier code extension later?
                csvLoader.parse(attachmentContentsByteArray, dateReceived, timeSeriesSupplier);
            }
        }
    }

    private MessagePartBody getMessageAttachmentContents(Gmail gmailService, MessagePart messagePart) {
        String messagePartId = messagePart.getPartId();
        String messageAttachmentId = messagePart.getBody().getAttachmentId();
        MessagePartBody messagePartBody = null;
        try {
            messagePartBody = gmailService
                    .users()
                    .messages()
                    .attachments()
                    .get("me", messagePartId, messageAttachmentId)
                    .execute();
        } catch (IOException e) {
            // TODO logging
        }
        return messagePartBody;
    }
}
