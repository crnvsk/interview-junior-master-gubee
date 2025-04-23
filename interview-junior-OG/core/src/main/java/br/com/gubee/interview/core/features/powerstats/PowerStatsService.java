package br.com.gubee.interview.core.features.powerstats;

import br.com.gubee.interview.model.PowerStats;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PowerStatsService {

    private final PowerStatsRepository powerStatsRepository;

    @Transactional
    public UUID create(PowerStats powerStats) {
        validatePowerStats(powerStats);
        return powerStatsRepository.create(powerStats);
    }

    public Optional<PowerStats> findById(UUID id) {
        return powerStatsRepository.findById(id);
    }

    @Transactional
    public void delete(UUID id) {
        powerStatsRepository.delete(id);
    }

    private void validatePowerStats(PowerStats powerStats) {
        if (powerStats.getStrength() < 0 || powerStats.getAgility() < 0 ||
                powerStats.getDexterity() < 0 || powerStats.getIntelligence() < 0) {
            throw new IllegalArgumentException("PowerStats values cannot be negative.");
        }
    }
}
