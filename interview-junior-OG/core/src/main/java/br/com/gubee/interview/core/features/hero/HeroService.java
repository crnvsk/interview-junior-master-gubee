package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.core.features.powerstats.PowerStatsService;
import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.PowerStats;
import br.com.gubee.interview.model.request.CreateHeroRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class HeroService {

    private final HeroRepository heroRepository;
    private final PowerStatsService powerStatsService;

    @Transactional
    public UUID create(CreateHeroRequest createHeroRequest) {
        PowerStats powerStats = PowerStats.builder()
                .strength(createHeroRequest.getStrength())
                .agility(createHeroRequest.getAgility())
                .dexterity(createHeroRequest.getDexterity())
                .intelligence(createHeroRequest.getIntelligence())
                .build();
        UUID powerStatsId = powerStatsService.create(powerStats);

        Hero hero = new Hero(createHeroRequest, powerStatsId);

        return heroRepository.create(hero);
    }

    public Optional<Hero> findById(UUID id) {
        return heroRepository.findById(id);
    }

    public List<Hero> findByName(String name) {
        return heroRepository.findByName(name);
    }

    @Transactional
    public boolean updateHero(UUID id, Hero updatedHero) {
        Optional<Hero> existingHero = heroRepository.findById(id);
        if (existingHero.isEmpty()) {
            return false;
        }
        heroRepository.update(id, updatedHero);
        return true;
    }

    @Transactional
    public boolean deleteHero(UUID id) {
        Optional<Hero> existingHero = heroRepository.findById(id);
        if (existingHero.isEmpty()) {
            return false;
        }
        heroRepository.delete(id);
        return true;
    }

    public Map<String, Object> compareHeroes(UUID hero1Id, UUID hero2Id) {
        Optional<Hero> hero1 = heroRepository.findById(hero1Id);
        Optional<Hero> hero2 = heroRepository.findById(hero2Id);

        if (hero1.isEmpty() || hero2.isEmpty()) {
            return null;
        }

        Hero h1 = hero1.get();
        Hero h2 = hero2.get();

        PowerStats stats1 = powerStatsService.findById(h1.getPowerStatsId())
                .orElseThrow(() -> new NoSuchElementException("PowerStats not found for ID: " + h1.getPowerStatsId()));
        PowerStats stats2 = powerStatsService.findById(h2.getPowerStatsId())
                .orElseThrow(() -> new NoSuchElementException("PowerStats not found for ID: " + h2.getPowerStatsId()));

        Map<String, Object> comparisonResult = Map.of(
                "hero1Id", h1.getId(),
                "hero2Id", h2.getId(),
                "strengthDifference", stats1.getStrength() - stats2.getStrength(),
                "agilityDifference", stats1.getAgility() - stats2.getAgility(),
                "dexterityDifference", stats1.getDexterity() - stats2.getDexterity(),
                "intelligenceDifference", stats1.getIntelligence() - stats2.getIntelligence());

        return comparisonResult;
    }
}