package pl.danwys.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MailboxScheduler {
    private final MailboxConnector gmailConnector;

    public MailboxScheduler(GmailConnector gmailConnector) {
        this.gmailConnector = gmailConnector;
    }

    @Scheduled(fixedDelay = 3000000)
    public void ScanMailbox() {
        gmailConnector.loadAttachmentsContents();
    }
}
