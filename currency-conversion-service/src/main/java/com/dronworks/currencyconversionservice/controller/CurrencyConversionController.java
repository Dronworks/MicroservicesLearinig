package com.dronworks.currencyconversionservice.controller;

import com.dronworks.currencyconversionservice.bean.CurrencyConversion;
import lombok.ToString;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@ToString
public class CurrencyConversionController {

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversion(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
        return new CurrencyConversion(2000L, from, to, quantity,
                BigDecimal.ONE, BigDecimal.ONE, "");
    }

}
