package pl.danwys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.danwys.entity.ProcessedTimeSeriesEmail;

public interface ProcessedTimeSeriesEmailRepository extends JpaRepository<ProcessedTimeSeriesEmail, Long> {
}
