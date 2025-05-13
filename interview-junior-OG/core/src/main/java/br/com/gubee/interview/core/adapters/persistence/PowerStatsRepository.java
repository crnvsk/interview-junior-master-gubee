package br.com.gubee.interview.core.adapters.persistence;

import br.com.gubee.interview.core.domain.PowerStats;

import java.util.Optional;
import java.util.UUID;

public interface PowerStatsRepository {
    UUID create(PowerStats powerStats);
    Optional<PowerStats> findById(UUID id);
    void delete(UUID id);
}