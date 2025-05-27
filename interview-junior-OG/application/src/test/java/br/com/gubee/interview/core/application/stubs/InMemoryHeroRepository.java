package br.com.gubee.interview.core.application.stubs;

import java.util.*;

import br.com.gubee.interview.core.domain.hero.Hero;
import br.com.gubee.interview.core.domain.hero.HeroCommandPort;
import br.com.gubee.interview.core.domain.hero.HeroQueryPort;

public class InMemoryHeroRepository implements HeroCommandPort, HeroQueryPort {
    private final Map<UUID, Hero> storage = new HashMap<>();

    @Override
    public Hero save(Hero hero) {
        UUID id = hero.getId() != null ? hero.getId() : UUID.randomUUID();
        hero.setId(id);
        storage.put(id, hero);
        return hero;
    }

    @Override
    public Optional<Hero> findById(UUID id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Optional<Hero> findByName(String name) {
        return storage.values().stream()
                .filter(hero -> hero.getName().equals(name))
                .findFirst();
    }

    @Override
    public void update(UUID id, Hero updatedHero) {
        storage.put(id, updatedHero);
    }

    @Override
    public void delete(UUID id) {
        storage.remove(id);
    }
}