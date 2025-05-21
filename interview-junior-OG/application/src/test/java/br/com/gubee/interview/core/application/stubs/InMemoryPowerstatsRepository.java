package br.com.gubee.interview.core.application.stubs;

import java.util.*;

import br.com.gubee.interview.core.domain.powerstats.Powerstats;
import br.com.gubee.interview.core.domain.powerstats.PowerstatsRepository;

public class InMemoryPowerstatsRepository implements PowerstatsRepository {
    private final Map<UUID, Powerstats> storage = new HashMap<>();

    @Override
    public Powerstats save(Powerstats powerstats) {
        storage.put(powerstats.getId(), powerstats);
        return powerstats;
    }

    @Override
    public Optional<Powerstats> findById(UUID id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Powerstats update(UUID id, Powerstats powerstats) {
        storage.put(id, powerstats);
        return powerstats;
    }
}
