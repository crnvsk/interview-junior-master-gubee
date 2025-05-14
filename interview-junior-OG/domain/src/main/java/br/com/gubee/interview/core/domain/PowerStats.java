package br.com.gubee.interview.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Data
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
@Builder
public class PowerStats {

    private UUID id;
    private int strength;
    private int agility;
    private int dexterity;
    private int intelligence;
    private Instant createdAt;
    private Instant updatedAt;

    public static class PowerStatsBuilder {
        public PowerStats build() {
            if (strength < 0) {
                throw new IllegalArgumentException("Strength cannot be negative");
            }
            if (agility < 0) {
                throw new IllegalArgumentException("Agility cannot be negative");
            }
            if (dexterity < 0) {
                throw new IllegalArgumentException("Dexterity cannot be negative");
            }
            if (intelligence < 0) {
                throw new IllegalArgumentException("Intelligence cannot be negative");
            }
            return new PowerStats(id, strength, agility, dexterity, intelligence, createdAt, updatedAt);
        }
    }
}
