package br.com.gubee.interview.core.application.powerstats.queries;

import br.com.gubee.interview.core.domain.PowerStats;
import br.com.gubee.interview.core.application.ports.repositories.PowerStatsRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindPowerStatsByIdQueryImpl implements FindPowerStatsByIdQuery {
    private final PowerStatsRepository powerStatsRepository;

    @Override
    public Optional<PowerStats> execute(UUID id) {
        return powerStatsRepository.findById(id);
    }
}