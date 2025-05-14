package br.com.gubee.interview.core.application.hero.commands;

import br.com.gubee.interview.core.domain.Hero;
import br.com.gubee.interview.core.domain.PowerStats;
import br.com.gubee.interview.core.application.helpers.HeroTestDataBuilder;
import br.com.gubee.interview.core.application.helpers.PowerStatsTestDataBuilder;
import br.com.gubee.interview.core.application.powerstats.commands.CreatePowerStatsCommand;
import br.com.gubee.interview.core.application.testdoubles.HeroRepositoryStub;
import br.com.gubee.interview.core.application.testdoubles.PowerStatsRepositoryStub;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CreateHeroCommandTest {

        private HeroRepositoryStub heroRepositoryStub;
        private PowerStatsRepositoryStub powerStatsRepositoryStub;
        private CreatePowerStatsCommand createPowerStatsCommandStub;
        private CreateHeroCommand createHeroCommand;

        @BeforeEach
        void setUp() {
                heroRepositoryStub = new HeroRepositoryStub();
                powerStatsRepositoryStub = new PowerStatsRepositoryStub();
                createPowerStatsCommandStub = new CreatePowerStatsCommand(powerStatsRepositoryStub);
                createHeroCommand = new CreateHeroCommand(heroRepositoryStub, createPowerStatsCommandStub);
        }

        @Test
        void shouldCreateHeroSuccessfully() {
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

                assertNotNull(heroId, "Expected hero ID to be generated");
                assertNotNull(heroRepositoryStub.findById(heroId).orElse(null),
                                "Expected hero to be saved in the repository");
        }

        @Test
        void shouldThrowExceptionWhenHeroIsNull() {
                PowerStats powerStats = new PowerStatsTestDataBuilder()
                                .withStrength(10)
                                .withAgility(8)
                                .withDexterity(7)
                                .withIntelligence(9)
                                .build();

                IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                        createHeroCommand.execute(null, powerStats);
                });

                assertEquals("Hero cannot be null", exception.getMessage(),
                                "Expected exception message to indicate null hero");
        }

        @Test
        void shouldThrowExceptionWhenPowerStatsIsNull() {
                Hero hero = new HeroTestDataBuilder()
                                .withName("Superman")
                                .build();

                IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                        createHeroCommand.execute(hero, null);
                });

                assertEquals("PowerStats cannot be null", exception.getMessage(),
                                "Expected exception message to indicate null PowerStats");
        }
}