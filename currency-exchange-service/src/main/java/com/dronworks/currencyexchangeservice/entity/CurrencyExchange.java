package com.dronworks.currencyexchangeservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@ToString
@Entity
public class CurrencyExchange {

    @Id
    private Long id;
    @Column(name="currency_from")
    private String from; //SQL will fail because if FROM is keyword
    @Column(name="currency_to")
    private String to;
    private BigDecimal conversionMultiplier;
    private String environment;

    public CurrencyExchange(Long id, String from, String to, BigDecimal conversionMultiplier) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.conversionMultiplier = conversionMultiplier;
    }

}
