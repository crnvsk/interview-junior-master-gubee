package br.com.gubee.interview.core.features.powerstats;

import br.com.gubee.interview.model.PowerStats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PowerStatsServiceTest {

    private PowerStatsService powerStatsService;
    private InMemoryPowerStatsRepository powerStatsRepository;

    @BeforeEach
    void setUp() {
        powerStatsRepository = new InMemoryPowerStatsRepository();
        powerStatsService = new PowerStatsService(powerStatsRepository);
    }

    @Test
    void createPowerStatsShouldReturnId() {
        PowerStats powerStats = PowerStats.builder()
                .strength(10)
                .agility(8)
                .dexterity(7)
                .intelligence(9)
                .build();

        UUID powerStatsId = powerStatsService.create(powerStats);

        assertNotNull(powerStatsId);
        PowerStats savedPowerStats = powerStatsRepository.findById(powerStatsId).orElse(null);
        assertNotNull(savedPowerStats);
        assertEquals(10, savedPowerStats.getStrength());
        assertEquals(8, savedPowerStats.getAgility());
        assertEquals(7, savedPowerStats.getDexterity());
        assertEquals(9, savedPowerStats.getIntelligence());
    }

    @Test
    void findByIdShouldReturnPowerStats() {
        PowerStats powerStats = PowerStats.builder()
                .strength(6)
                .agility(7)
                .dexterity(8)
                .intelligence(9)
                .build();

        UUID powerStatsId = powerStatsService.create(powerStats);

        Optional<PowerStats> foundPowerStats = powerStatsService.findById(powerStatsId);

        assertTrue(foundPowerStats.isPresent());
        assertEquals(6, foundPowerStats.get().getStrength());
        assertEquals(7, foundPowerStats.get().getAgility());
        assertEquals(8, foundPowerStats.get().getDexterity());
        assertEquals(9, foundPowerStats.get().getIntelligence());
    }

    @Test
    void findByIdShouldReturnEmptyWhenNotFound() {
        Optional<PowerStats> foundPowerStats = powerStatsService.findById(UUID.randomUUID());
        assertTrue(foundPowerStats.isEmpty());
    }
}
