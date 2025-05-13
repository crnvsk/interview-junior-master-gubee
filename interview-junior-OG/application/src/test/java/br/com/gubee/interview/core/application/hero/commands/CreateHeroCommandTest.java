package br.com.gubee.interview.core.application.hero.commands;

import br.com.gubee.interview.core.domain.Hero;
import br.com.gubee.interview.core.domain.PowerStats;
import br.com.gubee.interview.core.application.helpers.HeroTestDataBuilder;
import br.com.gubee.interview.core.application.helpers.PowerStatsTestDataBuilder;
import br.com.gubee.interview.core.application.testdoubles.CreatePowerStatsCommandStub;
import br.com.gubee.interview.core.application.testdoubles.HeroRepositoryStub;
import br.com.gubee.interview.core.application.testdoubles.PowerStatsRepositoryStub;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class CreateHeroCommandTest {

        @Test
        void shouldCreateHeroSuccessfully() {
                HeroRepositoryStub heroRepositoryStub = new HeroRepositoryStub();
                PowerStatsRepositoryStub powerStatsRepositoryStub = new PowerStatsRepositoryStub();

                CreatePowerStatsCommandStub createPowerStatsCommandStub = new CreatePowerStatsCommandStub(
                                powerStatsRepositoryStub);

                CreateHeroCommand createHeroCommand = new CreateHeroCommand(heroRepositoryStub,
                                createPowerStatsCommandStub);

                PowerStats powerStats = new PowerStatsTestDataBuilder()
                                .withStrength(10)
                                .withAgility(8)
                                .withDexterity(7)
                                .withIntelligence(9)
                                .build();

                Hero hero = new HeroTestDataBuilder()
                                .withName("Superman")
                                .build();

                UUID heroId = createHeroCommand.execute(hero, powerStats);

                assertNotNull(heroId);
                assertNotNull(heroRepositoryStub.findById(heroId).orElse(null));
        }
}