package br.com.gubee.interview.core.domain.hero;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class HeroComparisonResponseDTOTest {

    @Test
    void shouldCreateHeroComparisonResponseDTO() {
        UUID hero1Id = UUID.randomUUID();
        UUID hero2Id = UUID.randomUUID();

        HeroComparisonResponseDTO dto = new HeroComparisonResponseDTO(
                hero1Id, hero2Id, 5, -3, 2, 0);

        assertEquals(hero1Id, dto.hero1Id());
        assertEquals(hero2Id, dto.hero2Id());
        assertEquals(5, dto.strengthDifference());
        assertEquals(-3, dto.agilityDifference());
        assertEquals(2, dto.dexterityDifference());
        assertEquals(0, dto.intelligenceDifference());
    }
}