package br.com.gubee.interview.core.application.hero.commands;

import br.com.gubee.interview.core.domain.Hero;
import br.com.gubee.interview.core.domain.PowerStats;
import br.com.gubee.interview.core.application.helpers.HeroTestDataBuilder;
import br.com.gubee.interview.core.application.helpers.PowerStatsTestDataBuilder;
import br.com.gubee.interview.core.application.powerstats.queries.FindPowerStatsByIdQuery;
import br.com.gubee.interview.core.application.testdoubles.HeroRepositoryStub;
import br.com.gubee.interview.core.application.testdoubles.PowerStatsRepositoryStub;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CompareHeroesCommandTest {

    private HeroRepositoryStub heroRepositoryStub;
    private PowerStatsRepositoryStub powerStatsRepositoryStub;
    private FindPowerStatsByIdQuery findPowerStatsByIdQueryStub;
    private CompareHeroesCommand compareHeroesCommand;

    @BeforeEach
    void setUp() {
        heroRepositoryStub = new HeroRepositoryStub();
        powerStatsRepositoryStub = new PowerStatsRepositoryStub();
        findPowerStatsByIdQueryStub = new FindPowerStatsByIdQuery(powerStatsRepositoryStub);
        compareHeroesCommand = new CompareHeroesCommand(heroRepositoryStub, findPowerStatsByIdQueryStub);
    }

    @Test
    void shouldCompareHeroesSuccessfully() {
        PowerStats stats1 = new PowerStatsTestDataBuilder()
                .withStrength(10)
                .withAgility(8)
                .withDexterity(7)
                .withIntelligence(9)
                .build();

        PowerStats stats2 = new PowerStatsTestDataBuilder()
                .withStrength(8)
                .withAgility(7)
                .withDexterity(6)
                .withIntelligence(8)
                .build();

        UUID stats1Id = powerStatsRepositoryStub.create(stats1);
        UUID stats2Id = powerStatsRepositoryStub.create(stats2);

        Hero hero1 = new HeroTestDataBuilder()
                .withName("Hero1")
                .withPowerStatsId(stats1Id)
                .build();

        Hero hero2 = new HeroTestDataBuilder()
                .withName("Hero2")
                .withPowerStatsId(stats2Id)
                .build();

        UUID hero1Id = heroRepositoryStub.create(hero1);
        UUID hero2Id = heroRepositoryStub.create(hero2);

        Map<String, Object> comparison = compareHeroesCommand.execute(hero1Id, hero2Id);

        assertEquals(2, comparison.get("strengthDifference"), "Expected strength difference to be 2");
        assertEquals(1, comparison.get("agilityDifference"), "Expected agility difference to be 1");
    }

    @Test
    void shouldThrowExceptionWhenHeroIdIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            compareHeroesCommand.execute(null, UUID.randomUUID());
        });

        assertEquals("Hero ID cannot be null", exception.getMessage(),
                "Expected exception message to indicate null ID");
    }

    @Test
    void shouldThrowExceptionWhenHeroDoesNotExist() {
        UUID nonExistentHeroId1 = UUID.randomUUID();
        UUID nonExistentHeroId2 = UUID.randomUUID();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            compareHeroesCommand.execute(nonExistentHeroId1, nonExistentHeroId2);
        });

        assertTrue(exception.getMessage().contains("Hero not found for given ID"),
                "Expected exception message to indicate missing hero");
    }

    @Test
    void shouldThrowExceptionWhenHeroHasNoPowerStats() {
        Hero heroWithoutStats = new HeroTestDataBuilder()
                .withName("HeroWithoutStats")
                .withPowerStatsId(null)
                .build();

        UUID heroWithoutStatsId = heroRepositoryStub.create(heroWithoutStats);

        Hero anotherHero = new HeroTestDataBuilder()
                .withName("AnotherHero")
                .withPowerStatsId(UUID.randomUUID())
                .build();

        UUID anotherHeroId = heroRepositoryStub.create(anotherHero);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            compareHeroesCommand.execute(heroWithoutStatsId, anotherHeroId);
        });

        assertEquals("Hero does not have PowerStats", exception.getMessage(),
                "Expected exception message to indicate missing PowerStats");
    }
}