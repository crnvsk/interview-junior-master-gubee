package br.com.gubee.interview.core.adapters.web.controllers;

import br.com.gubee.interview.core.adapters.web.dtos.CreateHeroRequest;
import br.com.gubee.interview.core.adapters.web.dtos.HeroResponse;
import br.com.gubee.interview.core.domain.enums.Race;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = br.com.gubee.interview.core.infrastructure.Application.class)
class HeroControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        restTemplate.delete("/api/v1/heroes");
    }

    @Test
    void shouldCreateAndRetrieveHero() {
        CreateHeroRequest createHeroRequest = CreateHeroRequest.builder()
                .name("Superman")
                .race(Race.ALIEN)
                .strength(10)
                .agility(8)
                .dexterity(7)
                .intelligence(9)
                .build();

        ResponseEntity<Void> createResponse = restTemplate.postForEntity("/api/v1/heroes", createHeroRequest,
                Void.class);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        String location = createResponse.getHeaders().getLocation().toString();
        assertThat(location).isNotNull();

        ResponseEntity<HeroResponse> getResponse = restTemplate.getForEntity(location, HeroResponse.class);

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        HeroResponse heroResponse = getResponse.getBody();
        assertThat(heroResponse).isNotNull();
        assertThat(heroResponse.getName()).isEqualTo("Superman");
        assertThat(heroResponse.getRace()).isEqualTo(Race.ALIEN);
    }

    @Test
    void shouldFindHeroesByName() {
        CreateHeroRequest hero1 = CreateHeroRequest.builder()
                .name("Superman")
                .race(Race.ALIEN)
                .strength(10)
                .agility(8)
                .dexterity(7)
                .intelligence(9)
                .build();

        CreateHeroRequest hero2 = CreateHeroRequest.builder()
                .name("Supergirl")
                .race(Race.HUMAN)
                .strength(8)
                .agility(9)
                .dexterity(6)
                .intelligence(8)
                .build();

        restTemplate.postForEntity("/api/v1/heroes", hero1, Void.class);
        restTemplate.postForEntity("/api/v1/heroes", hero2, Void.class);

        ResponseEntity<List<HeroResponse>> response = restTemplate.exchange(
                "/api/v1/heroes?name=Super",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<HeroResponse>>() {});

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<?> heroes = response.getBody();
        assertThat(heroes).hasSize(2);
    }

    @Test
    void shouldDeleteHero() {
        CreateHeroRequest createHeroRequest = CreateHeroRequest.builder()
                .name("Batman")
                .race(Race.HUMAN)
                .strength(7)
                .agility(6)
                .dexterity(8)
                .intelligence(9)
                .build();

        ResponseEntity<Void> createResponse = restTemplate.postForEntity("/api/v1/heroes", createHeroRequest,
                Void.class);
        String location = createResponse.getHeaders().getLocation().toString();

        restTemplate.delete(location);

        ResponseEntity<HeroResponse> getResponse = restTemplate.getForEntity(location, HeroResponse.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}