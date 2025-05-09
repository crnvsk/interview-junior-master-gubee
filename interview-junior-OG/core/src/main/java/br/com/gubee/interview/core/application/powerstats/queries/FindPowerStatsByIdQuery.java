package br.com.gubee.interview.core.application.powerstats.queries;

import br.com.gubee.interview.core.domain.PowerStats;
import br.com.gubee.interview.core.adapters.persistence.PowerStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindPowerStatsByIdQuery {
    private final PowerStatsRepository powerStatsRepository;

    public Optional<PowerStats> execute(UUID id) {
        return powerStatsRepository.findById(id);
    }
}