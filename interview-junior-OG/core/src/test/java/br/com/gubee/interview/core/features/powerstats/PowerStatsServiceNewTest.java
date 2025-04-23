package br.com.gubee.interview.core.features.powerstats;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.gubee.interview.model.PowerStats;

public class PowerStatsServiceNewTest {
    private InMemoryPowerStatsService powerStatsService;
    private InMemoryPowerStatsRepository powerStatsRepository;

    @BeforeEach
    public void setUp() {
        this.powerStatsRepository = new InMemoryPowerStatsRepository();
        this.powerStatsService = new InMemoryPowerStatsService(powerStatsRepository);
    }

    @Test
    void criarPowerStatsComDadosValidos() {
        PowerStats powerStats = PowerStats.builder()
                .strength(10)
                .agility(9)
                .dexterity(8)
                .intelligence(7)
                .build();

        UUID powerStatsId = powerStatsRepository.create(powerStats);
        assertNotNull(powerStatsId);
        PowerStats savedPowerStats = powerStatsRepository.findById(powerStatsId).orElse(null);
        assertNotNull(savedPowerStats);
        assertEquals(10, savedPowerStats.getStrength());
        assertEquals(9, savedPowerStats.getAgility());
        assertEquals(8, savedPowerStats.getDexterity());
        assertEquals(7, savedPowerStats.getIntelligence());
    }

    @Test
    void criarPowerStatsComValoresNegativosDeveLancarExcecao() {
        PowerStats powerStats = PowerStats.builder()
                .strength(-1)
                .agility(9)
                .dexterity(8)
                .intelligence(7)
                .build();

        assertThrows(IllegalArgumentException.class, () -> {
            powerStatsService.create(powerStats);
        });
    }

    @Test
    void encontrarPowerStatsPorIdDeveRetornarPowerStats() {
        PowerStats powerStats = PowerStats.builder()
                .strength(10)
                .agility(9)
                .dexterity(8)
                .intelligence(7)
                .build();

        UUID powerStatsId = powerStatsRepository.create(powerStats);
        PowerStats foundPowerStats = powerStatsService.findById(powerStatsId).orElse(null);
        assertNotNull(foundPowerStats);
        assertEquals(powerStatsId, foundPowerStats.getId());
    }

    @Test
    void encontrarPowerStatsPorIdInexistenteDeveRetornarVazio() {
        UUID nonExistentId = UUID.randomUUID();
        PowerStats foundPowerStats = powerStatsService.findById(nonExistentId).orElse(null);
        assertNull(foundPowerStats);
    }

    @Test
    void deletarPowerStatsExistenteDeveRemover() {
        PowerStats powerStats = PowerStats.builder()
                .strength(10)
                .agility(9)
                .dexterity(8)
                .intelligence(7)
                .build();

        UUID powerStatsId = powerStatsRepository.create(powerStats);
        powerStatsService.delete(powerStatsId);
        PowerStats foundPowerStats = powerStatsService.findById(powerStatsId).orElse(null);
        assertNull(foundPowerStats);
    }
}
