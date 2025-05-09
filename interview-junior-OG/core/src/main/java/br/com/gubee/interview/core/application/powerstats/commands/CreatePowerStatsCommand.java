package br.com.gubee.interview.core.application.powerstats.commands;

import br.com.gubee.interview.core.domain.PowerStats;
import br.com.gubee.interview.core.adapters.persistence.PowerStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreatePowerStatsCommand {
    private final PowerStatsRepository powerStatsRepository;

    @Transactional
    public UUID execute(PowerStats powerStats) {
        validatePowerStats(powerStats);
        return powerStatsRepository.create(powerStats);
    }

    private void validatePowerStats(PowerStats powerStats) {
        if (powerStats.getStrength() < 0 || powerStats.getAgility() < 0 ||
                powerStats.getDexterity() < 0 || powerStats.getIntelligence() < 0) {
            throw new IllegalArgumentException("PowerStats values cannot be negative.");
        }
    }
}