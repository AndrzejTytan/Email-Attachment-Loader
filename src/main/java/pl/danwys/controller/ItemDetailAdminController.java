package pl.danwys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.danwys.entity.ItemDetail;
import pl.danwys.repository.ItemDetailRepository;
import pl.danwys.repository.ItemTimeSerieRepository;

@Controller
@RequestMapping("/admin/items")
public class ItemDetailAdminController {
    private final ItemDetailRepository itemDetailRepository;
    private final ItemTimeSerieRepository itemTimeSerieRepository;

    public ItemDetailAdminController(ItemDetailRepository itemDetailRepository, ItemTimeSerieRepository itemTimeSerieRepository) {
        this.itemDetailRepository = itemDetailRepository;
        this.itemTimeSerieRepository = itemTimeSerieRepository;
    }

    @GetMapping
    public String showAllItems(Model model) {
        model.addAttribute("items", itemDetailRepository.findAll());
        return "/admin/items";
    }


    // TODO time series data to be deleted first due to foreign key constraint?
    @GetMapping("/delete/{id}")
    @Transactional
    public String deleteItem(@PathVariable Long id) {
        itemTimeSerieRepository.deleteByItemDetail_Id(id);
        itemDetailRepository.deleteById(id);
        return "redirect:/admin/items";
    }


    @GetMapping("/form")
    public String showFormNew(Model model) {
        model.addAttribute("itemDetail", new ItemDetail());
        return "/admin/items-form";
    }


    @GetMapping("/form/{id}")
    public String showFormEdit(@PathVariable Long id, Model model) {
        model.addAttribute("itemDetail", itemDetailRepository.findById(id));
        return "/admin/items-form";
    }


    @PostMapping("/form")
    public String handleFromNew(ItemDetail itemDetail) {
        itemDetailRepository.save(itemDetail);
        return "redirect:/admin/items";
    }

    @PostMapping("/form/{id}")
    public String handleFromEdit(ItemDetail itemDetail) {
        itemDetailRepository.save(itemDetail);
        return "redirect:/admin/items";
    }
}
