package com.in28minutes.res.webservices.restfulwebservices.filtering;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilterController {
	
	@GetMapping("/filtering")
	public SomeBean retrieveSomeBean() {
		return new SomeBean("value1", "value2", "value3");
	}
	
	@GetMapping("/filtering-list")
	public List<SomeBean> retrieveListOfSomeBean() {
		return Arrays.asList(new SomeBean[] {new SomeBean("value1", "value2", "value3"), 
				new SomeBean("value1", "value2", "value3"),
				new SomeBean("value1", "value2", "value3")});
	}
	
	// field1, field2
	@GetMapping("/filtering2")
	public MappingJacksonValue retrieveSomeBeanD() {
		SomeBeanDynamic someBeanDynamic = new SomeBeanDynamic("value1", "value2", "value3");
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1", "field2"); 
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanDfilter", filter);
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(someBeanDynamic);
		mappingJacksonValue.setFilters(filters);
		return mappingJacksonValue;
	}
	
	// field3, field1
	@GetMapping("/filtering-list2")
	public MappingJacksonValue retrieveListOfSomeBeanD() {
		List<SomeBeanDynamic> asList = Arrays.asList(new SomeBeanDynamic[] {new SomeBeanDynamic("value1", "value2", "value3"), 
				new SomeBeanDynamic("value1", "value2", "value3"),
				new SomeBeanDynamic("value1", "value2", "value3")});
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field3", "field1"); 
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanDfilter", filter);
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(asList);
		mappingJacksonValue.setFilters(filters);
		return mappingJacksonValue;
	}
	
	

}
