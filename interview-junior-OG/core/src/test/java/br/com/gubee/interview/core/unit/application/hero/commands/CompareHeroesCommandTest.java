package br.com.gubee.interview.core.unit.application.hero.commands;

import br.com.gubee.interview.core.application.hero.commands.CompareHeroesCommand;
import br.com.gubee.interview.core.domain.Hero;
import br.com.gubee.interview.core.domain.PowerStats;
import br.com.gubee.interview.core.unit.helpers.HeroTestDataBuilder;
import br.com.gubee.interview.core.unit.helpers.PowerStatsTestDataBuilder;
import br.com.gubee.interview.core.unit.stubs.HeroRepositoryStub;
import br.com.gubee.interview.core.unit.stubs.PowerStatsRepositoryStub;
import br.com.gubee.interview.core.unit.stubs.FindPowerStatsByIdQueryStub;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompareHeroesCommandTest {

    @Test
    void shouldCompareHeroesSuccessfully() {
        HeroRepositoryStub heroRepositoryStub = new HeroRepositoryStub();
        PowerStatsRepositoryStub powerStatsRepositoryStub = new PowerStatsRepositoryStub();
        FindPowerStatsByIdQueryStub findPowerStatsByIdQueryStub = new FindPowerStatsByIdQueryStub(powerStatsRepositoryStub);

        CompareHeroesCommand compareHeroesCommand = new CompareHeroesCommand(heroRepositoryStub, findPowerStatsByIdQueryStub);

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

        assertEquals(2, comparison.get("strengthDifference"));
        assertEquals(1, comparison.get("agilityDifference"));
    }
}