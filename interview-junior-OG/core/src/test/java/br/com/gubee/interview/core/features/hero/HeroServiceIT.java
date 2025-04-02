package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.core.features.powerstats.PowerStatsService;
import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.PowerStats;
import br.com.gubee.interview.model.enums.Race;
import br.com.gubee.interview.model.request.CreateHeroRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("it")
public class HeroServiceIT {

    @Autowired
    private HeroService heroService;

    @Autowired
    private PowerStatsService powerStatsService;

    @Test
    void createHeroWithAllRequiredArguments() {
        CreateHeroRequest request = createHeroRequest();
        UUID heroId = heroService.create(request);

        assertNotNull(heroId);

        Optional<Hero> hero = heroService.findById(heroId);
        assertTrue(hero.isPresent());
        assertEquals("Batman", hero.get().getName());
        assertEquals(Race.HUMAN, hero.get().getRace());

        Optional<PowerStats> powerStats = powerStatsService.findById(hero.get().getPowerStatsId());
        assertTrue(powerStats.isPresent());
        assertEquals(6, powerStats.get().getStrength());
        assertEquals(5, powerStats.get().getAgility());
        assertEquals(8, powerStats.get().getDexterity());
        assertEquals(10, powerStats.get().getIntelligence());
    }

    @Test
    void findHeroById() {
        CreateHeroRequest request = createHeroRequest();
        UUID heroId = heroService.create(request);

        Optional<Hero> hero = heroService.findById(heroId);

        assertTrue(hero.isPresent());
        assertEquals("Batman", hero.get().getName());
    }

    @Test
    void findHeroByIdShouldReturnEmptyWhenNotFound() {
        Optional<Hero> hero = heroService.findById(UUID.randomUUID());
        assertTrue(hero.isEmpty());
    }

    @Test
    void findHeroesByName() {
        heroService.create(createHeroRequest("Flash"));
        heroService.create(createHeroRequest("Flash Gordon"));

        List<Hero> heroes = heroService.findByName("Flash");

        assertEquals(2, heroes.size());
    }

    @Test
    void findHeroesByNameShouldReturnEmptyWhenNotFound() {
        List<Hero> heroes = heroService.findByName("Unknown");
        assertTrue(heroes.isEmpty());
    }

    @Test
    void updateHero() {
        UUID heroId = heroService.create(createHeroRequest());

        Hero updatedHero = Hero.builder()
                .id(heroId)
                .name("Updated Batman")
                .race(Race.HUMAN)
                .build();

        boolean updated = heroService.updateHero(heroId, updatedHero);

        assertTrue(updated);

        Optional<Hero> hero = heroService.findById(heroId);
        assertTrue(hero.isPresent());
        assertEquals("Updated Batman", hero.get().getName());
    }

    @Test
    void updateHeroShouldReturnFalseWhenNotFound() {
        Hero updatedHero = Hero.builder()
                .id(UUID.randomUUID())
                .name("Nonexistent Hero")
                .race(Race.HUMAN)
                .build();

        boolean updated = heroService.updateHero(UUID.randomUUID(), updatedHero);

        assertFalse(updated);
    }

    @Test
    void deleteHero() {
        UUID heroId = heroService.create(createHeroRequest());

        boolean deleted = heroService.deleteHero(heroId);

        assertTrue(deleted);

        Optional<Hero> hero = heroService.findById(heroId);
        assertTrue(hero.isEmpty());
    }

    @Test
    void deleteHeroShouldReturnFalseWhenNotFound() {
        boolean deleted = heroService.deleteHero(UUID.randomUUID());
        assertFalse(deleted);
    }

    @Test
    void compareHeroes() {
        UUID hero1Id = heroService.create(createHeroRequest("Hero1", 8, 7, 6, 9));
        UUID hero2Id = heroService.create(createHeroRequest("Hero2", 6, 8, 7, 5));

        Map<String, Object> result = heroService.compareHeroes(hero1Id, hero2Id);

        assertNotNull(result);
        assertEquals(2, result.get("strengthDifference"));
        assertEquals(-1, result.get("agilityDifference"));
        assertEquals(-1, result.get("dexterityDifference"));
        assertEquals(4, result.get("intelligenceDifference"));
    }

    @Test
    void compareHeroesShouldReturnNullWhenHeroNotFound() {
        UUID hero1Id = heroService.create(createHeroRequest("Hero1", 8, 7, 6, 9));

        Map<String, Object> result = heroService.compareHeroes(hero1Id, UUID.randomUUID());

        assertNull(result);
    }

    @Test
    void compareHeroesShouldThrowExceptionWhenPowerStatsNotFound() {
        UUID hero1Id = heroService.create(createHeroRequest("Hero1", 8, 7, 6, 9));
        UUID hero2Id = heroService.create(createHeroRequest("Hero2", 6, 8, 7, 5));

        heroService.findById(hero2Id).ifPresent(hero -> powerStatsService.findById(hero.getPowerStatsId())
                .ifPresent(stats -> powerStatsService.delete(stats.getId())));

        assertThrows(NoSuchElementException.class, () -> heroService.compareHeroes(hero1Id, hero2Id));
    }

    private CreateHeroRequest createHeroRequest() {
        return createHeroRequest("Batman", 6, 5, 8, 10);
    }

    private CreateHeroRequest createHeroRequest(String name) {
        return createHeroRequest(name, 6, 5, 8, 10);
    }

    private CreateHeroRequest createHeroRequest(String name, int strength, int agility, int dexterity,
            int intelligence) {
        return CreateHeroRequest.builder()
                .name(name)
                .race(Race.HUMAN)
                .strength(strength)
                .agility(agility)
                .dexterity(dexterity)
                .intelligence(intelligence)
                .build();
    }
}