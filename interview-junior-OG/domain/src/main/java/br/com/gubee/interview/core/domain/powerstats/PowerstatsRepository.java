package br.com.gubee.interview.core.domain.powerstats;

import java.util.Optional;
import java.util.UUID;

public interface PowerstatsRepository {
    Powerstats save(Powerstats powerstats);

    Optional<Powerstats> findById(UUID id);

    Powerstats update(UUID id, Powerstats powerstats);
}
