package pl.danwys.entity;

import javax.persistence.*;

@Entity
@Table(name = "item_details")
public class ItemDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "identifier_code")
    private String identifierCode;

    @Column(name = "name")
    private String name;

    @Column(name = "pricing_currency_iso")
    private String pricingCurrencyIso;

    // TODO
    // to contain a map for time series data? indexing of date, key = offset from start date as Integer?
    // osobna encja na time series
    // https://www.baeldung.com/hibernate-persisting-maps

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifierCode() {
        return identifierCode;
    }

    public void setIdentifierCode(String identifierCode) {
        this.identifierCode = identifierCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPricingCurrencyIso() {
        return pricingCurrencyIso;
    }

    public void setPricingCurrencyIso(String pricingCurrencyIso) {
        this.pricingCurrencyIso = pricingCurrencyIso;
    }

}