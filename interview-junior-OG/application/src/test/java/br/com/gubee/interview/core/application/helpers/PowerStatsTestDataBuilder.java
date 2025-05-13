package br.com.gubee.interview.core.application.helpers;

import br.com.gubee.interview.core.domain.PowerStats;

public class PowerStatsTestDataBuilder {
    private int strength = 5;
    private int agility = 5;
    private int dexterity = 5;
    private int intelligence = 5;

    public PowerStatsTestDataBuilder withStrength(int strength) {
        this.strength = strength;
        return this;
    }

    public PowerStatsTestDataBuilder withAgility(int agility) {
        this.agility = agility;
        return this;
    }

    public PowerStatsTestDataBuilder withDexterity(int dexterity) {
        this.dexterity = dexterity;
        return this;
    }

    public PowerStatsTestDataBuilder withIntelligence(int intelligence) {
        this.intelligence = intelligence;
        return this;
    }

    public PowerStats build() {
        return PowerStats.builder()
                .strength(strength)
                .agility(agility)
                .dexterity(dexterity)
                .intelligence(intelligence)
                .build();
    }
}