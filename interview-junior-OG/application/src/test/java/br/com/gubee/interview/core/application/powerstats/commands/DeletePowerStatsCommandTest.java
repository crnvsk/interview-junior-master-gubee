package br.com.gubee.interview.core.application.powerstats.commands;

import br.com.gubee.interview.core.domain.PowerStats;
import br.com.gubee.interview.core.application.helpers.PowerStatsTestDataBuilder;
import br.com.gubee.interview.core.application.testdoubles.PowerStatsRepositoryStub;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;

class DeletePowerStatsCommandTest {

    @Test
    void shouldDeletePowerStatsSuccessfully() {
        PowerStatsRepositoryStub powerStatsRepositoryStub = new PowerStatsRepositoryStub();
        DeletePowerStatsCommand deletePowerStatsCommand = new DeletePowerStatsCommand(powerStatsRepositoryStub);

        PowerStats powerStats = new PowerStatsTestDataBuilder()
                .withStrength(10)
                .withAgility(8)
                .withDexterity(7)
                .withIntelligence(9)
                .build();

        UUID powerStatsId = powerStatsRepositoryStub.create(powerStats);

        deletePowerStatsCommand.execute(powerStatsId);

        assertFalse(powerStatsRepositoryStub.findById(powerStatsId).isPresent());
    }
}