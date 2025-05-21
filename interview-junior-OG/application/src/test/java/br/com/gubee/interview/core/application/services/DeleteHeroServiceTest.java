package br.com.gubee.interview.core.application.services;

import br.com.gubee.interview.core.application.stubs.InMemoryHeroRepository;
import br.com.gubee.interview.core.domain.enums.Race;
import br.com.gubee.interview.core.domain.hero.Hero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DeleteHeroServiceTest {
    private InMemoryHeroRepository heroRepository;
    private DeleteHeroService deleteHeroService;

    @BeforeEach
    void setUp() {
        heroRepository = new InMemoryHeroRepository();
        deleteHeroService = new DeleteHeroService(heroRepository);
    }

    @Test
    void shouldDeleteHeroById() {
        UUID heroId = UUID.randomUUID();
        Hero hero = new Hero(heroId, "DeleteMe", Race.HUMAN, UUID.randomUUID(), Instant.now(), Instant.now(), true);
        heroRepository.save(hero);

        assertTrue(heroRepository.findById(heroId).isPresent());

        deleteHeroService.deleteHero(heroId);

        assertFalse(heroRepository.findById(heroId).isPresent());
    }
}