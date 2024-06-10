package pl.danwys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.danwys.entity.TimeSeriesSupplier;

import java.util.Optional;

public interface TimeSeriesSupplierRepository extends JpaRepository<TimeSeriesSupplier, Long> {
    Optional<TimeSeriesSupplier> findTimeSeriesSupplierByEmail(String email);
}
