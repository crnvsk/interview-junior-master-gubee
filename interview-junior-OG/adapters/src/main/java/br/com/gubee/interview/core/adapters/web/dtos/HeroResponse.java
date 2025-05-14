package br.com.gubee.interview.core.adapters.web.dtos;

import br.com.gubee.interview.core.domain.enums.Race;
import br.com.gubee.interview.core.domain.Hero;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class HeroResponse {

    private UUID id;
    private String name;
    private Race race;
    private UUID powerStatsId;

    public static HeroResponse fromDomain(Hero hero) {
        return HeroResponse.builder()
                .id(hero.getId())
                .name(hero.getName())
                .race(hero.getRace())
                .powerStatsId(hero.getPowerStatsId())
                .build();
    }
}