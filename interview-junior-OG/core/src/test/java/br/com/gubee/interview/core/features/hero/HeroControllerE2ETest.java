package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.enums.Race;
import br.com.gubee.interview.model.request.CreateHeroRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HeroControllerE2ETest {

    @LocalServerPort
    private int port;

    @Autowired
    private RestTemplate restTemplate;

    private String baseUrl() {
        return "http://localhost:" + port + "/api/v1/heroes";
    }

    @Test
    void createAndFindHeroById() {
        // Arrange
        CreateHeroRequest request = CreateHeroRequest.builder()
                .name("Superman")
                .race(Race.ALIEN)
                .strength(10)
                .agility(8)
                .dexterity(7)
                .intelligence(9)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateHeroRequest> entity = new HttpEntity<>(request, headers);

        // Act - Create Hero
        ResponseEntity<Void> createResponse = restTemplate.postForEntity(baseUrl(), entity, Void.class);

        // Assert - Create Hero
        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        String location = createResponse.getHeaders().getLocation().toString();
        assertNotNull(location);

        // Act - Find Hero by ID
        ResponseEntity<Hero> findResponse = restTemplate.getForEntity(location, Hero.class);

        // Assert - Find Hero by ID
        assertEquals(HttpStatus.OK, findResponse.getStatusCode());
        Hero hero = findResponse.getBody();
        assertNotNull(hero);
        assertEquals("Superman", hero.getName());
        assertEquals(Race.ALIEN, hero.getRace());
    }

    @Test
    void findHeroesByName() {
        // Arrange
        CreateHeroRequest request1 = CreateHeroRequest.builder()
                .name("Flash")
                .race(Race.HUMAN)
                .strength(6)
                .agility(10)
                .dexterity(8)
                .intelligence(7)
                .build();

        CreateHeroRequest request2 = CreateHeroRequest.builder()
                .name("Flash Gordon")
                .race(Race.HUMAN)
                .strength(7)
                .agility(9)
                .dexterity(8)
                .intelligence(6)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        restTemplate.postForEntity(baseUrl(), new HttpEntity<>(request1, headers), Void.class);
        restTemplate.postForEntity(baseUrl(), new HttpEntity<>(request2, headers), Void.class);

        // Act
        ResponseEntity<Hero[]> response = restTemplate.getForEntity(baseUrl() + "?name=Flash", Hero[].class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Hero[] heroes = response.getBody();
        assertNotNull(heroes);
        assertEquals(2, heroes.length);
    }

    @Test
    void deleteHero() {
        // Arrange
        CreateHeroRequest request = CreateHeroRequest.builder()
                .name("Batman")
                .race(Race.HUMAN)
                .strength(6)
                .agility(5)
                .dexterity(8)
                .intelligence(10)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateHeroRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<Void> createResponse = restTemplate.postForEntity(baseUrl(), entity, Void.class);
        String location = createResponse.getHeaders().getLocation().toString();

        // Act - Delete Hero
        restTemplate.delete(location);

        // Act - Verify Deletion
        ResponseEntity<Hero> findResponse = restTemplate.getForEntity(location, Hero.class);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, findResponse.getStatusCode());
    }

    @Test
    void compareHeroes() {
        // Arrange
        CreateHeroRequest hero1 = CreateHeroRequest.builder()
                .name("Hero1")
                .race(Race.HUMAN)
                .strength(8)
                .agility(7)
                .dexterity(6)
                .intelligence(9)
                .build();

        CreateHeroRequest hero2 = CreateHeroRequest.builder()
                .name("Hero2")
                .race(Race.HUMAN)
                .strength(6)
                .agility(8)
                .dexterity(7)
                .intelligence(5)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        UUID hero1Id = UUID.fromString(restTemplate.postForEntity(baseUrl(), new HttpEntity<>(hero1, headers), Void.class)
                .getHeaders().getLocation().toString().split("/")[5]);
        UUID hero2Id = UUID.fromString(restTemplate.postForEntity(baseUrl(), new HttpEntity<>(hero2, headers), Void.class)
                .getHeaders().getLocation().toString().split("/")[5]);

        // Act
        ResponseEntity<Map> response = restTemplate.postForEntity(
                baseUrl() + "/compare?hero1Id=" + hero1Id + "&hero2Id=" + hero2Id,
                null,
                Map.class
        );

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        @SuppressWarnings("unchecked")
        Map<String, Object> comparisonResult = response.getBody();
        assertNotNull(comparisonResult);
        assertEquals(2, comparisonResult.get("strengthDifference"));
        assertEquals(-1, comparisonResult.get("agilityDifference"));
        assertEquals(-1, comparisonResult.get("dexterityDifference"));
        assertEquals(4, comparisonResult.get("intelligenceDifference"));
    }
}