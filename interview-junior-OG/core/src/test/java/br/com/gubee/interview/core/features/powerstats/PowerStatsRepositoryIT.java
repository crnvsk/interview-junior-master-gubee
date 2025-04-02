package br.com.gubee.interview.core.features.powerstats;

import br.com.gubee.interview.model.PowerStats;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("it")
public class PowerStatsRepositoryIT {

    @Autowired
    private PowerStatsRepository powerStatsRepository;

    @Test
    void findByIdShouldReturnPowerStats() {
        // Arrange: Cria um PowerStats e salva no banco de dados
        PowerStats powerStats = PowerStats.builder()
                .strength(10)
                .agility(8)
                .dexterity(7)
                .intelligence(9)
                .build();
        UUID powerStatsId = powerStatsRepository.create(powerStats);

        // Act: Recupera o PowerStats pelo ID
        Optional<PowerStats> foundPowerStats = powerStatsRepository.findById(powerStatsId);

        // Assert: Verifica se os atributos foram recuperados corretamente
        assertTrue(foundPowerStats.isPresent());
        assertEquals(10, foundPowerStats.get().getStrength());
        assertEquals(8, foundPowerStats.get().getAgility());
        assertEquals(7, foundPowerStats.get().getDexterity());
        assertEquals(9, foundPowerStats.get().getIntelligence());
    }

    @Test
    void findByIdShouldReturnEmptyWhenNotFound() {
        // Act: Tenta recuperar um PowerStats com um ID inexistente
        Optional<PowerStats> foundPowerStats = powerStatsRepository.findById(UUID.randomUUID());

        // Assert: Verifica se o resultado é vazio
        assertTrue(foundPowerStats.isEmpty());
    }
}