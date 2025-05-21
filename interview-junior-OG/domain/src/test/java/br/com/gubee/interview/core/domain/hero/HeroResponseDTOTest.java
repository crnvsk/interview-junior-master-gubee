package br.com.gubee.interview.core.domain.hero;

import br.com.gubee.interview.core.domain.enums.Race;
import br.com.gubee.interview.core.domain.powerstats.PowerstatsDTO;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class HeroResponseDTOTest {

    @Test
    void shouldCreateHeroResponseDTO() {
        UUID id = UUID.randomUUID();
        PowerstatsDTO powerstatsDTO = new PowerstatsDTO(10, 9, 8, 7);
        Instant now = Instant.now();

        HeroResponseDTO dto = new HeroResponseDTO(id, "Wonder Woman", Race.HUMAN, powerstatsDTO, now, now, true);

        assertEquals(id, dto.id());
        assertEquals("Wonder Woman", dto.name());
        assertEquals(Race.HUMAN, dto.race());
        assertEquals(powerstatsDTO, dto.powerStats());
        assertEquals(now, dto.createdAt());
        assertEquals(now, dto.updatedAt());
        assertTrue(dto.enabled());
    }
}