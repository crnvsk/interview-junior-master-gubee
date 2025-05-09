package br.com.gubee.interview.core.application.hero.commands;

import br.com.gubee.interview.core.domain.Hero;
import br.com.gubee.interview.core.domain.PowerStats;
import br.com.gubee.interview.core.adapters.persistence.HeroRepository;
import br.com.gubee.interview.core.application.powerstats.queries.FindPowerStatsByIdQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompareHeroesCommand {
    private final HeroRepository heroRepository;
    private final FindPowerStatsByIdQuery findPowerStatsByIdQuery;

    public Map<String, Object> execute(UUID hero1Id, UUID hero2Id) {
        Optional<Hero> hero1 = heroRepository.findById(hero1Id);
        Optional<Hero> hero2 = heroRepository.findById(hero2Id);

        if (hero1.isEmpty() || hero2.isEmpty()) {
            return null;
        }

        Hero h1 = hero1.get();
        Hero h2 = hero2.get();

        PowerStats stats1 = findPowerStatsByIdQuery.execute(h1.getPowerStatsId())
                .orElseThrow(() -> new NoSuchElementException("PowerStats not found for ID: " + h1.getPowerStatsId()));
        PowerStats stats2 = findPowerStatsByIdQuery.execute(h2.getPowerStatsId())
                .orElseThrow(() -> new NoSuchElementException("PowerStats not found for ID: " + h2.getPowerStatsId()));

        return Map.of(
                "hero1Id", h1.getId(),
                "hero2Id", h2.getId(),
                "strengthDifference", stats1.getStrength() - stats2.getStrength(),
                "agilityDifference", stats1.getAgility() - stats2.getAgility(),
                "dexterityDifference", stats1.getDexterity() - stats2.getDexterity(),
                "intelligenceDifference", stats1.getIntelligence() - stats2.getIntelligence()
        );
    }
}