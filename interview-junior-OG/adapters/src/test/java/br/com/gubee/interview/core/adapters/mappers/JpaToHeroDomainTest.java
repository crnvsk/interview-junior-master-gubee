package br.com.gubee.interview.core.adapters.mappers;

import br.com.gubee.interview.core.adapters.outbound.entities.JpaHeroEntity;
import br.com.gubee.interview.core.adapters.outbound.entities.JpaPowerstatsEntity;
import br.com.gubee.interview.core.adapters.utils.mappers.JpaToHeroDomain;
import br.com.gubee.interview.core.adapters.stubs.JpaToHeroDomainStub;
import br.com.gubee.interview.core.domain.enums.Race;
import br.com.gubee.interview.core.domain.hero.Hero;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class JpaToHeroDomainTest {

    @Test
    void shouldMapJpaHeroEntityToDomain() {
        UUID heroId = UUID.randomUUID();
        UUID statsId = UUID.randomUUID();
        Instant createdAt = Instant.now();
        Instant updatedAt = Instant.now();

        JpaPowerstatsEntity stats = new JpaPowerstatsEntity();
        stats.setId(statsId);

        JpaHeroEntity jpa = new JpaHeroEntity();
        jpa.setId(heroId);
        jpa.setName("HeroName");
        jpa.setRace(Race.HUMAN);
        jpa.setPowerStats(stats);
        jpa.setCreatedAt(createdAt);
        jpa.setUpdatedAt(updatedAt);
        jpa.setEnabled(true);

        JpaToHeroDomain mapper = new JpaToHeroDomainStub();
        Hero domain = mapper.toDomain(jpa);

        assertEquals(heroId, domain.getId());
        assertEquals("HeroName", domain.getName());
        assertEquals(Race.HUMAN, domain.getRace());
        assertEquals(statsId, domain.getPowerStatsId());
        assertEquals(createdAt, domain.getCreatedAt());
        assertEquals(updatedAt, domain.getUpdatedAt());
        assertTrue(domain.isEnabled());
    }
}
