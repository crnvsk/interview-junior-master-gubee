package br.com.gubee.interview.core.domain.powerstats;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PowerstatsTest {

    @Test
    void shouldCreatePowerstatsWithAllFields() {
        UUID id = UUID.randomUUID();
        Instant now = Instant.now();
        Powerstats powerstats = new Powerstats(id, 10, 9, 8, 7, now, now);

        assertEquals(id, powerstats.getId());
        assertEquals(10, powerstats.getStrength());
        assertEquals(9, powerstats.getAgility());
        assertEquals(8, powerstats.getDexterity());
        assertEquals(7, powerstats.getIntelligence());
        assertEquals(now, powerstats.getCreatedAt());
        assertEquals(now, powerstats.getUpdatedAt());
    }

    @Test
    void shouldSetAndGetFields() {
        Powerstats powerstats = new Powerstats();
        UUID id = UUID.randomUUID();
        Instant now = Instant.now();

        powerstats.setId(id);
        powerstats.setStrength(5);
        powerstats.setAgility(6);
        powerstats.setDexterity(4);
        powerstats.setIntelligence(3);
        powerstats.setCreatedAt(now);
        powerstats.setUpdatedAt(now);

        assertEquals(id, powerstats.getId());
        assertEquals(5, powerstats.getStrength());
        assertEquals(6, powerstats.getAgility());
        assertEquals(4, powerstats.getDexterity());
        assertEquals(3, powerstats.getIntelligence());
        assertEquals(now, powerstats.getCreatedAt());
        assertEquals(now, powerstats.getUpdatedAt());
    }
}