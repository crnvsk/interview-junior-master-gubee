package br.com.gubee.interview.core.application.services;

import br.com.gubee.interview.core.application.stubs.InMemoryHeroRepository;
import br.com.gubee.interview.core.application.stubs.InMemoryPowerstatsRepository;
import br.com.gubee.interview.core.domain.enums.Race;
import br.com.gubee.interview.core.domain.hero.Hero;
import br.com.gubee.interview.core.domain.hero.HeroRequestDTO;
import br.com.gubee.interview.core.domain.powerstats.Powerstats;
import br.com.gubee.interview.core.domain.powerstats.PowerstatsDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UpdateHeroServiceTest {
    private InMemoryHeroRepository heroRepository;
    private InMemoryPowerstatsRepository powerstatsRepository;
    private UpdateHeroService updateHeroService;

    @BeforeEach
    void setUp() {
        heroRepository = new InMemoryHeroRepository();
        powerstatsRepository = new InMemoryPowerstatsRepository();
        updateHeroService = new UpdateHeroService(
                heroRepository,
                heroRepository,
                powerstatsRepository,
                powerstatsRepository);
    }

    @Test
    void shouldUpdateHeroAndPowerstats() {
        UUID heroId = UUID.randomUUID();
        UUID statsId = UUID.randomUUID();
        Instant now = Instant.now();

        Powerstats powerstats = new Powerstats(statsId, 10, 9, 8, 7, now, now);
        powerstatsRepository.save(powerstats);

        Hero hero = new Hero(heroId, "OldName", Race.HUMAN, statsId, now, now, true);
        heroRepository.save(hero);

        PowerstatsDTO updatedStatsDTO = new PowerstatsDTO(1, 2, 3, 4);
        HeroRequestDTO updatedHeroDTO = new HeroRequestDTO("NewName", Race.ALIEN, updatedStatsDTO, false);

        updateHeroService.updateHero(heroId, updatedHeroDTO);

        Hero updatedHero = heroRepository.findById(heroId).orElseThrow();
        Powerstats updatedStats = powerstatsRepository.findById(statsId).orElseThrow();

        assertEquals("NewName", updatedHero.getName());
        assertEquals(Race.ALIEN, updatedHero.getRace());
        assertFalse(updatedHero.isEnabled());
        assertEquals(1, updatedStats.getStrength());
        assertEquals(2, updatedStats.getAgility());
        assertEquals(3, updatedStats.getDexterity());
        assertEquals(4, updatedStats.getIntelligence());
    }
}