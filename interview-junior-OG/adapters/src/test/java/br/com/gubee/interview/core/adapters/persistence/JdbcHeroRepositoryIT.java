package br.com.gubee.interview.core.adapters.persistence;

import br.com.gubee.interview.core.domain.Hero;
import br.com.gubee.interview.core.domain.enums.Race;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class JdbcHeroRepositoryIT {

    private JdbcHeroRepository repository;
    private NamedParameterJdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        DataSource dataSource = new DriverManagerDataSource(
                "jdbc:h2:mem:testdb;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
                "sa",
                "");
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        repository = new JdbcHeroRepository(jdbcTemplate);

        resetDatabase();
    }

    private void resetDatabase() {
        jdbcTemplate.getJdbcTemplate().execute("DROP TABLE IF EXISTS hero");
        jdbcTemplate.getJdbcTemplate().execute("""
                    CREATE TABLE hero (
                        id UUID PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        race VARCHAR(50) NOT NULL,
                        power_stats_id UUID NOT NULL
                    )
                """);
    }

    @Test
    void shouldCreateAndFindHeroById() {
        Hero hero = Hero.builder()
                .name("Superman")
                .race(Race.ALIEN)
                .powerStatsId(UUID.randomUUID())
                .build();

        UUID heroId = repository.create(hero);
        Optional<Hero> foundHero = repository.findById(heroId);

        assertThat(foundHero).isPresent();
        assertThat(foundHero.get().getName()).isEqualTo("Superman");
        assertThat(foundHero.get().getRace()).isEqualTo(Race.ALIEN);
    }

    @Test
    void shouldReturnEmptyWhenHeroNotFound() {
        Optional<Hero> foundHero = repository.findById(UUID.randomUUID());

        assertThat(foundHero).isEmpty();
    }

    @Test
    void shouldFindHeroesByName() {
        Hero hero1 = Hero.builder()
                .name("Superman")
                .race(Race.ALIEN)
                .powerStatsId(UUID.randomUUID())
                .build();
        Hero hero2 = Hero.builder()
                .name("Supergirl")
                .race(Race.HUMAN)
                .powerStatsId(UUID.randomUUID())
                .build();
        repository.create(hero1);
        repository.create(hero2);

        List<Hero> foundHeroes = repository.findByName("Super");

        assertThat(foundHeroes).hasSize(2);
        assertThat(foundHeroes).extracting(Hero::getName).contains("Superman", "Supergirl");
    }

    @Test
    void shouldUpdateHero() {
        Hero hero = Hero.builder()
                .name("Superman")
                .race(Race.HUMAN)
                .powerStatsId(UUID.randomUUID())
                .build();
        UUID heroId = repository.create(hero);

        Hero updatedHero = Hero.builder()
                .id(heroId)
                .name("Superman Updated")
                .race(Race.ALIEN)
                .powerStatsId(hero.getPowerStatsId())
                .build();

        repository.update(heroId, updatedHero);
        Optional<Hero> foundHero = repository.findById(heroId);

        assertThat(foundHero).isPresent();
        assertThat(foundHero.get().getName()).isEqualTo("Superman Updated");
        assertThat(foundHero.get().getRace()).isEqualTo(Race.ALIEN);
    }

    @Test
    void shouldNotUpdateNonExistentHero() {
        Hero updatedHero = Hero.builder()
                .id(UUID.randomUUID())
                .name("Non-existent Hero")
                .race(Race.HUMAN)
                .powerStatsId(UUID.randomUUID())
                .build();

        repository.update(updatedHero.getId(), updatedHero);
        Optional<Hero> foundHero = repository.findById(updatedHero.getId());

        assertThat(foundHero).isEmpty();
    }

    @Test
    void shouldDeleteHeroById() {
        Hero hero = Hero.builder()
                .name("Batman")
                .race(Race.HUMAN)
                .powerStatsId(UUID.randomUUID())
                .build();
        UUID heroId = repository.create(hero);

        repository.delete(heroId);
        Optional<Hero> foundHero = repository.findById(heroId);

        assertThat(foundHero).isEmpty();
    }

    @Test
    void shouldNotDeleteNonExistentHero() {
        UUID nonExistentHeroId = UUID.randomUUID();

        repository.delete(nonExistentHeroId);
        Optional<Hero> foundHero = repository.findById(nonExistentHeroId);

        assertThat(foundHero).isEmpty();
    }
}