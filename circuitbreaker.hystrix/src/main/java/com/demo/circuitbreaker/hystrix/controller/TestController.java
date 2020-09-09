package com.demo.circuitbreaker.hystrix.controller;

import com.demo.circuitbreaker.hystrix.core.component.proxy.PersonServiceClient;
import com.demo.commons.models.Person;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    private final PersonServiceClient personService;

    public TestController(PersonServiceClient personService) {
        this.personService = personService;
    }

    @PostMapping
    public String testMethod(@RequestBody final Person person) {
        return this.personService.getPersonByName(person.getName()).getName();
    }

}
