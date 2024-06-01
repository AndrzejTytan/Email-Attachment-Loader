package pl.danwys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.danwys.repository.TimeSeriesSupplierRepository;

@Controller
@RequestMapping("/admin/suppliers")
public class TimeSeriesSupplierController {
    private final TimeSeriesSupplierRepository timeSeriesSupplierRepository;

    public TimeSeriesSupplierController(TimeSeriesSupplierRepository timeSeriesSupplierRepository) {
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
}
