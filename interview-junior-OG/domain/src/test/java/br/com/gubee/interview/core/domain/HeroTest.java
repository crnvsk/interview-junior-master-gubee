package br.com.gubee.interview.core.domain;

import br.com.gubee.interview.core.domain.enums.Race;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class HeroTest {

    @Test
    void shouldCreateHeroSuccessfully() {
        UUID powerStatsId = UUID.randomUUID();
        Hero hero = Hero.builder()
                .id(UUID.randomUUID())
                .name("Superman")
                .race(Race.HUMAN)
                .powerStatsId(powerStatsId)
                .build();

        assertNotNull(hero.getId());
        assertEquals("Superman", hero.getName());
        assertEquals(Race.HUMAN, hero.getRace());
        assertEquals(powerStatsId, hero.getPowerStatsId());
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Hero.builder()
                .id(UUID.randomUUID())
                .race(Race.HUMAN)
                .powerStatsId(UUID.randomUUID())
                .build();
        });

        assertEquals("Name cannot be null", exception.getMessage());
    }
}