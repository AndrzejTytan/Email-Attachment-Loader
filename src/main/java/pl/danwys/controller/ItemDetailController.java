package pl.danwys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.danwys.repository.ItemDetailRepository;

@Controller
@RequestMapping("/items")
public class ItemDetailController {
    private final ItemDetailRepository itemDetailRepository;

    public ItemDetailController(ItemDetailRepository itemDetailRepository) {
        this.itemDetailRepository = itemDetailRepository;
    }

    @GetMapping
    public String showAllItems(Model model) {
        model.addAttribute("items", itemDetailRepository.findAll());
        return "/items/items";
    }
}
