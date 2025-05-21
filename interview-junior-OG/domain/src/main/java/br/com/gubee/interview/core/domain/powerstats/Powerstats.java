package br.com.gubee.interview.core.domain.powerstats;

import java.time.Instant;
import java.util.UUID;

public class Powerstats {
    private UUID id;
    private int strength;
    private int agility;
    private int dexterity;
    private int intelligence;
    private Instant createdAt;
    private Instant updatedAt;

    public Powerstats() {
    }

    public Powerstats(UUID id, int strength, int agility, int dexterity, int intelligence, Instant createdAt,
            Instant updatedAt) {
        validateStat(strength, "strength");
        validateStat(agility, "agility");
        validateStat(dexterity, "dexterity");
        validateStat(intelligence, "intelligence");
        this.id = id;
        this.strength = strength;
        this.agility = agility;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    private void validateStat(int value, String fieldName) {
        if (value < 0 || value > 10) {
            throw new IllegalArgumentException(fieldName + " must be between 0 and 10");
        }
    }

    public PowerstatsComparisonResult compareWith(Powerstats other) {
        return new PowerstatsComparisonResult(
                this.strength - other.strength,
                this.agility - other.agility,
                this.dexterity - other.dexterity,
                this.intelligence - other.intelligence);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        validateStat(strength, "strength");
        this.strength = strength;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        validateStat(agility, "agility");
        this.agility = agility;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        validateStat(dexterity, "dexterity");
        this.dexterity = dexterity;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        validateStat(intelligence, "intelligence");
        this.intelligence = intelligence;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}