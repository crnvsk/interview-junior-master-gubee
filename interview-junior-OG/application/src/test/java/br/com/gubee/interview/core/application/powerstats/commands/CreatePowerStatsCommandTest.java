package br.com.gubee.interview.core.application.powerstats.commands;

import br.com.gubee.interview.core.domain.PowerStats;
import br.com.gubee.interview.core.application.helpers.PowerStatsTestDataBuilder;
import br.com.gubee.interview.core.application.testdoubles.PowerStatsRepositoryStub;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class CreatePowerStatsCommandTest {

    @Test
    void shouldCreatePowerStatsSuccessfully() {
        PowerStatsRepositoryStub powerStatsRepositoryStub = new PowerStatsRepositoryStub();
        CreatePowerStatsCommand createPowerStatsCommand = new CreatePowerStatsCommand(powerStatsRepositoryStub);

        PowerStats powerStats = new PowerStatsTestDataBuilder()
                .withStrength(10)
                .withAgility(8)
                .withDexterity(7)
                .withIntelligence(9)
                .build();

        UUID powerStatsId = createPowerStatsCommand.execute(powerStats);

        assertNotNull(powerStatsId);
        assertNotNull(powerStatsRepositoryStub.findById(powerStatsId).orElse(null));
    }
}