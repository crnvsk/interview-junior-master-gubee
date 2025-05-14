package br.com.gubee.interview.core.application.powerstats.commands;

import br.com.gubee.interview.core.domain.PowerStats;
import br.com.gubee.interview.core.application.helpers.PowerStatsTestDataBuilder;
import br.com.gubee.interview.core.application.testdoubles.PowerStatsRepositoryStub;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CreatePowerStatsCommandTest {

    private PowerStatsRepositoryStub powerStatsRepositoryStub;
    private CreatePowerStatsCommand createPowerStatsCommand;

    @BeforeEach
    void setUp() {
        powerStatsRepositoryStub = new PowerStatsRepositoryStub();
        createPowerStatsCommand = new CreatePowerStatsCommand(powerStatsRepositoryStub);
    }

    @Test
    void shouldCreatePowerStatsSuccessfully() {
        PowerStats powerStats = new PowerStatsTestDataBuilder()
                .withStrength(10)
                .withAgility(8)
                .withDexterity(7)
                .withIntelligence(9)
                .build();

        UUID powerStatsId = createPowerStatsCommand.execute(powerStats);

        assertNotNull(powerStatsId, "Expected PowerStats ID to be generated");
        assertNotNull(powerStatsRepositoryStub.findById(powerStatsId).orElse(null),
                "Expected PowerStats to be saved in the repository");
    }

    @Test
    void shouldThrowExceptionWhenPowerStatsIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            createPowerStatsCommand.execute(null);
        });

        assertEquals("PowerStats cannot be null", exception.getMessage(),
                "Expected exception message to indicate null PowerStats");
    }
}