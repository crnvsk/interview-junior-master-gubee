package br.com.gubee.interview.core.unit.stubs;

import br.com.gubee.interview.core.adapters.persistence.HeroRepository;
import br.com.gubee.interview.core.domain.Hero;

import java.util.*;

public class HeroRepositoryStub implements HeroRepository {
    private final Map<UUID, Hero> database = new HashMap<>();

    @Override
    public UUID create(Hero hero) {
        UUID id = UUID.randomUUID();
        hero.setId(id);
        database.put(id, hero);
        return id;
    }

    @Override
    public Optional<Hero> findById(UUID id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public List<Hero> findByName(String name) {
        List<Hero> result = new ArrayList<>();
        for (Hero hero : database.values()) {
            if (hero.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(hero);
            }
        }
        return result;
    }

    @Override
    public void update(UUID id, Hero updatedHero) {
        database.put(id, updatedHero);
    }

    @Override
    public void delete(UUID id) {
        database.remove(id);
    }
}