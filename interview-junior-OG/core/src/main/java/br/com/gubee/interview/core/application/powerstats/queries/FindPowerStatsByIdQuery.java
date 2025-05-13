package br.com.gubee.interview.core.application.powerstats.queries;

import br.com.gubee.interview.core.domain.PowerStats;

import java.util.Optional;
import java.util.UUID;

public interface FindPowerStatsByIdQuery {
    Optional<PowerStats> execute(UUID id);
}