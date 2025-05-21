package br.com.gubee.interview.core.adapters.mappers;

import br.com.gubee.interview.core.adapters.outbound.entities.JpaPowerstatsEntity;
import br.com.gubee.interview.core.adapters.stubs.JpaToPowerstatsDomainStub;
import br.com.gubee.interview.core.adapters.utils.mappers.JpaToPowerstatsDomain;
import br.com.gubee.interview.core.domain.powerstats.Powerstats;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class JpaToPowerstatsDomainTest {

    @Test
    void shouldMapJpaPowerstatsEntityToDomain() {
        UUID id = UUID.randomUUID();
        Instant createdAt = Instant.now();
        Instant updatedAt = Instant.now();

        JpaPowerstatsEntity jpa = new JpaPowerstatsEntity();
        jpa.setId(id);
        jpa.setStrength(10);
        jpa.setAgility(9);
        jpa.setDexterity(8);
        jpa.setIntelligence(7);
        jpa.setCreatedAt(createdAt);
        jpa.setUpdatedAt(updatedAt);

        JpaToPowerstatsDomain mapper = new JpaToPowerstatsDomainStub();
        Powerstats domain = mapper.toDomain(jpa);

        assertEquals(id, domain.getId());
        assertEquals(10, domain.getStrength());
        assertEquals(9, domain.getAgility());
        assertEquals(8, domain.getDexterity());
        assertEquals(7, domain.getIntelligence());
        assertEquals(createdAt, domain.getCreatedAt());
        assertEquals(updatedAt, domain.getUpdatedAt());
    }
}
