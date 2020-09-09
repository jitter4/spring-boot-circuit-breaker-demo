package com.demo.circuitbreaker.hystrix.core.component.proxy;

import com.demo.commons.models.Person;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PersonServiceClient {

    private final RestTemplate restTemplate;

    @Autowired
    public PersonServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "fallbackPerson")
    public Person getPersonByName(String name) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        String url = "http://localhost:5001/person";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("name", "shubham");

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<Person> response = restTemplate.exchange(
                builder.toUriString(),
                org.springframework.http.HttpMethod.GET,
                entity,
                Person.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Person Service Down");
        }

        return response.getBody();
    }

    public Person fallbackPerson(String name) {
        return Person.builder().name("Fallback " + name).build();
    }

}
