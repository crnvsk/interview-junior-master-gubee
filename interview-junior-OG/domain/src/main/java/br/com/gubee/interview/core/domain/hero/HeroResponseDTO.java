package br.com.gubee.interview.core.domain.hero;

import br.com.gubee.interview.core.domain.enums.Race;
import br.com.gubee.interview.core.domain.powerstats.PowerstatsDTO;

import java.time.Instant;
import java.util.UUID;

public record HeroResponseDTO(
    UUID id,
    String name,
    Race race,
    PowerstatsDTO powerStats,
    Instant createdAt,
    Instant updatedAt,
    Boolean enabled) {
}