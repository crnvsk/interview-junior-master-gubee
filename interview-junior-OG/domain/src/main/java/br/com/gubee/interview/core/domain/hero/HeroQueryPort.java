package br.com.gubee.interview.core.domain.hero;

import java.util.Optional;
import java.util.UUID;

public interface HeroQueryPort {
    Optional<Hero> findById(UUID id);

    Optional<Hero> findByName(String name);
}