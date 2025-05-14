package br.com.gubee.interview.core.domain;

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

        assertEquals(10, powerStats.getStrength(), "Expected strength to be 10");
        assertEquals(8, powerStats.getAgility(), "Expected agility to be 8");
        assertEquals(7, powerStats.getDexterity(), "Expected dexterity to be 7");
        assertEquals(9, powerStats.getIntelligence(), "Expected intelligence to be 9");
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

        assertEquals("Strength cannot be negative", exception.getMessage(),
                "Expected exception message for negative strength");
    }
}