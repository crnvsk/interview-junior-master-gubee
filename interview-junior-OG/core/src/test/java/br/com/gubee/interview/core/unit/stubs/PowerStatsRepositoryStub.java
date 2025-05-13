package br.com.gubee.interview.core.unit.stubs;

import br.com.gubee.interview.core.adapters.persistence.PowerStatsRepository;
import br.com.gubee.interview.core.domain.PowerStats;

import java.util.*;

public class PowerStatsRepositoryStub implements PowerStatsRepository {
    private final Map<UUID, PowerStats> database = new HashMap<>();

    @Override
    public UUID create(PowerStats powerStats) {
        UUID id = UUID.randomUUID();
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