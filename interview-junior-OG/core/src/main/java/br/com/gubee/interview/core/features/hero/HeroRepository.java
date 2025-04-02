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
}
