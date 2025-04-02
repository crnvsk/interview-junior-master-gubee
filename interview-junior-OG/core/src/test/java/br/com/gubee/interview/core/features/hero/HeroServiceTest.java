package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.core.features.powerstats.InMemoryPowerStatsService;
import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.enums.Race;
import br.com.gubee.interview.model.request.CreateHeroRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class HeroServiceTest {

    private HeroService heroService;
    private InMemoryHeroRepository heroRepository;
    private InMemoryPowerStatsService powerStatsService;

    @BeforeEach
    void setUp() {
        heroRepository = new InMemoryHeroRepository();
        powerStatsService = new InMemoryPowerStatsService();
        heroService = new HeroService(heroRepository, powerStatsService);
    }

    @Test
    void createHeroWithValidData() {
        CreateHeroRequest request = CreateHeroRequest.builder()
                .name("Superman")
                .race(Race.ALIEN)
                .strength(10)
                .agility(8)
                .dexterity(7)
                .intelligence(9)
                .build();

        UUID heroId = heroService.create(request);

        assertNotNull(heroId);
        Hero hero = heroRepository.findById(heroId).orElse(null);
        assertNotNull(hero);
        assertEquals("Superman", hero.getName());
        assertEquals(Race.ALIEN, hero.getRace());
    }

    @Test
    void findHeroByIdShouldReturnHero() {
        UUID heroId = heroService.create(CreateHeroRequest.builder()
                .name("Batman")
                .race(Race.HUMAN)
                .strength(8)
                .agility(7)
                .dexterity(6)
                .intelligence(9)
                .build());

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
    void findHeroesByNameShouldReturnMatchingHeroes() {
        heroService.create(CreateHeroRequest.builder()
                .name("Flash")
                .race(Race.HUMAN)
                .strength(7)
                .agility(10)
                .dexterity(8)
                .intelligence(6)
                .build());

        heroService.create(CreateHeroRequest.builder()
                .name("Flash Gordon")
                .race(Race.HUMAN)
                .strength(6)
                .agility(9)
                .dexterity(7)
                .intelligence(8)
                .build());

        List<Hero> heroes = heroService.findByName("Flash");

        assertEquals(2, heroes.size());
    }

    @Test
    void updateHeroShouldUpdateAttributes() {
        UUID heroId = heroService.create(CreateHeroRequest.builder()
                .name("Wonder Woman")
                .race(Race.HUMAN)
                .strength(9)
                .agility(8)
                .dexterity(7)
                .intelligence(8)
                .build());

        Hero updatedHero = Hero.builder()
                .id(heroId)
                .name("Wonder Woman Updated")
                .race(Race.HUMAN)
                .build();

        boolean updated = heroService.updateHero(heroId, updatedHero);

        assertTrue(updated);
        Hero hero = heroRepository.findById(heroId).orElse(null);
        assertNotNull(hero);
        assertEquals("Wonder Woman Updated", hero.getName());
    }

    @Test
    void deleteHeroShouldRemoveHero() {
        UUID heroId = heroService.create(CreateHeroRequest.builder()
                .name("Aquaman")
                .race(Race.HUMAN)
                .strength(8)
                .agility(7)
                .dexterity(6)
                .intelligence(7)
                .build());

        boolean deleted = heroService.deleteHero(heroId);

        assertTrue(deleted);
        assertTrue(heroRepository.findById(heroId).isEmpty());
    }

    @Test
    void compareHeroesShouldReturnCorrectDifferences() {
        UUID hero1Id = heroService.create(CreateHeroRequest.builder()
                .name("Hero1")
                .race(Race.HUMAN)
                .strength(8)
                .agility(7)
                .dexterity(6)
                .intelligence(9)
                .build());

        UUID hero2Id = heroService.create(CreateHeroRequest.builder()
                .name("Hero2")
                .race(Race.HUMAN)
                .strength(6)
                .agility(8)
                .dexterity(7)
                .intelligence(5)
                .build());

        Map<String, Object> result = heroService.compareHeroes(hero1Id, hero2Id);

        assertNotNull(result);
        assertEquals(2, result.get("strengthDifference"));
        assertEquals(-1, result.get("agilityDifference"));
        assertEquals(-1, result.get("dexterityDifference"));
        assertEquals(4, result.get("intelligenceDifference"));
    }
}
