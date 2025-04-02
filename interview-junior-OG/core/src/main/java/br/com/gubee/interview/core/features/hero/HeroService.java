package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.request.CreateHeroRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HeroService {

    private final HeroRepository heroRepository;

    @Transactional
    public UUID create(CreateHeroRequest createHeroRequest) {
        UUID powerStatsId = generatePowerStatsId(createHeroRequest);

        Hero hero = new Hero(createHeroRequest, powerStatsId);

        return heroRepository.create(hero);
    }

    public Optional<Hero> findById(UUID id) {
        return heroRepository.findById(id);
    }

    private UUID generatePowerStatsId(CreateHeroRequest createHeroRequest) {
        String combinedStats = createHeroRequest.getStrength() + "-" +
                createHeroRequest.getAgility() + "-" +
                createHeroRequest.getDexterity() + "-" +
                createHeroRequest.getIntelligence();
        return UUID.nameUUIDFromBytes(combinedStats.getBytes());
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
}