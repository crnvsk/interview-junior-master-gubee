package br.com.gubee.interview.core.application.powerstats.queries;

import br.com.gubee.interview.core.domain.PowerStats;
import br.com.gubee.interview.core.application.helpers.PowerStatsTestDataBuilder;
import br.com.gubee.interview.core.application.testdoubles.FindPowerStatsByIdQueryStub;
import br.com.gubee.interview.core.application.testdoubles.PowerStatsRepositoryStub;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FindPowerStatsByIdQueryTest {

    @Test
    void shouldFindPowerStatsByIdSuccessfully() {
        PowerStatsRepositoryStub powerStatsRepositoryStub = new PowerStatsRepositoryStub();
        FindPowerStatsByIdQuery findPowerStatsByIdQuery = new FindPowerStatsByIdQueryStub(powerStatsRepositoryStub);

        PowerStats powerStats = new PowerStatsTestDataBuilder()
                .withStrength(10)
                .withAgility(8)
                .withDexterity(7)
                .withIntelligence(9)
                .build();

        UUID powerStatsId = powerStatsRepositoryStub.create(powerStats);

        Optional<PowerStats> result = findPowerStatsByIdQuery.execute(powerStatsId);

        assertTrue(result.isPresent());
        assertEquals(10, result.get().getStrength());
        assertEquals(8, result.get().getAgility());
        assertEquals(7, result.get().getDexterity());
        assertEquals(9, result.get().getIntelligence());
    }

    @Test
    void shouldReturnEmptyWhenPowerStatsNotFound() {
        PowerStatsRepositoryStub powerStatsRepositoryStub = new PowerStatsRepositoryStub();
        FindPowerStatsByIdQuery findPowerStatsByIdQuery = new FindPowerStatsByIdQueryStub(powerStatsRepositoryStub);

        Optional<PowerStats> result = findPowerStatsByIdQuery.execute(UUID.randomUUID());

        assertTrue(result.isEmpty());
    }
}