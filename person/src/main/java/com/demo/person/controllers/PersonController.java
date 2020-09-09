package com.demo.person.controllers;

import com.demo.commons.models.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
public class PersonController {

    @GetMapping
    public Person getPersonByName(@RequestParam("name") String name) {
        return Person.builder().name(name).build();
    }

}
