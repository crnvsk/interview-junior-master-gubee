package br.com.gubee.interview.core.adapters.persistence;

import br.com.gubee.interview.core.domain.PowerStats;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class JdbcPowerStatsRepositoryIT {

    private JdbcPowerStatsRepository repository;
    private NamedParameterJdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        DataSource dataSource = new DriverManagerDataSource(
                "jdbc:h2:mem:testdb;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
                "sa",
                "");
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        repository = new JdbcPowerStatsRepository(jdbcTemplate);

        resetDatabase();
    }

    private void resetDatabase() {
        jdbcTemplate.getJdbcTemplate().execute("DROP TABLE IF EXISTS power_stats");
        jdbcTemplate.getJdbcTemplate().execute("""
                    CREATE TABLE power_stats (
                        id UUID PRIMARY KEY,
                        strength INT NOT NULL,
                        agility INT NOT NULL,
                        dexterity INT NOT NULL,
                        intelligence INT NOT NULL
                    )
                """);
    }

    @Test
    void shouldCreateAndFindPowerStatsById() {
        PowerStats powerStats = PowerStats.builder()
                .strength(10)
                .agility(8)
                .dexterity(7)
                .intelligence(9)
                .build();

        UUID powerStatsId = repository.create(powerStats);
        Optional<PowerStats> foundPowerStats = repository.findById(powerStatsId);

        assertThat(foundPowerStats).isPresent();
        assertThat(foundPowerStats.get().getStrength()).isEqualTo(10);
        assertThat(foundPowerStats.get().getAgility()).isEqualTo(8);
        assertThat(foundPowerStats.get().getDexterity()).isEqualTo(7);
        assertThat(foundPowerStats.get().getIntelligence()).isEqualTo(9);
    }

    @Test
    void shouldReturnEmptyWhenPowerStatsNotFound() {
        Optional<PowerStats> foundPowerStats = repository.findById(UUID.randomUUID());

        assertThat(foundPowerStats).isEmpty();
    }

    @Test
    void shouldDeletePowerStatsById() {
        PowerStats powerStats = PowerStats.builder()
                .strength(5)
                .agility(6)
                .dexterity(7)
                .intelligence(8)
                .build();
        UUID powerStatsId = repository.create(powerStats);

        repository.delete(powerStatsId);
        Optional<PowerStats> foundPowerStats = repository.findById(powerStatsId);

        assertThat(foundPowerStats).isEmpty();
    }

    @Test
    void shouldNotDeleteNonExistentPowerStats() {
        UUID nonExistentId = UUID.randomUUID();

        repository.delete(nonExistentId);
        Optional<PowerStats> foundPowerStats = repository.findById(nonExistentId);

        assertThat(foundPowerStats).isEmpty();
    }
}