package br.com.gubee.interview.core.domain.powerstats;

public record PowerstatsComparisonResult(
        int strengthDifference,
        int agilityDifference,
        int dexterityDifference,
        int intelligenceDifference) {
}