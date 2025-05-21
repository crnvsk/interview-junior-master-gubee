package br.com.gubee.interview.core.domain.powerstats;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PowerstatsDTOTest {

    @Test
    void shouldCreatePowerstatsDTO() {
        PowerstatsDTO dto = new PowerstatsDTO(1, 2, 3, 4);

        assertEquals(1, dto.strength());
        assertEquals(2, dto.agility());
        assertEquals(3, dto.dexterity());
        assertEquals(4, dto.intelligence());
    }
}