package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.enums.Race;
import lombok.RequiredArgsConstructor;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class HeroRepository {

        private static final String CREATE_HERO_QUERY = "INSERT INTO hero" +
                        " (name, race, power_stats_id)" +
                        " VALUES (:name, :race, :powerStatsId) RETURNING id";

        private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

        public UUID create(Hero hero) {
                final Map<String, Object> params = Map.of("name", hero.getName(),
                                "race", hero.getRace().name(),
                                "powerStatsId", hero.getPowerStatsId());

                return namedParameterJdbcTemplate.queryForObject(
                                CREATE_HERO_QUERY,
                                params,
                                UUID.class);
        }

        private static final String FIND_HERO_BY_ID_QUERY = "SELECT * FROM hero WHERE id = :id";

        Optional<Hero> findById(UUID id) {
                final Map<String, Object> params = Map.of("id", id);
                return namedParameterJdbcTemplate.query(FIND_HERO_BY_ID_QUERY, params, heroRowMapper())
                                .stream()
                                .findFirst();
        }

        private RowMapper<Hero> heroRowMapper() {
                return (rs, rowNum) -> Hero.builder()
                                .id(UUID.fromString(rs.getString("id")))
                                .name(rs.getString("name"))
                                .race(Race.valueOf(rs.getString("race")))
                                .powerStatsId(UUID.fromString(rs.getString("power_stats_id")))
                                .createdAt(rs.getTimestamp("created_at").toInstant())
                                .updatedAt(rs.getTimestamp("updated_at").toInstant())
                                .enabled(rs.getBoolean("enabled"))
                                .build();
        }

        private static final String FIND_HEROES_BY_NAME_QUERY = "SELECT * FROM hero WHERE name ILIKE :name";

        public List<Hero> findByName(String name) {
                final Map<String, Object> params = Map.of("name", "%" + name + "%");
                return namedParameterJdbcTemplate.query(FIND_HEROES_BY_NAME_QUERY, params, heroRowMapper());
        }

        private static final String UPDATE_HERO_QUERY = "UPDATE hero SET " +
                        "name = :name, " +
                        "race = :race, " +
                        "power_stats_id = :powerStatsId, " +
                        "updated_at = NOW() " +
                        "WHERE id = :id";

        public void update(UUID id, Hero updatedHero) {
                final Map<String, Object> params = Map.of(
                                "id", id,
                                "name", updatedHero.getName(),
                                "race", updatedHero.getRace().name(),
                                "powerStatsId", updatedHero.getPowerStatsId());
                namedParameterJdbcTemplate.update(UPDATE_HERO_QUERY, params);
        }
}
