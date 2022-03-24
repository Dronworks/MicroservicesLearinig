package com.dronworks.currencyconversionservice.controller;

import com.dronworks.currencyconversionservice.bean.CurrencyConversion;
import com.dronworks.currencyconversionservice.proxy.CurrencyExchangeProxy;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@ToString
public class CurrencyConversionController {

    @Autowired
    private CurrencyExchangeProxy currencyExchangeProxy;

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversion(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
        Map<String, String> uriConfigs = new HashMap<>();
        uriConfigs.put("from", from);
        uriConfigs.put("to", to);
        try {
            ResponseEntity<CurrencyConversion> forEntity = new RestTemplate().getForEntity(
                    "http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                    CurrencyConversion.class,
                    uriConfigs);// Because are similar, use only the same fields
            CurrencyConversion currencyConversion = forEntity.getBody();
            currencyConversion.setQuantity(quantity);
            currencyConversion.setTotalCalculatedAmount(quantity.multiply(currencyConversion.getConversionMultiplier()));
            return currencyConversion;
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }


    }

    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversionProxy(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
        CurrencyConversion currencyConversion = currencyExchangeProxy.retrieveExchangeValue(from, to);
        currencyConversion.setQuantity(quantity);
        currencyConversion.setTotalCalculatedAmount(quantity.multiply(currencyConversion.getConversionMultiplier()));
        currencyConversion.setEnvironment(currencyConversion.getEnvironment() + " feign");
        return currencyConversion;
    }

}
