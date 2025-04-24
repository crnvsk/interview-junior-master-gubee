package br.com.gubee.interview.core.features.hero;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.enums.Race;

@SpringBootTest
@AutoConfigureMockMvc
class HeroIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HeroRepository heroRepository;

    @Test
    void deveCadastrarHeroiComSucesso() throws Exception {
        String requestBody = """
                    {
                        "name": "Superman",
                        "race": "ALIEN",
                        "strength": 10,
                        "agility": 8,
                        "dexterity": 7,
                        "intelligence": 9
                    }
                """;

        mockMvc.perform(post("/api/v1/heroes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated());

        assertTrue(heroRepository.findByName("Superman").size() > 0);
    }

    @Test
    void deveRetornarHeroiPorId() throws Exception {
        UUID heroId = heroRepository.create(Hero.builder()
                .name("Batman")
                .race(Race.HUMAN)
                .powerStatsId(UUID.randomUUID())
                .build());
        
        Hero hero = heroRepository.findById(heroId).orElseThrow();

        mockMvc.perform(get("/api/v1/heroes/" + hero.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Batman"));
    }

    @Test
    void deveAtualizarHeroi() throws Exception {
        UUID heroId = heroRepository.create(Hero.builder()
                .name("Flash")
                .race(Race.HUMAN)
                .powerStatsId(UUID.randomUUID())
                .build());

        Hero hero = heroRepository.findById(heroId).orElseThrow();

        String updateRequest = """
                    {
                        "name": "Flash Updated",
                        "race": "HUMAN"
                    }
                """;

        mockMvc.perform(put("/api/v1/heroes/" + hero.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateRequest))
                .andExpect(status().isNoContent());

        Hero updatedHero = heroRepository.findById(hero.getId()).orElseThrow();
        assertEquals("Flash Updated", updatedHero.getName());
    }

    @Test
    void deveDeletarHeroi() throws Exception {
        UUID heroId = heroRepository.create(Hero.builder()
                .name("Green Lantern")
                .race(Race.HUMAN)
                .powerStatsId(UUID.randomUUID())
                .build());
        
        Hero hero = heroRepository.findById(heroId).orElseThrow();

        mockMvc.perform(delete("/api/v1/heroes/" + hero.getId()))
                .andExpect(status().isNoContent());

        assertTrue(heroRepository.findById(hero.getId()).isEmpty());
    }

    @Test
    void deveCompararHerois() throws Exception {
        UUID hero1Id = heroRepository.create(Hero.builder()
                .name("Aquaman")
                .race(Race.HUMAN)
                .powerStatsId(UUID.randomUUID())
                .build());

        UUID hero2Id = heroRepository.create(Hero.builder()
                .name("Flash")
                .race(Race.HUMAN)
                .powerStatsId(UUID.randomUUID())
                .build());
        Hero hero1 = heroRepository.findById(hero1Id).orElseThrow();
        Hero hero2 = heroRepository.findById(hero2Id).orElseThrow();

        mockMvc.perform(post("/api/v1/heroes/compare")
                .param("hero1Id", hero1.getId().toString())
                .param("hero2Id", hero2.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.strengthDifference").exists());
    }
}
