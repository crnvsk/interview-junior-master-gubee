package br.com.gubee.interview.core.features.powerstats;

import java.util.*;

import br.com.gubee.interview.core.adapters.persistence.PowerStatsRepository;
import br.com.gubee.interview.core.domain.PowerStats;

public class InMemoryPowerStatsRepository extends PowerStatsRepository {

    private final Map<UUID, PowerStats> database = new HashMap<>();

    public InMemoryPowerStatsRepository() {
        super(null);
    }

    @Override
    public UUID create(PowerStats powerStats) {
        UUID id = UUID.randomUUID();
        powerStats.setId(id);
        database.put(id, powerStats);
        return id;
    }

    @Override
    public Optional<PowerStats> findById(UUID id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public void delete(UUID id) {
        database.remove(id);
    }
}
