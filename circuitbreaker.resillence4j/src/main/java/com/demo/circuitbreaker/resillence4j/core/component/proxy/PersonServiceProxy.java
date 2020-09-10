package com.demo.circuitbreaker.resillence4j.core.component.proxy;

import com.demo.commons.component.clients.PersonServiceClient;
import com.demo.commons.models.Person;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PersonServiceProxy {

    private final PersonServiceClient personServiceClient;

    @Autowired
    public PersonServiceProxy(final PersonServiceClient personServiceClient) {
        this.personServiceClient = personServiceClient;
    }

    @Retry(name = "personService", fallbackMethod = "fallbackPerson")
    @CircuitBreaker(name = "personService", fallbackMethod = "fallbackPerson")
    @RateLimiter(name = "personService")
    @TimeLimiter(name = "personService")
    @Bulkhead(name = "personService")
    public Mono<Person> getPersonByName(final String name) {
        Person person = null;
        try {
            person = personServiceClient.getPeronByName(name);
        } catch (Exception ex) {
            return Mono.error(ex.getCause());
        }
        return Mono.just(person);
    }

    public Mono<Person> fallbackPerson(String name, Throwable throwable) {
        return Mono.just(Person.builder().name("Recovered " + name).build());
    }

}