package br.com.gubee.interview.core.adapters.persistence;

import br.com.gubee.interview.core.domain.Hero;
import br.com.gubee.interview.core.domain.enums.Race;
import br.com.gubee.interview.core.application.ports.repositories.HeroRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JdbcHeroRepository implements HeroRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String INSERT_HERO_SQL = "INSERT INTO hero (id, name, race, power_stats_id) VALUES (:id, :name, :race, :powerStatsId)";
    private static final String SELECT_HERO_BY_ID_SQL = "SELECT * FROM hero WHERE id = :id";
    private static final String SELECT_HERO_BY_NAME_SQL = "SELECT * FROM hero WHERE name ILIKE :name";
    private static final String UPDATE_HERO_SQL = "UPDATE hero SET name = :name, race = :race, power_stats_id = :powerStatsId WHERE id = :id";
    private static final String DELETE_HERO_SQL = "DELETE FROM hero WHERE id = :id";

    @Override
    public UUID create(Hero hero) {
        UUID id = UUID.randomUUID();
        final Map<String, Object> params = Map.of(
                "id", id,
                "name", hero.getName(),
                "race", hero.getRace().name(),
                "powerStatsId", hero.getPowerStatsId());

        try {
            namedParameterJdbcTemplate.update(INSERT_HERO_SQL, params);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to create hero", e);
        }

        return id;
    }

    @Override
    public Optional<Hero> findById(UUID id) {
        final Map<String, Object> params = Map.of("id", id);
        try {
            return namedParameterJdbcTemplate.query(
                    SELECT_HERO_BY_ID_SQL,
                    params,
                    heroRowMapper()).stream().findFirst();
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to find hero by ID", e);
        }
    }

    @Override
    public List<Hero> findByName(String name) {
        final Map<String, Object> params = Map.of("name", "%" + name + "%");
        try {
            return namedParameterJdbcTemplate.query(
                    SELECT_HERO_BY_NAME_SQL,
                    params,
                    heroRowMapper());
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to find heroes by name", e);
        }
    }

    @Override
    public void update(UUID id, Hero updatedHero) {
        final Map<String, Object> params = Map.of(
                "id", id,
                "name", updatedHero.getName(),
                "race", updatedHero.getRace().name(),
                "powerStatsId", updatedHero.getPowerStatsId());
        try {
            namedParameterJdbcTemplate.update(UPDATE_HERO_SQL, params);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to update hero", e);
        }
    }

    @Override
    public void delete(UUID id) {
        final Map<String, Object> params = Map.of("id", id);
        try {
            namedParameterJdbcTemplate.update(DELETE_HERO_SQL, params);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to delete hero", e);
        }
    }

    private RowMapper<Hero> heroRowMapper() {
        return (rs, rowNum) -> {
            try {
                return Hero.builder()
                        .id(UUID.fromString(rs.getString("id")))
                        .name(rs.getString("name"))
                        .race(Race.valueOf(rs.getString("race")))
                        .powerStatsId(UUID.fromString(rs.getString("power_stats_id")))
                        .build();
            } catch (Exception e) {
                throw new RuntimeException("Failed to map hero row", e);
            }
        };
    }
}