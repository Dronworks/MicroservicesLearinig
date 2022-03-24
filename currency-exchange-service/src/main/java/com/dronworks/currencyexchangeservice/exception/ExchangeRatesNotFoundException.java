package com.dronworks.currencyexchangeservice.exception;

public class ExchangeRatesNotFoundException extends RuntimeException {
    public ExchangeRatesNotFoundException(String message) {
        super(message);
    }
}
