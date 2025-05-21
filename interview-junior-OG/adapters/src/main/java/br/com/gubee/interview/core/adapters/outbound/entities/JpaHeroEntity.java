package br.com.gubee.interview.core.adapters.outbound.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

import br.com.gubee.interview.core.domain.enums.Race;
import br.com.gubee.interview.core.domain.hero.Hero;

@Table(name = "hero")
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JpaHeroEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Race race;


    @OneToOne
    @JoinColumn(name = "power_stats_id", referencedColumnName = "id")
    private JpaPowerstatsEntity powerStats;

    private Instant createdAt;
    private Instant updatedAt;
    private boolean enabled;

    @Version
    private Long version;

    public JpaHeroEntity(Hero hero) {
        this.id = hero.getId();
        this.name = hero.getName();
        this.race = hero.getRace();
        this.createdAt = hero.getCreatedAt();
        this.updatedAt = hero.getUpdatedAt();
        this.enabled = hero.isEnabled();
    }
}
