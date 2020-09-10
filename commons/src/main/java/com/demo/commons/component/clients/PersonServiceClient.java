package com.demo.commons.component.clients;

import com.demo.commons.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PersonServiceClient {

    private final RestTemplate restTemplate;

    @Autowired
    public PersonServiceClient(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Person getPeronByName(final String name) {
        var headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        var url = "http://localhost:5001/person";

        var builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("name", name);

        var entity = new HttpEntity<>(headers);
        var response = restTemplate.exchange(
                builder.toUriString(),
                org.springframework.http.HttpMethod.GET,
                entity,
                Person.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Person Service exception");
        }

        return response.getBody();
    }

}
