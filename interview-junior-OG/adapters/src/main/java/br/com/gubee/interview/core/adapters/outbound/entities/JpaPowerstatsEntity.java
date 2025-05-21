package br.com.gubee.interview.core.adapters.outbound.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

import br.com.gubee.interview.core.domain.powerstats.Powerstats;

@Table(name = "power_stats")
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JpaPowerstatsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private int strength;
    private int agility;
    private int dexterity;
    private int intelligence;
    private Instant createdAt;
    private Instant updatedAt;

    @Version
    private Long version;

    public JpaPowerstatsEntity(Powerstats powerstats) {
        this.id = powerstats.getId();
        this.strength = powerstats.getStrength();
        this.agility = powerstats.getAgility();
        this.dexterity = powerstats.getDexterity();
        this.intelligence = powerstats.getIntelligence();
        this.createdAt = powerstats.getCreatedAt();
        this.updatedAt = powerstats.getUpdatedAt();
    }
}
