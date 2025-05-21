package br.com.gubee.interview.core.domain.hero;

import br.com.gubee.interview.core.domain.enums.Race;
import br.com.gubee.interview.core.domain.powerstats.PowerstatsDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeroRequestDTOTest {

    @Test
    void shouldCreateHeroRequestDTO() {
        PowerstatsDTO powerstatsDTO = new PowerstatsDTO(10, 9, 8, 7);
        HeroRequestDTO dto = new HeroRequestDTO("Flash", Race.HUMAN, powerstatsDTO, true);

        assertEquals("Flash", dto.name());
        assertEquals(Race.HUMAN, dto.race());
        assertEquals(powerstatsDTO, dto.powerStats());
        assertTrue(dto.enabled());
    }
}