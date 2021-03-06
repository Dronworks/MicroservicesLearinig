package com.in28minutes.res.webservices.restfulwebservices.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	
	@Autowired
	private MessageSource messageSource; // Automatically from resources messgages.properties
	
	@RequestMapping(method=RequestMethod.GET, path="/hello")
	public String helloWorld() {
		return "Hello world";
	}
	
	@GetMapping(path="/hello-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello world");
	}

	@GetMapping("/hello/path-variable/{name}")
	public HelloWorldBean HelloWroldPathVariable(@PathVariable String name) {
		return new HelloWorldBean(String.format("Hello world %s", name));
	}
	
	@GetMapping("/hello-bean-internationalized")
	public String helloWorldInternationalized(@RequestHeader(name="Accept-Language", required=false) Locale locale) {
		return messageSource.getMessage("hello.world.message", null, "Hello World Default", locale);
	}
	
	//without parameter!!!
	@GetMapping("/hello-bean-internationalized_wp")
	public String helloWorldInternationalizedWithoutParameter() {
		return messageSource.getMessage("hello.world.message", null, LocaleContextHolder.getLocale()); // default locale is english
	}
	
}
