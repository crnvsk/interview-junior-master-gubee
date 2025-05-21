package br.com.gubee.interview.core.domain.hero;

import java.util.Optional;
import java.util.UUID;

public interface HeroRepository {
    Hero save(Hero hero);

    Optional<Hero> findById(UUID id);

    Optional<Hero> findByName(String name);

    void update(UUID id, Hero updatedHero);

    void delete(UUID id);
}
