package br.com.gubee.interview.core.application.testdoubles;

import br.com.gubee.interview.core.application.powerstats.queries.FindPowerStatsByIdQuery;
import br.com.gubee.interview.core.domain.PowerStats;

import java.util.Optional;
import java.util.UUID;

public class FindPowerStatsByIdQueryStub implements FindPowerStatsByIdQuery {
    private final PowerStatsRepositoryStub powerStatsRepositoryStub;

    public FindPowerStatsByIdQueryStub(PowerStatsRepositoryStub powerStatsRepositoryStub) {
        this.powerStatsRepositoryStub = powerStatsRepositoryStub;
    }

    @Override
    public Optional<PowerStats> execute(UUID id) {
        return powerStatsRepositoryStub.findById(id);
    }
}