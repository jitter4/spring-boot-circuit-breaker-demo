package com.demo.circuitbreaker.resillence4j.controller;

import com.demo.circuitbreaker.resillence4j.core.component.proxy.PersonServiceProxy;
import com.demo.commons.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/test")
public class Test2Controller {

    private final PersonServiceProxy personServiceProxy;

    @Autowired
    public Test2Controller(final PersonServiceProxy personServiceProxy) {
        this.personServiceProxy = personServiceProxy;
    }

    @PostMapping
    public Mono<Person> test2Person(@RequestBody final Person person) {
        return this.personServiceProxy.getPersonByName(person.getName());
    }

}
