package br.com.gubee.interview.core.features.powerstats;

import br.com.gubee.interview.model.PowerStats;

import java.util.Optional;
import java.util.UUID;

public class InMemoryPowerStatsService extends PowerStatsService {

    private final PowerStatsRepository powerStatsRepository;

    public InMemoryPowerStatsService(PowerStatsRepository powerStatsRepository) {
        super(powerStatsRepository);
        this.powerStatsRepository = powerStatsRepository;
    }

    @Override
    public UUID create(PowerStats powerStats) {
        validatePowerStats(powerStats);
        return powerStatsRepository.create(powerStats);
    }

    @Override
    public Optional<PowerStats> findById(UUID id) {
        return powerStatsRepository.findById(id);
    }

    @Override
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