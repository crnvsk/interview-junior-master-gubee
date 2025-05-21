package br.com.gubee.interview.core.infrastructure.intergration;

import br.com.gubee.interview.core.domain.enums.Race;
import br.com.gubee.interview.core.domain.hero.HeroRequestDTO;
import br.com.gubee.interview.core.domain.powerstats.PowerstatsDTO;
import com.jayway.jsonpath.JsonPath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class HeroApiIT {

	@LocalServerPort
	int port;

	@Autowired
	private WebTestClient webTestClient;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@BeforeEach
	void cleanDatabase() {
		jdbcTemplate.execute("DELETE FROM hero");
		jdbcTemplate.execute("DELETE FROM power_stats");
	}

	@Test
	void shouldCreateHero() {
		PowerstatsDTO powerstats = new PowerstatsDTO(10, 8, 9, 7);
		HeroRequestDTO request = new HeroRequestDTO(
				"Integration Hero",
				Race.HUMAN,
				powerstats,
				true);

		webTestClient.post()
				.uri("/api/v1/heroes")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(request)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$.id").isNotEmpty()
				.jsonPath("$.name").isEqualTo("Integration Hero")
				.jsonPath("$.race").isEqualTo("HUMAN")
				.jsonPath("$.enabled").isEqualTo(true)
				.jsonPath("$.powerStats.strength").isEqualTo(10)
				.jsonPath("$.powerStats.agility").isEqualTo(8)
				.jsonPath("$.powerStats.dexterity").isEqualTo(9)
				.jsonPath("$.powerStats.intelligence").isEqualTo(7);
	}

	@Test
	void shouldGetHeroById() {
		PowerstatsDTO powerstats = new PowerstatsDTO(5, 6, 7, 8);
		HeroRequestDTO request = new HeroRequestDTO(
				"GetById Hero",
				Race.HUMAN,
				powerstats,
				true);

		byte[] responseBody = webTestClient.post()
				.uri("/api/v1/heroes")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(request)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.returnResult()
				.getResponseBodyContent();

		assert responseBody != null;
		String heroId = JsonPath.read(new String(responseBody), "$.id").toString();

		webTestClient.get()
				.uri("/api/v1/heroes/{id}", heroId)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$.id").isEqualTo(heroId)
				.jsonPath("$.name").isEqualTo("GetById Hero");
	}

	@Test
	void shouldGetHeroByName() {
		PowerstatsDTO powerstats = new PowerstatsDTO(10, 2, 3, 4);
		HeroRequestDTO request = new HeroRequestDTO(
				"GetByName Hero",
				Race.HUMAN,
				powerstats,
				true);

		webTestClient.post()
				.uri("/api/v1/heroes")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(request)
				.exchange()
				.expectStatus().isOk();

		webTestClient.get()
				.uri(uriBuilder -> uriBuilder
						.path("/api/v1/heroes/name/{name}")
						.build("GetByName Hero"))
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$.name").isEqualTo("GetByName Hero");
	}

	@Test
	void shouldUpdateHero() {
		PowerstatsDTO powerstats = new PowerstatsDTO(1, 2, 3, 4);
		HeroRequestDTO request = new HeroRequestDTO(
				"Update Hero",
				Race.HUMAN,
				powerstats,
				true);

		byte[] responseBody = webTestClient.post()
				.uri("/api/v1/heroes")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(request)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.returnResult()
				.getResponseBodyContent();

		assert responseBody != null;
		String heroId = JsonPath.read(new String(responseBody), "$.id").toString();

		HeroRequestDTO updateRequest = new HeroRequestDTO(
				"Updated Hero",
				Race.HUMAN,
				new PowerstatsDTO(10, 4, 5, 6),
				false);

		webTestClient.put()
				.uri("/api/v1/heroes/{id}", heroId)
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(updateRequest)
				.exchange()
				.expectStatus().isNoContent();

		webTestClient.get()
				.uri("/api/v1/heroes/{id}", heroId)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$.name").isEqualTo("Updated Hero")
				.jsonPath("$.enabled").isEqualTo(false)
				.jsonPath("$.powerStats.strength").isEqualTo(10);
	}

	@Test
	void shouldDeleteHero() {
		PowerstatsDTO powerstats = new PowerstatsDTO(5, 6, 7, 8);
		HeroRequestDTO request = new HeroRequestDTO(
				"Delete Hero",
				Race.HUMAN,
				powerstats,
				true);

		byte[] responseBody = webTestClient.post()
				.uri("/api/v1/heroes")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(request)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.returnResult()
				.getResponseBodyContent();

		assert responseBody != null;
		String heroId = JsonPath.read(new String(responseBody), "$.id").toString();

		webTestClient.delete()
				.uri("/api/v1/heroes/{id}", heroId)
				.exchange()
				.expectStatus().isNoContent();

		webTestClient.get()
				.uri("/api/v1/heroes/{id}", heroId)
				.exchange()
				.expectStatus().isNotFound();
	}

	@Test
	void shouldCompareHeroes() {
		PowerstatsDTO powerstats1 = new PowerstatsDTO(10, 2, 3, 4);
		HeroRequestDTO hero1 = new HeroRequestDTO(
				"Hero One",
				Race.HUMAN,
				powerstats1,
				true);

		byte[] responseBody1 = webTestClient.post()
				.uri("/api/v1/heroes")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(hero1)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.returnResult()
				.getResponseBodyContent();

		assert responseBody1 != null;
		String hero1Id = JsonPath.read(new String(responseBody1), "$.id").toString();

		PowerstatsDTO powerstats2 = new PowerstatsDTO(5, 1, 6, 8);
		HeroRequestDTO hero2 = new HeroRequestDTO(
				"Hero Two",
				Race.HUMAN,
				powerstats2,
				true);

		byte[] responseBody2 = webTestClient.post()
				.uri("/api/v1/heroes")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(hero2)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.returnResult()
				.getResponseBodyContent();

		assert responseBody2 != null;
		String hero2Id = JsonPath.read(new String(responseBody2), "$.id").toString();

		webTestClient.get()
				.uri(uriBuilder -> uriBuilder
						.path("/api/v1/heroes/compare")
						.queryParam("hero1", hero1Id)
						.queryParam("hero2", hero2Id)
						.build())
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$.hero1Id").isEqualTo(hero1Id)
				.jsonPath("$.hero2Id").isEqualTo(hero2Id)
				.jsonPath("$.strengthDifference").isEqualTo(5)
				.jsonPath("$.agilityDifference").isEqualTo(1)
				.jsonPath("$.dexterityDifference").isEqualTo(-3)
				.jsonPath("$.intelligenceDifference").isEqualTo(-4);
	}
}