package br.com.gubee.interview.core.application.powerstats.queries;

import br.com.gubee.interview.core.domain.PowerStats;
import br.com.gubee.interview.core.application.ports.repositories.PowerStatsRepository;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FindPowerStatsByIdQuery {
    private final PowerStatsRepository powerStatsRepository;

    @Transactional
    public Optional<PowerStats> execute(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("PowerStats ID cannot be null");
        }

        return powerStatsRepository.findById(id);
    }
}