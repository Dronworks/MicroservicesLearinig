package com.dronworks.microservices.limitsservice.controller;

import com.dronworks.microservices.limitsservice.bean.Limits;
import com.dronworks.microservices.limitsservice.configuration.LimitConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitController {
	
	@Autowired
	private LimitConfiguration configuration;
	
	@GetMapping("/limits")
	public Limits exposeLimits() {
//		return new Limits(1, 1000);
		return new Limits(configuration.getMinimum(), configuration.getMaximum());
	}

}
