package br.com.gubee.interview.core.adapters.repositories;

import br.com.gubee.interview.core.adapters.outbound.respositories.powerstats.PowerstatsRepositoryImpl;
import br.com.gubee.interview.core.adapters.stubs.JpaPowerstatsRepositoryStub;
import br.com.gubee.interview.core.adapters.stubs.JpaToPowerstatsDomainStub;
import br.com.gubee.interview.core.domain.powerstats.Powerstats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PowerstatsRepositoryImplTest {
    private JpaPowerstatsRepositoryStub jpaPowerstatsRepository;
    private JpaToPowerstatsDomainStub jpaToPowerstatsDomain;
    private PowerstatsRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        jpaPowerstatsRepository = new JpaPowerstatsRepositoryStub();
        jpaToPowerstatsDomain = new JpaToPowerstatsDomainStub();
        repository = new PowerstatsRepositoryImpl(jpaPowerstatsRepository);
        repository.setJpaToPowerstatsDomain(jpaToPowerstatsDomain);
    }

    @Test
    void shouldSaveAndFindPowerstats() {
        UUID id = UUID.randomUUID();
        Instant now = Instant.now();
        Powerstats powerstats = new Powerstats(id, 10, 9, 8, 7, now, now);

        Powerstats saved = repository.save(powerstats);

        assertNotNull(saved);
        assertEquals(10, saved.getStrength());

        Optional<Powerstats> found = repository.findById(id);
        assertTrue(found.isPresent());
        assertEquals(9, found.get().getAgility());
    }

    @Test
    void shouldUpdatePowerstats() {
        UUID id = UUID.randomUUID();
        Instant now = Instant.now();
        Powerstats original = new Powerstats(id, 1, 2, 3, 4, now, now);
        repository.save(original);

        Powerstats updated = new Powerstats(id, 10, 9, 8, 7, now, now);
        Powerstats result = repository.update(id, updated);

        assertEquals(10, result.getStrength());
        assertEquals(9, result.getAgility());
        assertEquals(8, result.getDexterity());
        assertEquals(7, result.getIntelligence());
    }
}