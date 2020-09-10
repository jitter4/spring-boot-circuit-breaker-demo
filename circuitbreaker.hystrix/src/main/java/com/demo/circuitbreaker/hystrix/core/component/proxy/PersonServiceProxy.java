package com.demo.circuitbreaker.hystrix.core.component.proxy;

import com.demo.commons.component.clients.PersonServiceClient;
import com.demo.commons.models.Person;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PersonServiceProxy {

    private final PersonServiceClient personServiceClient;

    @Autowired
    public PersonServiceProxy(final PersonServiceClient personServiceClient) {
        this.personServiceClient = personServiceClient;
    }

    @HystrixCommand(fallbackMethod = "fallbackPerson")
    public Person getPersonByName(final String name) {
        return personServiceClient.getPeronByName(name);
    }

    public Person fallbackPerson(final String name) {
        return Person.builder().name("Fallback " + name).build();
    }

}
