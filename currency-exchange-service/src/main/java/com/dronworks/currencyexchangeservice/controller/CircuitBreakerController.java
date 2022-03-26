package com.dronworks.currencyexchangeservice.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController {

    Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @GetMapping("/sample-api")
//    @Retry(name="sample-api-prop", fallbackMethod = "hardcodedResponse")
    /*
        Starts with close state - allowing sending much of the requests.
        After a threshold open state and stopping letting the requests, returning fallback.
        After another threshold half-open state - each few seconds lets few requests to proceed. If success close state.
        If not open state.
        resillience4j.readme.io/docs/circuitbreaker - shows more options. For example threshold for long requests(configurable)
        can see configuration example in Getting Started on the left.
        Threshold in percents
     */
    @CircuitBreaker(name="default", fallbackMethod = "hardcodedResponse")
    public String sampleApi() {
        logger.info("Sample api call received");
        ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/some-dummy", String.class);

        return forEntity.getBody();
    }

    public String hardcodedResponse(Exception ex) { // Can deal with different kind of exceptions with different fallback functions
        return "fallback-response";
    }

}
