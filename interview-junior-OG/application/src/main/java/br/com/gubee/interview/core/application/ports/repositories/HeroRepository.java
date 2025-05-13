package br.com.gubee.interview.core.application.ports.repositories;

import br.com.gubee.interview.core.domain.Hero;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HeroRepository {
    UUID create(Hero hero);

    Optional<Hero> findById(UUID id);

    List<Hero> findByName(String name);

    void update(UUID id, Hero updatedHero);

    void delete(UUID id);
}