package br.com.gubee.interview.core.features.powerstats;

import br.com.gubee.interview.model.PowerStats;
import lombok.RequiredArgsConstructor;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PowerStatsRepository {

    private static final String CREATE_POWER_STATS_QUERY = "INSERT INTO power_stats" +
            " (strength, agility, dexterity, intelligence)" +
            " VALUES (:strength, :agility, :dexterity, :intelligence) RETURNING id";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    UUID create(PowerStats powerStats) {
        return namedParameterJdbcTemplate.queryForObject(
                CREATE_POWER_STATS_QUERY,
                new BeanPropertySqlParameterSource(powerStats),
                UUID.class);
    }

    private static final String FIND_POWER_STATS_BY_ID_QUERY = "SELECT * FROM power_stats WHERE id = :id";

    public Optional<PowerStats> findById(UUID id) {
        final Map<String, Object> params = Map.of("id", id);
        return namedParameterJdbcTemplate.query(FIND_POWER_STATS_BY_ID_QUERY, params, powerStatsRowMapper())
                .stream()
                .findFirst();
    }

    public void delete(UUID id) {
        String sql = "DELETE FROM power_stats WHERE id = :id";
        Map<String, Object> params = Map.of("id", id);
        namedParameterJdbcTemplate.update(sql, params);
    }

    private RowMapper<PowerStats> powerStatsRowMapper() {
        return (rs, rowNum) -> PowerStats.builder()
                .id(UUID.fromString(rs.getString("id")))
                .strength(rs.getInt("strength"))
                .agility(rs.getInt("agility"))
                .dexterity(rs.getInt("dexterity"))
                .intelligence(rs.getInt("intelligence"))
                .createdAt(rs.getTimestamp("created_at").toInstant())
                .updatedAt(rs.getTimestamp("updated_at").toInstant())
                .build();
    }
}
