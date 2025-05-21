package br.com.gubee.interview.core.application.services;

import br.com.gubee.interview.core.application.stubs.HeroMapperStub;
import br.com.gubee.interview.core.application.stubs.InMemoryHeroRepository;
import br.com.gubee.interview.core.application.stubs.InMemoryPowerstatsRepository;
import br.com.gubee.interview.core.domain.enums.Race;
import br.com.gubee.interview.core.domain.hero.HeroRequestDTO;
import br.com.gubee.interview.core.domain.hero.HeroResponseDTO;
import br.com.gubee.interview.core.domain.powerstats.PowerstatsDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateHeroServiceTest {
    private InMemoryHeroRepository heroRepository;
    private InMemoryPowerstatsRepository powerstatsRepository;
    private HeroMapperStub heroMapper;
    private CreateHeroService createHeroService;

    @BeforeEach
    void setUp() {
        heroRepository = new InMemoryHeroRepository();
        powerstatsRepository = new InMemoryPowerstatsRepository();
        heroMapper = new HeroMapperStub();
        createHeroService = new CreateHeroService(heroRepository, powerstatsRepository, heroMapper);
    }

    @Test
    void shouldCreateHeroAndReturnHeroResponseDTO() {
        PowerstatsDTO powerstatsDTO = new PowerstatsDTO(10, 9, 8, 7);
        HeroRequestDTO requestDTO = new HeroRequestDTO("StubHero", Race.HUMAN, powerstatsDTO, true);

        HeroResponseDTO response = createHeroService.createHero(requestDTO);

        assertNotNull(response);
        assertEquals("StubHero", response.name());
        assertEquals(Race.HUMAN, response.race());
        assertEquals(10, response.powerStats().strength());
        assertEquals(9, response.powerStats().agility());
        assertEquals(8, response.powerStats().dexterity());
        assertEquals(7, response.powerStats().intelligence());
        assertTrue(response.enabled());
        assertNotNull(response.id());
        assertNotNull(response.createdAt());
        assertNotNull(response.updatedAt());
    }
}