package br.com.gubee.interview.core.adapters.persistence;

import br.com.gubee.interview.core.domain.PowerStats;
import br.com.gubee.interview.core.application.ports.repositories.PowerStatsRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JdbcPowerStatsRepository implements PowerStatsRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String INSERT_POWER_STATS_SQL = "INSERT INTO power_stats (id, strength, agility, dexterity, intelligence) "
            +
            "VALUES (:id, :strength, :agility, :dexterity, :intelligence)";
    private static final String SELECT_POWER_STATS_BY_ID_SQL = "SELECT * FROM power_stats WHERE id = :id";
    private static final String DELETE_POWER_STATS_SQL = "DELETE FROM power_stats WHERE id = :id";

    @Override
    public UUID create(PowerStats powerStats) {
        UUID id = UUID.randomUUID();
        final Map<String, Object> params = Map.of(
                "id", id,
                "strength", powerStats.getStrength(),
                "agility", powerStats.getAgility(),
                "dexterity", powerStats.getDexterity(),
                "intelligence", powerStats.getIntelligence());

        try {
            namedParameterJdbcTemplate.update(INSERT_POWER_STATS_SQL, params);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to create power stats", e);
        }

        return id;
    }

    @Override
    public Optional<PowerStats> findById(UUID id) {
        final Map<String, Object> params = Map.of("id", id);
        try {
            return namedParameterJdbcTemplate.query(
                    SELECT_POWER_STATS_BY_ID_SQL,
                    params,
                    powerStatsRowMapper()).stream().findFirst();
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to find power stats by ID", e);
        }
    }

    @Override
    public void delete(UUID id) {
        final Map<String, Object> params = Map.of("id", id);
        try {
            namedParameterJdbcTemplate.update(DELETE_POWER_STATS_SQL, params);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to delete power stats", e);
        }
    }

    private RowMapper<PowerStats> powerStatsRowMapper() {
        return (rs, rowNum) -> {
            try {
                return PowerStats.builder()
                        .id(UUID.fromString(rs.getString("id")))
                        .strength(rs.getInt("strength"))
                        .agility(rs.getInt("agility"))
                        .dexterity(rs.getInt("dexterity"))
                        .intelligence(rs.getInt("intelligence"))
                        .build();
            } catch (Exception e) {
                throw new RuntimeException("Failed to map power stats row", e);
            }
        };
    }
}