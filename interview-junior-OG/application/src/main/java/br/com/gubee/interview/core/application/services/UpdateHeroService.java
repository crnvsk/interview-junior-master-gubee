package br.com.gubee.interview.core.application.services;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gubee.interview.core.application.usecases.UpdateHeroUseCase;
import br.com.gubee.interview.core.domain.hero.Hero;
import br.com.gubee.interview.core.domain.hero.HeroRepository;
import br.com.gubee.interview.core.domain.hero.HeroRequestDTO;
import br.com.gubee.interview.core.domain.powerstats.Powerstats;
import br.com.gubee.interview.core.domain.powerstats.PowerstatsRepository;

@Service
public class UpdateHeroService implements UpdateHeroUseCase {
    private final HeroRepository heroRepository;
    private final PowerstatsRepository powerstatsRepository;

    public UpdateHeroService(HeroRepository heroRepository, PowerstatsRepository powerstatsRepository) {
        this.heroRepository = heroRepository;
        this.powerstatsRepository = powerstatsRepository;
    }

    @Override
    @Transactional
    public void updateHero(UUID id, HeroRequestDTO updatedHeroDTO) {
        Optional<Hero> optionalHero = heroRepository.findById(id);
        if (optionalHero.isPresent()) {
            Hero existingHero = optionalHero.get();

            UUID powerStatsId = existingHero.getPowerStatsId();
            Powerstats powerstats = powerstatsRepository.findById(powerStatsId)
                    .orElseThrow(() -> new RuntimeException("Powerstats not found"));

            powerstats.setStrength(updatedHeroDTO.powerStats().strength());
            powerstats.setAgility(updatedHeroDTO.powerStats().agility());
            powerstats.setDexterity(updatedHeroDTO.powerStats().dexterity());
            powerstats.setIntelligence(updatedHeroDTO.powerStats().intelligence());
            powerstats.setUpdatedAt(Instant.now());

            powerstatsRepository.update(powerstats.getId(), powerstats);

            existingHero.setName(updatedHeroDTO.name());
            existingHero.setRace(updatedHeroDTO.race());
            existingHero.setEnabled(updatedHeroDTO.enabled());
            existingHero.setUpdatedAt(Instant.now());

            heroRepository.update(existingHero.getId(), existingHero);
        }
    }

}
