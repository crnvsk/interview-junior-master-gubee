package br.com.gubee.interview.core.application.helpers;

import br.com.gubee.interview.core.domain.PowerStats;

public class PowerStatsTestDataBuilder {
    private int strength = 1;
    private int agility = 2;
    private int dexterity = 3;
    private int intelligence = 4;

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
        if (strength < 0 || strength > 10) {
            throw new IllegalArgumentException("Strength must be between 0 and 10.");
        }
        return PowerStats.builder()
                .strength(strength)
                .agility(agility)
                .dexterity(dexterity)
                .intelligence(intelligence)
                .build();
    }
}