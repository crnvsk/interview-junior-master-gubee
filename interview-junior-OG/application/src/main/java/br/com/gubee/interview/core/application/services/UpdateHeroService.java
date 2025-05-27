package br.com.gubee.interview.core.application.services;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gubee.interview.core.application.usecases.UpdateHeroUseCase;
import br.com.gubee.interview.core.domain.hero.Hero;
import br.com.gubee.interview.core.domain.hero.HeroCommandPort;
import br.com.gubee.interview.core.domain.hero.HeroQueryPort;
import br.com.gubee.interview.core.domain.hero.HeroRequestDTO;
import br.com.gubee.interview.core.domain.powerstats.Powerstats;
import br.com.gubee.interview.core.domain.powerstats.PowerstatsCommandPort;
import br.com.gubee.interview.core.domain.powerstats.PowerstatsQueryPort;

@Service
public class UpdateHeroService implements UpdateHeroUseCase {
    private final HeroCommandPort heroCommandPort;
    private final HeroQueryPort heroQueryPort;
    private final PowerstatsCommandPort powerstatsCommandPort;
    private final PowerstatsQueryPort powerstatsQueryPort;

    public UpdateHeroService(
            HeroCommandPort heroCommandPort,
            HeroQueryPort heroQueryPort,
            PowerstatsCommandPort powerstatsCommandPort,
            PowerstatsQueryPort powerstatsQueryPort) {
        this.heroCommandPort = heroCommandPort;
        this.heroQueryPort = heroQueryPort;
        this.powerstatsCommandPort = powerstatsCommandPort;
        this.powerstatsQueryPort = powerstatsQueryPort;
    }

    @Override
    @Transactional
    public void updateHero(UUID id, HeroRequestDTO updatedHeroDTO) {
        Optional<Hero> optionalHero = heroQueryPort.findById(id);
        if (optionalHero.isPresent()) {
            Hero existingHero = optionalHero.get();

            UUID powerStatsId = existingHero.getPowerStatsId();
            Powerstats powerstats = powerstatsQueryPort.findById(powerStatsId)
                    .orElseThrow(() -> new RuntimeException("Powerstats not found"));

            powerstats.setStrength(updatedHeroDTO.powerStats().strength());
            powerstats.setAgility(updatedHeroDTO.powerStats().agility());
            powerstats.setDexterity(updatedHeroDTO.powerStats().dexterity());
            powerstats.setIntelligence(updatedHeroDTO.powerStats().intelligence());
            powerstats.setUpdatedAt(Instant.now());

            powerstatsCommandPort.update(powerstats.getId(), powerstats);

            existingHero.setName(updatedHeroDTO.name());
            existingHero.setRace(updatedHeroDTO.race());
            existingHero.setEnabled(updatedHeroDTO.enabled());
            existingHero.setUpdatedAt(Instant.now());

            heroCommandPort.update(existingHero.getId(), existingHero);
        }
    }
}