package pl.danwys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.danwys.service.GmailConnector;
import pl.danwys.service.MailboxConnector;

@Controller
@RequestMapping("/admin")
public class ManualTriggerItemProcessingController {
    private final MailboxConnector gmailConnector;

    public ManualTriggerItemProcessingController(GmailConnector gmailConnector) {
        this.gmailConnector = gmailConnector;
    }

    @GetMapping("/trigger")
    public String triggerMailboxCheck() {
        gmailConnector.loadAttachmentsContents();
        return "redirect:/";
    }
}
