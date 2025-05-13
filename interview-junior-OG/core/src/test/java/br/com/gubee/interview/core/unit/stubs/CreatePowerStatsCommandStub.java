package br.com.gubee.interview.core.unit.stubs;

import br.com.gubee.interview.core.adapters.persistence.PowerStatsRepository;
import br.com.gubee.interview.core.application.powerstats.commands.CreatePowerStatsCommand;
import br.com.gubee.interview.core.domain.PowerStats;

import java.util.UUID;

public class CreatePowerStatsCommandStub extends CreatePowerStatsCommand {
    public CreatePowerStatsCommandStub(PowerStatsRepository powerStatsRepository) {
        super(powerStatsRepository);
    }
    
    @Override
    public UUID execute(PowerStats powerStats) {
        return UUID.randomUUID();
    }
}