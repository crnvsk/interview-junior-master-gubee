package br.com.gubee.interview.core.adapters.persistence;

import br.com.gubee.interview.core.domain.PowerStats;
import br.com.gubee.interview.core.application.ports.repositories.PowerStatsRepository;

import lombok.RequiredArgsConstructor;

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

    @Override
    public UUID create(PowerStats powerStats) {
        UUID id = UUID.randomUUID();
        final Map<String, Object> params = Map.of(
                "id", id,
                "strength", powerStats.getStrength(),
                "agility", powerStats.getAgility(),
                "dexterity", powerStats.getDexterity(),
                "intelligence", powerStats.getIntelligence()
        );

        namedParameterJdbcTemplate.update(
                "INSERT INTO power_stats (id, strength, agility, dexterity, intelligence) " +
                        "VALUES (:id, :strength, :agility, :dexterity, :intelligence)",
                params
        );

        return id;
    }

    @Override
    public Optional<PowerStats> findById(UUID id) {
        final Map<String, Object> params = Map.of("id", id);
        return namedParameterJdbcTemplate.query(
                "SELECT * FROM power_stats WHERE id = :id",
                params,
                powerStatsRowMapper()).stream().findFirst();
    }

    @Override
    public void delete(UUID id) {
        final Map<String, Object> params = Map.of("id", id);
        namedParameterJdbcTemplate.update("DELETE FROM power_stats WHERE id = :id", params);
    }

    private RowMapper<PowerStats> powerStatsRowMapper() {
        return (rs, rowNum) -> PowerStats.builder()
                .id(UUID.fromString(rs.getString("id")))
                .strength(rs.getInt("strength"))
                .agility(rs.getInt("agility"))
                .dexterity(rs.getInt("dexterity"))
                .intelligence(rs.getInt("intelligence"))
                .build();
    }
}