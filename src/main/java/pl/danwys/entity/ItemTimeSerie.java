package pl.danwys.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "item_time_series")
public class ItemTimeSerie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_detail_id")
    private ItemDetail itemDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "processed_time_series_email_id")
    private ProcessedTimeSeriesEmail processedTimeSeriesEmail;

    @Column(name = "price_value")
    private BigDecimal priceValue;

    @Column(name = "price_date")
    private LocalDate priceDate;

    public ItemTimeSerie(ItemDetail itemDetail, ProcessedTimeSeriesEmail processedTimeSeriesEmail, BigDecimal priceValue, LocalDate priceDate) {
        this.itemDetail = itemDetail;
        this.processedTimeSeriesEmail = processedTimeSeriesEmail;
        this.priceValue = priceValue;
        this.priceDate = priceDate;
    }

    public ItemTimeSerie() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ItemDetail getItemDetail() {
        return itemDetail;
    }

    public void setItemDetail(ItemDetail itemDetail) {
        this.itemDetail = itemDetail;
    }

    public ProcessedTimeSeriesEmail getProcessedTimeSeriesEmail() {
        return processedTimeSeriesEmail;
    }

    public void setProcessedTimeSeriesEmail(ProcessedTimeSeriesEmail processedTimeSeriesEmail) {
        this.processedTimeSeriesEmail = processedTimeSeriesEmail;
    }

    public BigDecimal getPriceValue() {
        return priceValue;
    }

    public void setPriceValue(BigDecimal priceValue) {
        this.priceValue = priceValue;
    }

    public LocalDate getPriceDate() {
        return priceDate;
    }

    public void setPriceDate(LocalDate priceDate) {
        this.priceDate = priceDate;
    }

}