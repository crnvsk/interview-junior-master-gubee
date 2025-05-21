package br.com.gubee.interview.core.domain.powerstats;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PowerstatsDTO(
        @NotNull @Min(0) @Max(10) Integer strength,
        @NotNull @Min(0) @Max(10) Integer agility,
        @NotNull @Min(0) @Max(10) Integer dexterity,
        @NotNull @Min(0) @Max(10) Integer intelligence) {
}