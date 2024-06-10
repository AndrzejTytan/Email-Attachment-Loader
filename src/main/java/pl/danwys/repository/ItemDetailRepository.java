package pl.danwys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.danwys.entity.ItemDetail;

import java.util.Optional;

public interface ItemDetailRepository extends JpaRepository<ItemDetail, Long> {
    Optional<ItemDetail> findItemDetailByIdentifierCode(String identifierCode);
}
