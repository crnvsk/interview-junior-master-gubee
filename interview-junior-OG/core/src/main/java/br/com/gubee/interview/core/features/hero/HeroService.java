package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.request.CreateHeroRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HeroService {

    private final HeroRepository heroRepository;

    @Transactional
    public UUID create(CreateHeroRequest createHeroRequest) {
        // Generate powerStatsId based on the request
        UUID powerStatsId = generatePowerStatsId(createHeroRequest);

        // Use the existing constructor
        Hero hero = new Hero(createHeroRequest, powerStatsId);

        return heroRepository.create(hero);
    }

    public Optional<Hero> findById(UUID id) {
        return heroRepository.findById(id);
    }

    private UUID generatePowerStatsId(CreateHeroRequest createHeroRequest) {
        // Example logic: Combine power stats into a unique identifier
        String combinedStats = createHeroRequest.getStrength() + "-" +
                createHeroRequest.getAgility() + "-" +
                createHeroRequest.getDexterity() + "-" +
                createHeroRequest.getIntelligence();
        return UUID.nameUUIDFromBytes(combinedStats.getBytes());
    }
}