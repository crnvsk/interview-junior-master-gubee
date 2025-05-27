package br.com.gubee.interview.core.application.stubs;

import java.util.*;

import br.com.gubee.interview.core.domain.powerstats.Powerstats;
import br.com.gubee.interview.core.domain.powerstats.PowerstatsCommandPort;
import br.com.gubee.interview.core.domain.powerstats.PowerstatsQueryPort;

public class InMemoryPowerstatsRepository implements PowerstatsCommandPort, PowerstatsQueryPort {
    private final Map<UUID, Powerstats> storage = new HashMap<>();

    @Override
    public Powerstats save(Powerstats powerstats) {
        UUID id = powerstats.getId() != null ? powerstats.getId() : UUID.randomUUID();
        powerstats.setId(id);
        storage.put(id, powerstats);
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