package com.in28minutes.res.webservices.restfulwebservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonVersioningController {
	
	//URI VERSIONING
	
	@GetMapping("/v1/person")
	public PersonV1 personV1() {
		return new PersonV1("test");
	}

	@GetMapping("/v2/person")
	public PersonV2 personV2() {
		return new PersonV2(new Name("test", "tester"));
	}
	
	//Request param VERSIONING
	
	// Example http://localhost:8080/person/param?version=1
	@GetMapping(value="/person/param", params="version=1")
	public PersonV1 paramV1() {
		return new PersonV1("test");
	}

	@GetMapping(value="/person/param", params="version=2")
	public PersonV2 paramV2() {
		return new PersonV2(new Name("test", "tester"));
	}
	
	//Header param VERSIONING
	
	// Example http://localhost:8080/person/header -> header flag: X-API-VERSION - 1
	@GetMapping(value="/person/header", headers="X-API-VERSION=1")
	public PersonV1 headerV1() {
		return new PersonV1("test");
	}

	@GetMapping(value="/person/header", headers="X-API-VERSION=2")
	public PersonV2 headerV2() {
		return new PersonV2(new Name("test", "tester"));
	}
	
	//Produces/Accept header VERSIONING
	
	// Example http://localhost:8080/person/produces -> header flag: Accept - application/vnd.company.app-v1+json
	@GetMapping(value="/person/produces", produces="application/vnd.company.app-v1+json")
	public PersonV1 producesV1() {
		return new PersonV1("test");
	}

	@GetMapping(value="/person/produces", produces="application/vnd.company.app-v2+json")
	public PersonV2 producesV2() {
		return new PersonV2(new Name("test", "tester"));
	}
	
}
