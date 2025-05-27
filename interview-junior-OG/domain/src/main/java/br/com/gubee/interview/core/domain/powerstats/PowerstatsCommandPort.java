package br.com.gubee.interview.core.domain.powerstats;

import java.util.UUID;

public interface PowerstatsCommandPort {
    Powerstats save(Powerstats powerstats);

    Powerstats update(UUID id, Powerstats powerstats);
}