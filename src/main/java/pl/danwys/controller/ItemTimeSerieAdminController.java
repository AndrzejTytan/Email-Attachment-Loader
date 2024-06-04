package pl.danwys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.danwys.entity.ItemTimeSerie;
import pl.danwys.repository.ItemDetailRepository;
import pl.danwys.repository.ItemTimeSerieRepository;
import pl.danwys.service.EntityInitializerService;

import java.util.List;

@Controller
@RequestMapping("/admin/items/time-series")
public class ItemTimeSerieAdminController {
    private final ItemDetailRepository itemDetailRepository;
    private final ItemTimeSerieRepository itemTimeSerieRepository;
    private final EntityInitializerService entityInitializerService;

    public ItemTimeSerieAdminController(ItemTimeSerieRepository itemTimeSerieRepository, ItemDetailRepository itemDetailRepository, EntityInitializerService entityInitializerService) {
        this.itemTimeSerieRepository = itemTimeSerieRepository;
        this.itemDetailRepository = itemDetailRepository;
        this.entityInitializerService = entityInitializerService;
    }

    @GetMapping("/{id}")
    public String showItemWithTimeSeries(@PathVariable Long id, Model model) {
        model.addAttribute("item", itemDetailRepository.findById(id));

        // This controller and view requires lazily initialized fields, so we initialize them here
        List<ItemTimeSerie> timeSeries = entityInitializerService.getInitializedTimeSeriesById(id);
        model.addAttribute("timeSeries", timeSeries);
        return "admin/time-series";
    }
}