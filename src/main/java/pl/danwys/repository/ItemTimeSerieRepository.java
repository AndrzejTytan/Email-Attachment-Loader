package pl.danwys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.danwys.entity.ItemTimeSerie;

import java.util.List;

public interface ItemTimeSerieRepository extends JpaRepository<ItemTimeSerie, Long> {
    List<ItemTimeSerie> getAllByItemDetailIdOrderByPriceDateDesc(Long itemDetailId);
}
