package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.model.Hero;

import java.util.Map;
import java.util.HashMap;
import java.util.UUID;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

class InMemoryHeroRepository extends HeroRepository {

    private final Map<UUID, Hero> database = new HashMap<>();

    public InMemoryHeroRepository() {
        super(null);
    }

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
