package br.com.gubee.interview.core.application.powerstats.commands;

import br.com.gubee.interview.core.domain.PowerStats;
import br.com.gubee.interview.core.application.helpers.PowerStatsTestDataBuilder;
import br.com.gubee.interview.core.application.testdoubles.PowerStatsRepositoryStub;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DeletePowerStatsCommandTest {

    private PowerStatsRepositoryStub powerStatsRepositoryStub;
    private DeletePowerStatsCommand deletePowerStatsCommand;

    @BeforeEach
    void setUp() {
        powerStatsRepositoryStub = new PowerStatsRepositoryStub();
        deletePowerStatsCommand = new DeletePowerStatsCommand(powerStatsRepositoryStub);
    }

    @Test
    void shouldDeletePowerStatsSuccessfully() {
        PowerStats powerStats = new PowerStatsTestDataBuilder()
                .withStrength(10)
                .withAgility(8)
                .withDexterity(7)
                .withIntelligence(9)
                .build();

        UUID powerStatsId = powerStatsRepositoryStub.create(powerStats);

        boolean result = deletePowerStatsCommand.execute(powerStatsId);

        assertTrue(result, "Expected PowerStats to be deleted successfully");
        assertTrue(powerStatsRepositoryStub.findById(powerStatsId).isEmpty(),
                "Expected PowerStats to no longer exist in the repository");
    }

    @Test
    void shouldReturnFalseWhenPowerStatsDoesNotExist() {
        boolean result = deletePowerStatsCommand.execute(UUID.randomUUID());

        assertFalse(result, "Expected delete to return false for non-existent PowerStats");
    }

    @Test
    void shouldThrowExceptionWhenIdIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            deletePowerStatsCommand.execute(null);
        });

        assertEquals("PowerStats ID cannot be null", exception.getMessage(),
                "Expected exception message to indicate null ID");
    }
}