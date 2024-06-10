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

import java.security.GeneralSecurityException;
import java.util.*;

@Service
public class GmailConnector implements MailboxConnector {
    private final CsvLoader csvLoader;
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final List<String> SCOPES = Collections.singletonList(GmailScopes.MAIL_GOOGLE_COM);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    public GmailConnector(CsvLoader csvLoader) throws GeneralSecurityException, IOException {
        this.csvLoader = csvLoader;
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
            // TODO
        } catch (IOException e) {
            // TODO
        }
    }

    // Creates an authorized Credential object.
    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        try (InputStream in = GmailConnector.class.getResourceAsStream(CREDENTIALS_FILE_PATH)) {
            if (in == null) {
                throw new FileNotFoundException("Credentials not found: " + CREDENTIALS_FILE_PATH);
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
            throw new RuntimeException(e); //TODO
        }
    }

    private void ExtractFromMessages(Gmail gmailService, List<Message> inboxMessagesIds) {
        for (Message messageId : inboxMessagesIds) {
            // retrieves actual message with contents
            Message messageContents = getMessageContents(gmailService, messageId.getId());

            LocalDateTime receivedDateGMT = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(messageContents.getInternalDate()),
                    ZoneId.of("Etc/GMT")
            );

            String sender = getSender(messageContents.getPayload().getHeaders());
            // TODO check if exists in db and pass further

            List<MessagePart> messageParts = messageContents
                    .getPayload()
                    .getParts();

            processMessageParts(gmailService, messageParts, receivedDateGMT, sender);
        }
    }

    private Message getMessageContents(Gmail gmailService, String messageId) {
        try {
            return gmailService
                    .users()
                    .messages()
                    .get("me", messageId)
                    .execute();
        } catch (IOException e) {
            throw new RuntimeException(e); //TODO
        }
    }

    private String getSender(List<MessagePartHeader> headers) {
        String sender = null;
        for (MessagePartHeader header : headers) {
            if (header.getName().equals("From")) {
                sender = header.getValue();
                // sender values are like "Google <google@no-reply.com> - extracting email address
                sender = sender.substring(sender.indexOf("<") + 1, sender.indexOf(">"));
                break;
            }
        }
        return sender;
    }

    private void processMessageParts(Gmail gmailService, List<MessagePart> messageParts,
                                     LocalDateTime dateReceived, String sender) {
        for (MessagePart messagePart : messageParts) {
            String attachmentName = messagePart.getFilename();
            if (attachmentName == null) continue;
            boolean hasAllowedFileExtension = attachmentName.endsWith(".csv"); // TODO change to 'list of allowed extensions'
            if (hasAllowedFileExtension) {
                try {
                    MessagePartBody attachmentPart = getMessageAttachmentContents(gmailService, messagePart);
                    String attachmentContentsBase64 = attachmentPart.getData();
                    byte[] attachmentContentsByteArray = Base64.getDecoder().decode(attachmentContentsBase64);
                    if (attachmentName.endsWith(".csv")) { // TODO change to strategy pattern for easier code extension later?
                        csvLoader.parse(attachmentContentsByteArray, dateReceived, sender);
                    }
                } catch (IOException e) {
                    System.out.println("IO Exception processing attachment: "); // TODO
                }
            }
        }
    }

    private MessagePartBody getMessageAttachmentContents(Gmail gmailService, MessagePart messagePart) throws IOException {
        String messagePartId = messagePart.getPartId();
        String messageAttachmentId = messagePart.getBody().getAttachmentId();
        return gmailService
                .users()
                .messages()
                .attachments()
                .get("me", messagePartId, messageAttachmentId)
                .execute();
    }
}
