package br.com.gubee.interview.core.domain.hero;

import br.com.gubee.interview.core.domain.enums.Race;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class HeroTest {

    @Test
    void shouldCreateHeroWithAllFields() {
        UUID id = UUID.randomUUID();
        UUID powerStatsId = UUID.randomUUID();
        Instant now = Instant.now();

        Hero hero = new Hero(id, "Superman", Race.HUMAN, powerStatsId, now, now, true);

        assertEquals(id, hero.getId());
        assertEquals("Superman", hero.getName());
        assertEquals(Race.HUMAN, hero.getRace());
        assertEquals(powerStatsId, hero.getPowerStatsId());
        assertEquals(now, hero.getCreatedAt());
        assertEquals(now, hero.getUpdatedAt());
        assertTrue(hero.isEnabled());
    }

    @Test
    void shouldSetAndGetFields() {
        Hero hero = new Hero();
        UUID id = UUID.randomUUID();
        UUID powerStatsId = UUID.randomUUID();
        Instant now = Instant.now();

        hero.setId(id);
        hero.setName("Batman");
        hero.setRace(Race.HUMAN);
        hero.setPowerStatsId(powerStatsId);
        hero.setCreatedAt(now);
        hero.setUpdatedAt(now);
        hero.setEnabled(false);

        assertEquals(id, hero.getId());
        assertEquals("Batman", hero.getName());
        assertEquals(Race.HUMAN, hero.getRace());
        assertEquals(powerStatsId, hero.getPowerStatsId());
        assertEquals(now, hero.getCreatedAt());
        assertEquals(now, hero.getUpdatedAt());
        assertFalse(hero.isEnabled());
    }
}