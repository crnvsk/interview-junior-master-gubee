package br.com.gubee.interview.core.unit.helpers;

import br.com.gubee.interview.core.domain.Hero;
import br.com.gubee.interview.core.domain.enums.Race;

import java.util.UUID;

public class HeroTestDataBuilder {
    private UUID id = UUID.randomUUID();
    private String name = "Default Hero";
    private Race race = Race.HUMAN;
    private UUID powerStatsId = UUID.randomUUID();

    public HeroTestDataBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    public HeroTestDataBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public HeroTestDataBuilder withRace(Race race) {
        this.race = race;
        return this;
    }

    public HeroTestDataBuilder withPowerStatsId(UUID powerStatsId) {
        this.powerStatsId = powerStatsId;
        return this;
    }

    public Hero build() {
        return Hero.builder()
                .id(id)
                .name(name)
                .race(race)
                .powerStatsId(powerStatsId)
                .build();
    }
}