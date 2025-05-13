package br.com.gubee.interview.core.unit.domain;

import br.com.gubee.interview.core.domain.PowerStats;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PowerStatsTest {

    @Test
    void shouldCreatePowerStatsSuccessfully() {
        PowerStats powerStats = PowerStats.builder()
                .strength(10)
                .agility(8)
                .dexterity(7)
                .intelligence(9)
                .build();

        assertEquals(10, powerStats.getStrength());
        assertEquals(8, powerStats.getAgility());
        assertEquals(7, powerStats.getDexterity());
        assertEquals(9, powerStats.getIntelligence());
    }

    @Test
    void shouldThrowExceptionWhenStrengthIsNegative() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            PowerStats.builder()
                .strength(-1)
                .agility(8)
                .dexterity(7)
                .intelligence(9)
                .build();
        });

        assertEquals("Strength cannot be negative", exception.getMessage());
    }

    @Test
    void shouldCalculateTotalPowerCorrectly() {
        PowerStats powerStats = PowerStats.builder()
                .strength(10)
                .agility(8)
                .dexterity(7)
                .intelligence(9)
                .build();

        int totalPower = powerStats.calculateTotalPower();

        assertEquals(34, totalPower);
    }
}