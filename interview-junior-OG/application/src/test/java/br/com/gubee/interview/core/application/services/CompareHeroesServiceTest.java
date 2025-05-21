package br.com.gubee.interview.core.application.services;

import br.com.gubee.interview.core.application.stubs.InMemoryHeroRepository;
import br.com.gubee.interview.core.application.stubs.InMemoryPowerstatsRepository;
import br.com.gubee.interview.core.domain.enums.Race;
import br.com.gubee.interview.core.domain.hero.Hero;
import br.com.gubee.interview.core.domain.hero.HeroComparisonResponseDTO;
import br.com.gubee.interview.core.domain.powerstats.Powerstats;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CompareHeroesServiceTest {
    private InMemoryHeroRepository heroRepository;
    private InMemoryPowerstatsRepository powerstatsRepository;
    private CompareHeroesService compareHeroesService;

    @BeforeEach
    void setUp() {
        heroRepository = new InMemoryHeroRepository();
        powerstatsRepository = new InMemoryPowerstatsRepository();
        compareHeroesService = new CompareHeroesService(heroRepository, powerstatsRepository);
    }

    @Test
    void shouldCompareTwoHeroesAndReturnCorrectDifferences() {
        UUID hero1Id = UUID.randomUUID();
        UUID hero2Id = UUID.randomUUID();
        UUID stats1Id = UUID.randomUUID();
        UUID stats2Id = UUID.randomUUID();
        Instant now = Instant.now();

        Powerstats stats1 = new Powerstats(stats1Id, 10, 9, 8, 7, now, now);
        Powerstats stats2 = new Powerstats(stats2Id, 6, 7, 8, 9, now, now);

        powerstatsRepository.save(stats1);
        powerstatsRepository.save(stats2);

        Hero hero1 = new Hero(hero1Id, "Hero1", Race.HUMAN, stats1Id, now, now, true);
        Hero hero2 = new Hero(hero2Id, "Hero2", Race.HUMAN, stats2Id, now, now, true);

        heroRepository.save(hero1);
        heroRepository.save(hero2);

        HeroComparisonResponseDTO result = compareHeroesService.compare(hero1Id, hero2Id);

        assertEquals(hero1Id, result.hero1Id());
        assertEquals(hero2Id, result.hero2Id());
        assertEquals(4, result.strengthDifference());
        assertEquals(2, result.agilityDifference());
        assertEquals(0, result.dexterityDifference());
        assertEquals(-2, result.intelligenceDifference());
    }
}
