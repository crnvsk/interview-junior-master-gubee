package br.com.gubee.interview.core.application.services;

import br.com.gubee.interview.core.application.stubs.HeroMapperStub;
import br.com.gubee.interview.core.application.stubs.InMemoryHeroRepository;
import br.com.gubee.interview.core.application.stubs.InMemoryPowerstatsRepository;
import br.com.gubee.interview.core.domain.enums.Race;
import br.com.gubee.interview.core.domain.hero.Hero;
import br.com.gubee.interview.core.domain.hero.HeroResponseDTO;
import br.com.gubee.interview.core.domain.powerstats.Powerstats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FindHeroByNameServiceTest {
    private InMemoryHeroRepository heroRepository;
    private InMemoryPowerstatsRepository powerstatsRepository;
    private HeroMapperStub heroMapper;
    private FindHeroByNameService findHeroByNameService;

    @BeforeEach
    void setUp() {
        heroRepository = new InMemoryHeroRepository();
        powerstatsRepository = new InMemoryPowerstatsRepository();
        heroMapper = new HeroMapperStub();
        findHeroByNameService = new FindHeroByNameService(heroRepository, powerstatsRepository, heroMapper);
    }

    @Test
    void shouldReturnHeroResponseDTOWhenHeroExistsByName() {
        UUID heroId = UUID.randomUUID();
        UUID statsId = UUID.randomUUID();
        Instant now = Instant.now();

        Powerstats stats = new Powerstats(statsId, 10, 9, 8, 7, now, now);
        powerstatsRepository.save(stats);

        Hero hero = new Hero(heroId, "FindByName", Race.HUMAN, statsId, now, now, true);
        heroRepository.save(hero);

        Optional<HeroResponseDTO> result = findHeroByNameService.findHeroByName("FindByName");

        assertTrue(result.isPresent());
        assertEquals("FindByName", result.get().name());
        assertEquals(10, result.get().powerStats().strength());
    }

    @Test
    void shouldReturnEmptyWhenHeroDoesNotExistByName() {
        Optional<HeroResponseDTO> result = findHeroByNameService.findHeroByName("NotFound");
        assertTrue(result.isEmpty());
    }
}