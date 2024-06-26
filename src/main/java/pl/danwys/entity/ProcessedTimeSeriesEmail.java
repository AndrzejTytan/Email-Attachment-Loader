package pl.danwys.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "processed_time_series_emails")
public class ProcessedTimeSeriesEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // used only in one view - no performance concerns hence EAGER
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "time_series_supplier_id")
    private TimeSeriesSupplier timeSeriesSupplier;

    @Column(name = "email_receieved")
    private LocalDateTime emailReceieved;

    @Column(name = "email_processed")
    private LocalDateTime emailProcessed;

    public ProcessedTimeSeriesEmail() {
    }

    public ProcessedTimeSeriesEmail(TimeSeriesSupplier timeSeriesSupplier, LocalDateTime emailReceieved, LocalDateTime emailProcessed) {
        this.timeSeriesSupplier = timeSeriesSupplier;
        this.emailReceieved = emailReceieved;
        this.emailProcessed = emailProcessed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TimeSeriesSupplier getTimeSeriesSupplier() {
        return timeSeriesSupplier;
    }

    public void setTimeSeriesSupplier(TimeSeriesSupplier timeSeriesSupplier) {
        this.timeSeriesSupplier = timeSeriesSupplier;
    }

    public LocalDateTime getEmailReceieved() {
        return emailReceieved;
    }

    public void setEmailReceieved(LocalDateTime emailReceieved) {
        this.emailReceieved = emailReceieved;
    }

    public LocalDateTime getEmailProcessed() {
        return emailProcessed;
    }

    public void setEmailProcessed(LocalDateTime emailProcessed) {
        this.emailProcessed = emailProcessed;
    }

}