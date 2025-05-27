package br.com.gubee.interview.core.domain.powerstats;

import java.util.Optional;
import java.util.UUID;

public interface PowerstatsQueryPort {
    Optional<Powerstats> findById(UUID id);
}