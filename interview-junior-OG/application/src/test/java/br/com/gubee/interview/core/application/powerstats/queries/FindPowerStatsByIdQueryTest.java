package br.com.gubee.interview.core.application.powerstats.queries;

import br.com.gubee.interview.core.domain.PowerStats;
import br.com.gubee.interview.core.application.helpers.PowerStatsTestDataBuilder;
import br.com.gubee.interview.core.application.testdoubles.PowerStatsRepositoryStub;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FindPowerStatsByIdQueryTest {

    private PowerStatsRepositoryStub powerStatsRepositoryStub;
    private FindPowerStatsByIdQuery findPowerStatsByIdQuery;

    @BeforeEach
    void setUp() {
        powerStatsRepositoryStub = new PowerStatsRepositoryStub();
        findPowerStatsByIdQuery = new FindPowerStatsByIdQuery(powerStatsRepositoryStub);
    }

    @Test
    void shouldReturnPowerStatsWhenIdExists() {
        PowerStats powerStats = new PowerStatsTestDataBuilder()
                .withStrength(10)
                .withAgility(8)
                .withDexterity(7)
                .withIntelligence(9)
                .build();
        UUID powerStatsId = powerStatsRepositoryStub.create(powerStats);

        Optional<PowerStats> result = findPowerStatsByIdQuery.execute(powerStatsId);

        assertTrue(result.isPresent(), "Expected PowerStats to be found");
        assertEquals(10, result.get().getStrength(), "Expected strength to be 10");
        assertEquals(8, result.get().getAgility(), "Expected agility to be 8");
        assertEquals(7, result.get().getDexterity(), "Expected dexterity to be 7");
        assertEquals(9, result.get().getIntelligence(), "Expected intelligence to be 9");
    }

    @Test
    void shouldReturnEmptyWhenPowerStatsDoesNotExist() {
        Optional<PowerStats> result = findPowerStatsByIdQuery.execute(UUID.randomUUID());

        assertTrue(result.isEmpty(), "Expected no PowerStats to be found");
    }

    @Test
    void shouldThrowExceptionWhenIdIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            findPowerStatsByIdQuery.execute(null);
        });

        assertEquals("PowerStats ID cannot be null", exception.getMessage(),
                "Expected exception message to indicate null ID");
    }
}