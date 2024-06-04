package pl.danwys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.danwys.repository.ProcessedTimeSeriesEmailRepository;

@Controller
@RequestMapping("/admin/sources")
public class ProcessedTimeSeriesEmailAdminController {
    private final ProcessedTimeSeriesEmailRepository processedTimeSeriesEmailRepository;

    public ProcessedTimeSeriesEmailAdminController(ProcessedTimeSeriesEmailRepository processedTimeSeriesEmailRepository) {
        this.processedTimeSeriesEmailRepository = processedTimeSeriesEmailRepository;
    }

    @GetMapping
    public String showAllEmails(Model model) {
        model.addAttribute("emails", processedTimeSeriesEmailRepository.findAll());
        return "/admin/emails";
    }
}
