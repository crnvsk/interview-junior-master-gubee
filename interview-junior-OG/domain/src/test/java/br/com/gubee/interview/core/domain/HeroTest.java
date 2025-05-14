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
                .race(Race.ALIEN)
                .powerStatsId(powerStatsId)
                .build();

        assertNotNull(hero.getId(), "Expected hero ID to be initialized");
        assertEquals("Superman", hero.getName(), "Expected hero name to be 'Superman'");
        assertEquals(Race.ALIEN, hero.getRace(), "Expected hero race to be ALIEN");
        assertEquals(powerStatsId, hero.getPowerStatsId(), "Expected powerStatsId to match");
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

        assertEquals("Name cannot be null", exception.getMessage(), "Expected exception message for null name");
    }
}