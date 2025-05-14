package br.com.gubee.interview.core.application.hero.commands;

import br.com.gubee.interview.core.domain.Hero;
import br.com.gubee.interview.core.application.ports.repositories.HeroRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateHeroCommand {
    private final HeroRepository heroRepository;

    public boolean execute(UUID id, Hero updatedHero) {
        if (id == null) {
            throw new IllegalArgumentException("Hero ID cannot be null");
        }
        if (updatedHero == null) {
            throw new IllegalArgumentException("Updated hero cannot be null");
        }

        Optional<Hero> existingHero = heroRepository.findById(id);
        if (existingHero.isEmpty()) {
            return false;
        }
        heroRepository.update(id, updatedHero);
        return true;
    }
}