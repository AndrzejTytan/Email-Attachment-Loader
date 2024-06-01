package pl.danwys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.danwys.entity.ItemDetail;

public interface ItemDetailRepository extends JpaRepository<ItemDetail, Long> {
}
