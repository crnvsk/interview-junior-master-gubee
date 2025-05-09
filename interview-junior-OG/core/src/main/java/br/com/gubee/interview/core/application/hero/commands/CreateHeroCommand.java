package br.com.gubee.interview.core.application.hero.commands;

import br.com.gubee.interview.core.domain.Hero;
import br.com.gubee.interview.core.domain.PowerStats;
import br.com.gubee.interview.core.adapters.persistence.HeroRepository;
import br.com.gubee.interview.core.application.powerstats.commands.CreatePowerStatsCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateHeroCommand {
    private final HeroRepository heroRepository;
    private final CreatePowerStatsCommand createPowerStatsCommand;

    public UUID execute(Hero hero, PowerStats powerStats) {
        UUID powerStatsId = createPowerStatsCommand.execute(powerStats);
        hero.setPowerStatsId(powerStatsId);
        return heroRepository.create(hero);
    }
}