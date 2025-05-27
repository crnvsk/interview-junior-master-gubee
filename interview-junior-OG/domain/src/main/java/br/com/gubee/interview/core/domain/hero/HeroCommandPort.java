package br.com.gubee.interview.core.domain.hero;

import java.util.UUID;

public interface HeroCommandPort {
    Hero save(Hero hero);

    void update(UUID id, Hero updatedHero);

    void delete(UUID id);
}