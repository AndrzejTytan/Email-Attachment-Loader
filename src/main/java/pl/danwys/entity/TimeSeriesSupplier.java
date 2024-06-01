package pl.danwys.entity;

import javax.persistence.*;

@Entity
@Table(name = "time_series_suppliers")
public class TimeSeriesSupplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "file_column_index_identifier_code")
    private int fileColumnIndexIdentifierCode;

    @Column(name = "file_column_index_price_date")
    private int fileColumnIndexPriceDate;

    @Column(name = "file_column_index_price_value")
    private int fileColumnIndexPriceValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getFileColumnIndexIdentifierCode() {
        return fileColumnIndexIdentifierCode;
    }

    public void setFileColumnIndexIdentifierCode(int fileColumnIndexIdentifierCode) {
        this.fileColumnIndexIdentifierCode = fileColumnIndexIdentifierCode;
    }

    public int getFileColumnIndexPriceDate() {
        return fileColumnIndexPriceDate;
    }

    public void setFileColumnIndexPriceDate(int fileColumnIndexPriceDate) {
        this.fileColumnIndexPriceDate = fileColumnIndexPriceDate;
    }

    public int getFileColumnIndexPriceValue() {
        return fileColumnIndexPriceValue;
    }

    public void setFileColumnIndexPriceValue(int fileColumnIndexPriceValue) {
        this.fileColumnIndexPriceValue = fileColumnIndexPriceValue;
    }
}
