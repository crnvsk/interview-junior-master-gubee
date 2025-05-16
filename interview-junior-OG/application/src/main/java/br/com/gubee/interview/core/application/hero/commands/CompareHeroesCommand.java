package br.com.gubee.interview.core.application.hero.commands;

import br.com.gubee.interview.core.domain.Hero;
import br.com.gubee.interview.core.domain.PowerStats;
import br.com.gubee.interview.core.application.ports.repositories.HeroRepository;
import br.com.gubee.interview.core.application.powerstats.queries.FindPowerStatsByIdQuery;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompareHeroesCommand {
    private final HeroRepository heroRepository;
    private final FindPowerStatsByIdQuery findPowerStatsByIdQuery;

    @Transactional
    public Map<String, Object> execute(UUID hero1Id, UUID hero2Id) {
        if (hero1Id == null || hero2Id == null) {
            throw new IllegalArgumentException("Hero ID cannot be null");
        }

        Hero hero1 = heroRepository.findById(hero1Id)
                .orElseThrow(() -> new IllegalArgumentException("Hero not found for given ID"));
        Hero hero2 = heroRepository.findById(hero2Id)
                .orElseThrow(() -> new IllegalArgumentException("Hero not found for given ID"));

        if (hero1.getPowerStatsId() == null) {
            throw new IllegalArgumentException("Hero does not have PowerStats");
        }
        if (hero2.getPowerStatsId() == null) {
            throw new IllegalArgumentException("Hero does not have PowerStats");
        }

        PowerStats stats1 = findPowerStatsByIdQuery.execute(hero1.getPowerStatsId())
                .orElseThrow(() -> new NoSuchElementException("PowerStats not found for given ID"));
        PowerStats stats2 = findPowerStatsByIdQuery.execute(hero2.getPowerStatsId())
                .orElseThrow(() -> new NoSuchElementException("PowerStats not found for given ID"));

        return Map.of(
                "hero1Id", hero1.getId(),
                "hero2Id", hero2.getId(),
                "strengthDifference", stats1.getStrength() - stats2.getStrength(),
                "agilityDifference", stats1.getAgility() - stats2.getAgility(),
                "dexterityDifference", stats1.getDexterity() - stats2.getDexterity(),
                "intelligenceDifference", stats1.getIntelligence() - stats2.getIntelligence());
    }
}