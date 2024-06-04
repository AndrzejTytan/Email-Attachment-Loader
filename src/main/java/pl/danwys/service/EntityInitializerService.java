package pl.danwys.service;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.danwys.entity.ItemTimeSerie;
import pl.danwys.repository.ItemTimeSerieRepository;

import java.util.List;

@Service
@Transactional
public class EntityInitializerService {
    private final ItemTimeSerieRepository itemTimeSerieRepository;

    public EntityInitializerService(ItemTimeSerieRepository itemTimeSerieRepository) {
        this.itemTimeSerieRepository = itemTimeSerieRepository;
    }

    public List<ItemTimeSerie> getInitializedTimeSeriesById(Long id) {
        List<ItemTimeSerie> timeSeries = itemTimeSerieRepository.getAllByItemDetailIdOrderByPriceDateDesc(id);
        timeSeries.forEach(ts -> Hibernate.initialize(ts.getProcessedTimeSeriesEmail()));
        return timeSeries;
    }
}
