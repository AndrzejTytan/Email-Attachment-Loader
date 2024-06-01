package pl.danwys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.danwys.entity.TimeSeriesSupplier;
import pl.danwys.repository.TimeSeriesSupplierRepository;

@Controller
@RequestMapping("/admin/suppliers")
public class TimeSeriesSupplierAdminController {
    private final TimeSeriesSupplierRepository timeSeriesSupplierRepository;

    public TimeSeriesSupplierAdminController(TimeSeriesSupplierRepository timeSeriesSupplierRepository) {
        this.timeSeriesSupplierRepository = timeSeriesSupplierRepository;
    }

    @GetMapping
    public String showAllSuppliers(Model model) {
        model.addAttribute("suppliers", timeSeriesSupplierRepository.findAll());
        return "/admin/suppliers";
    }

    @GetMapping("/delete/{id}")
    public String deleteSupplier(@PathVariable Long id) {
        timeSeriesSupplierRepository.deleteById(id);
        return "redirect:/admin/suppliers";
    }

    @GetMapping("/form")
    public String showFormNew(Model model) {
        model.addAttribute("timeSeriesSupplier", new TimeSeriesSupplier());
        return "/admin/suppliers-form";
    }

    @GetMapping("/form/{id}")
    public String showFormEdit(@PathVariable Long id, Model model) {
        model.addAttribute("timeSeriesSupplier", timeSeriesSupplierRepository.findById(id));
        return "/admin/suppliers-form";
    }

    @PostMapping("/form")
    public String handleFromNew(TimeSeriesSupplier timeSeriesSupplier) {
        timeSeriesSupplierRepository.save(timeSeriesSupplier);
        return "redirect:/admin/suppliers";
    }

    @PostMapping("/form/{id}")
    public String handleFromEdit(TimeSeriesSupplier timeSeriesSupplier) {
        timeSeriesSupplierRepository.save(timeSeriesSupplier);
        return "redirect:/admin/suppliers";
    }
}
