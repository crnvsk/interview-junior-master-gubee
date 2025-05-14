package br.com.gubee.interview.core.adapters.web.mappers;

import br.com.gubee.interview.core.adapters.web.dtos.CreateHeroRequest;
import br.com.gubee.interview.core.domain.Hero;
import br.com.gubee.interview.core.domain.PowerStats;

import java.util.UUID;

public class HeroMapper {

    public static Hero toHero(CreateHeroRequest request, UUID powerStatsId) {
        return Hero.builder()
                .name(request.getName())
                .race(request.getRace())
                .powerStatsId(powerStatsId)
                .build();
    }

    public static PowerStats toPowerStats(CreateHeroRequest request) {
        return PowerStats.builder()
                .strength(request.getStrength())
                .agility(request.getAgility())
                .dexterity(request.getDexterity())
                .intelligence(request.getIntelligence())
                .build();
    }
}