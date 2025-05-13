package br.com.gubee.interview.core.adapters.persistence;

import br.com.gubee.interview.core.domain.Hero;
import br.com.gubee.interview.core.domain.enums.Race;
import br.com.gubee.interview.core.application.ports.repositories.HeroRepository;

import lombok.RequiredArgsConstructor;

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

    @Override
    public UUID create(Hero hero) {
        UUID id = UUID.randomUUID();
        final Map<String, Object> params = Map.of(
                "id", id,
                "name", hero.getName(),
                "race", hero.getRace().name(),
                "powerStatsId", hero.getPowerStatsId());

        namedParameterJdbcTemplate.update(
                "INSERT INTO hero (id, name, race, power_stats_id) VALUES (:id, :name, :race, :powerStatsId)",
                params);

        return id;
    }

    @Override
    public Optional<Hero> findById(UUID id) {
        final Map<String, Object> params = Map.of("id", id);
        return namedParameterJdbcTemplate.query(
                "SELECT * FROM hero WHERE id = :id",
                params,
                heroRowMapper()).stream().findFirst();
    }

    @Override
    public List<Hero> findByName(String name) {
        final Map<String, Object> params = Map.of("name", "%" + name + "%");
        return namedParameterJdbcTemplate.query(
                "SELECT * FROM hero WHERE name ILIKE :name",
                params,
                heroRowMapper());
    }

    @Override
    public void update(UUID id, Hero updatedHero) {
        final Map<String, Object> params = Map.of(
                "id", id,
                "name", updatedHero.getName(),
                "race", updatedHero.getRace().name(),
                "powerStatsId", updatedHero.getPowerStatsId());
        namedParameterJdbcTemplate.update(
                "UPDATE hero SET name = :name, race = :race, power_stats_id = :powerStatsId WHERE id = :id",
                params);
    }

    @Override
    public void delete(UUID id) {
        final Map<String, Object> params = Map.of("id", id);
        namedParameterJdbcTemplate.update("DELETE FROM hero WHERE id = :id", params);
    }

    private RowMapper<Hero> heroRowMapper() {
        return (rs, rowNum) -> Hero.builder()
                .id(UUID.fromString(rs.getString("id")))
                .name(rs.getString("name"))
                .race(Race.valueOf(rs.getString("race")))
                .powerStatsId(UUID.fromString(rs.getString("power_stats_id")))
                .build();
    }
} 