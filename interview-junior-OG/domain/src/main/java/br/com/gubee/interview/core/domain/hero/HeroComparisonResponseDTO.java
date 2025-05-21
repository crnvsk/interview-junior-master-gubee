package br.com.gubee.interview.core.domain.hero;

import java.util.UUID;

public record HeroComparisonResponseDTO(
        UUID hero1Id,
        UUID hero2Id,
        int strengthDifference,
        int agilityDifference,
        int dexterityDifference,
        int intelligenceDifference) {
}