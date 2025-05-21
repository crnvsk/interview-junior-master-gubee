package br.com.gubee.interview.core.adapters.repositories;

import br.com.gubee.interview.core.adapters.outbound.entities.JpaPowerstatsEntity;
import br.com.gubee.interview.core.adapters.outbound.respositories.hero.HeroRepositoryImpl;
import br.com.gubee.interview.core.adapters.stubs.JpaHeroRepositoryStub;
import br.com.gubee.interview.core.adapters.stubs.JpaPowerstatsRepositoryStub;
import br.com.gubee.interview.core.adapters.stubs.JpaToHeroDomainStub;
import br.com.gubee.interview.core.domain.enums.Race;
import br.com.gubee.interview.core.domain.hero.Hero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class HeroRepositoryImplTest {
    private JpaHeroRepositoryStub heroRepoStub;
    private JpaPowerstatsRepositoryStub powerstatsRepoStub;
    private JpaToHeroDomainStub toDomainStub;
    private HeroRepositoryImpl heroRepository;

    @BeforeEach
    void setUp() {
        heroRepoStub = new JpaHeroRepositoryStub();
        powerstatsRepoStub = new JpaPowerstatsRepositoryStub();
        toDomainStub = new JpaToHeroDomainStub();

        heroRepository = new HeroRepositoryImpl(heroRepoStub, powerstatsRepoStub);
        heroRepository.setJpaHeroDomain(toDomainStub);
    }

    @Test
    void testSaveAndFindById() {
        JpaPowerstatsEntity powerstats = new JpaPowerstatsEntity();
        powerstats.setId(UUID.randomUUID());
        powerstatsRepoStub.save(powerstats);

        Hero hero = new Hero();
        hero.setId(UUID.randomUUID());
        hero.setName("Superman");
        hero.setRace(Race.ALIEN);
        hero.setPowerStatsId(powerstats.getId());
        hero.setCreatedAt(Instant.now());
        hero.setUpdatedAt(Instant.now());
        hero.setEnabled(true);

        Hero saved = heroRepository.save(hero);

        assertNotNull(saved.getId());
        assertEquals("Superman", saved.getName());

        Optional<Hero> found = heroRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Superman", found.get().getName());
    }

    @Test
    void testFindByName() {
        Hero hero = new Hero();
        hero.setId(UUID.randomUUID());
        hero.setName("Batman");
        hero.setRace(Race.HUMAN);
        hero.setCreatedAt(Instant.now());
        hero.setUpdatedAt(Instant.now());
        hero.setEnabled(true);

        heroRepository.save(hero);

        Optional<Hero> found = heroRepository.findByName("Batman");

        assertTrue(found.isPresent());
        assertEquals("Batman", found.get().getName());
    }

    @Test
    void testUpdate() {
        Hero hero = new Hero();
        hero.setId(UUID.randomUUID());
        hero.setName("Flash");
        hero.setRace(Race.HUMAN);
        hero.setCreatedAt(Instant.now());
        hero.setUpdatedAt(Instant.now());
        hero.setEnabled(true);

        Hero saved = heroRepository.save(hero);

        Hero updated = new Hero();
        updated.setId(saved.getId());
        updated.setName("Flash Updated");
        updated.setRace(Race.HUMAN);
        updated.setCreatedAt(saved.getCreatedAt());
        updated.setUpdatedAt(Instant.now());
        updated.setEnabled(true);

        heroRepository.update(saved.getId(), updated);

        Optional<Hero> found = heroRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Flash Updated", found.get().getName());
    }

    @Test
    void testDelete() {
        Hero hero = new Hero();
        hero.setId(UUID.randomUUID());
        hero.setName("Wonder Woman");
        hero.setRace(Race.HUMAN);
        hero.setCreatedAt(Instant.now());
        hero.setUpdatedAt(Instant.now());
        hero.setEnabled(true);

        Hero saved = heroRepository.save(hero);

        heroRepository.delete(saved.getId());

        Optional<Hero> found = heroRepository.findById(saved.getId());
        assertFalse(found.isPresent());
    }
}