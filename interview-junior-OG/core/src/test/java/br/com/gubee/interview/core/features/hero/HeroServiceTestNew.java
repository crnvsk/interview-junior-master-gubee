package br.com.gubee.interview.core.features.hero;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import br.com.gubee.interview.core.features.powerstats.InMemoryPowerStatsRepository;
import br.com.gubee.interview.core.features.powerstats.InMemoryPowerStatsService;
import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.enums.Race;
import br.com.gubee.interview.model.request.CreateHeroRequest;

public class HeroServiceTestNew {
    private HeroService heroService;
    private InMemoryHeroRepository heroRepository;
    private InMemoryPowerStatsRepository powerStatsRepository;
    private InMemoryPowerStatsService powerStatsService;

    @BeforeEach
    public void setUp() {
        this.heroRepository = new InMemoryHeroRepository();
        this.powerStatsRepository = new InMemoryPowerStatsRepository();
        this.powerStatsService = new InMemoryPowerStatsService(powerStatsRepository);
        this.heroService = new HeroService(heroRepository, powerStatsService);
    }

    @Test
    void criarHeroiComDadosValidos() {
        CreateHeroRequest request = CreateHeroRequest.builder()
                .name("Superman")
                .race(Race.ALIEN)
                .strength(10)
                .agility(8)
                .dexterity(7)
                .intelligence(9)
                .build();
        
        UUID heroId = heroService.create(request);
        assertNotNull(heroId);
        Hero hero = heroRepository.findById(heroId).orElse(null);
        assertNotNull(hero);
        assertEquals("Superman", hero.getName());
    }

    @Test
    void criarHeroiComDadosInvalidosDeveLancarExcecao() {
        CreateHeroRequest request = CreateHeroRequest.builder()
                .name("")
                .race(Race.ALIEN)
                .strength(10)
                .agility(8)
                .dexterity(7)
                .intelligence(9)
                .build();
        try {
            heroService.create(request);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("message.name.mandatory"));
        }
    }

    @Test
    void encontrarHeroiPorIdExistenteDeveRetornarHeroi() {
        UUID heroId = heroService.create(CreateHeroRequest.builder()
                .name("Batman")
                .race(Race.HUMAN)
                .strength(8)
                .agility(7)
                .dexterity(6)
                .intelligence(9)
                .build());
        
        Optional<Hero> hero = heroService.findById(heroId);
        assertTrue(hero.isPresent());
        assertEquals("Batman", hero.get().getName());
        assertEquals(Race.HUMAN, hero.get().getRace());
    }

    @Test
    void encontrarHeroiPorIdInexistenteDeveRetornarVazio() {
        UUID heroId = UUID.randomUUID();
        Optional<Hero> hero = heroService.findById(heroId);
        assertTrue(hero.isEmpty());
    }

    @Test
    void encontrarHeroiPorNomeExistenteDeveRetornarHeroi() {
        heroService.create(CreateHeroRequest.builder()
                .name("Flash")
                .race(Race.HUMAN)
                .strength(7)
                .agility(10)
                .dexterity(8)
                .intelligence(6)
                .build());

        List<Hero> heroes = heroService.findByName("Flash");
    
        assertNotNull(heroes);

        for (Hero hero : heroes) {
            assertEquals("Flash", hero.getName());
        }
   }

   @Test
   void encontrarHeroiPorNomeInexistenteDeveRetornarListaVazia() {
       List<Hero> heroes = heroService.findByName("Inexistente");
       assertNotNull(heroes);
       assertTrue(heroes.isEmpty());
   }

   @Test
   void atualizarHeroiExistenteDeveRetornarTrue() {
       UUID heroId = heroService.create(CreateHeroRequest.builder()
                .name("Aquaman")
                .race(Race.HUMAN)
                .strength(9)
                .agility(8)
                .dexterity(7)
                .intelligence(8)
                .build());
        
        Hero updatedHero = Hero.builder()
                .name("Aquaman Updated")
                .race(Race.HUMAN)
                .build();
        
        boolean result = heroService.updateHero(heroId, updatedHero);
        assertTrue(result);
        Hero hero = heroRepository.findById(heroId).orElse(null);
        assertNotNull(hero);
        assertEquals("Aquaman Updated", hero.getName());
   }
   
   @Test
   void atualizarHeroiInexistenteDeveRetornarFalse() {
       UUID heroId = UUID.randomUUID();
       Hero updatedHero = Hero.builder()
               .name("Inexistente")
               .build();
       boolean result = heroService.updateHero(heroId, updatedHero);
        assertTrue(!result);
   }

   @Test
   void deletarHeroiExistenteDeveRetornarTrue() {
       UUID heroId = heroService.create(CreateHeroRequest.builder()
               .name("Green Lantern")
               .build());
       boolean result = heroService.deleteHero(heroId);
       assertTrue(result);
   }

   @Test
   void deletarHeroiInexistenteDeveRetornarFalse() {
       UUID heroId = UUID.randomUUID();
       boolean result = heroService.deleteHero(heroId);
       assertTrue(!result);
   }

   @Test
   void  compararHeroisExistentesDeveRetornarComparacao() {
       UUID hero1Id = heroService.create(CreateHeroRequest.builder()
            .name("Aquaman")
            .race(Race.HUMAN)
            .strength(10)
            .agility(8)
            .dexterity(7)
            .intelligence(7)
            .build());
        
        UUID hero2Id = heroService.create(CreateHeroRequest.builder()
            .name("Flash")
            .race(Race.HUMAN)
            .strength(8)
            .agility(10)
            .dexterity(7)
            .intelligence(8)
            .build());

        Map<String, Object> comparacao = heroService.compareHeroes(hero1Id, hero2Id);

        assertNotNull(comparacao);
        assertEquals(2, comparacao.get("strengthDifference"));
        assertEquals(-2, comparacao.get("agilityDifference"));
        assertEquals(0, comparacao.get("dexterityDifference"));
        assertEquals(-1, comparacao.get("intelligenceDifference"));
   }

   @Test
    void compararHeroisInexistentesDeveRetornarNulo() {
         UUID hero1Id = UUID.randomUUID();
         UUID hero2Id = UUID.randomUUID();
         Map<String, Object> comparacao = heroService.compareHeroes(hero1Id, hero2Id);
         assertNull(comparacao);
    }
}
    
