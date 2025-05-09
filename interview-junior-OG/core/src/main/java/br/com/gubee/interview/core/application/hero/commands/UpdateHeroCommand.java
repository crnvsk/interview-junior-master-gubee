package br.com.gubee.interview.core.application.hero.commands;

import br.com.gubee.interview.core.domain.Hero;
import br.com.gubee.interview.core.adapters.persistence.HeroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateHeroCommand {
    private final HeroRepository heroRepository;

    public boolean execute(UUID id, Hero updatedHero) {
        Optional<Hero> existingHero = heroRepository.findById(id);
        if (existingHero.isEmpty()) {
            return false;
        }
        heroRepository.update(id, updatedHero);
        return true;
    }
}