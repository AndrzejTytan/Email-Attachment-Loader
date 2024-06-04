package pl.danwys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.danwys.entity.ItemTimeSerie;
import pl.danwys.repository.ItemDetailRepository;
import pl.danwys.repository.ItemTimeSerieRepository;

import java.util.List;

@Controller
@RequestMapping("/items/time-series")
public class ItemTimeSerieController {
    private final ItemDetailRepository itemDetailRepository;
    private final ItemTimeSerieRepository itemTimeSerieRepository;

    public ItemTimeSerieController(ItemTimeSerieRepository itemTimeSerieRepository, ItemDetailRepository itemDetailRepository) {
        this.itemTimeSerieRepository = itemTimeSerieRepository;
        this.itemDetailRepository = itemDetailRepository;
    }

    @GetMapping("/{id}")
    public String showItemWithTimeSeries(@PathVariable Long id, Model model) {
        model.addAttribute("item", itemDetailRepository.findById(id));
        List<ItemTimeSerie> timeSeries = itemTimeSerieRepository.getAllByItemDetailIdOrderByPriceDateDesc(id);
        model.addAttribute("timeSeries", timeSeries);
        return "items/time-series";
    }
}
