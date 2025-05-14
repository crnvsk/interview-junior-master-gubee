package br.com.gubee.interview.core.application.powerstats.queries;

import br.com.gubee.interview.core.domain.PowerStats;
import br.com.gubee.interview.core.application.ports.repositories.PowerStatsRepository;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class FindPowerStatsByIdQuery {
    private final PowerStatsRepository powerStatsRepository;

    public Optional<PowerStats> execute(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("PowerStats ID cannot be null");
        }

        return powerStatsRepository.findById(id);
    }
}