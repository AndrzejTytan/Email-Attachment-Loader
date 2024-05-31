package pl.danwys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.danwys.entity.TimeSeriesSupplier;

public interface TimeSeriesSupplierRepository extends JpaRepository<TimeSeriesSupplier, Long> {
}
