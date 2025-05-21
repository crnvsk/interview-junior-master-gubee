package br.com.gubee.interview.core.application.usecases;

import java.util.Optional;
import java.util.UUID;

import br.com.gubee.interview.core.domain.hero.HeroResponseDTO;

public interface FindHeroByIdUseCase {

    public Optional<HeroResponseDTO> findHeroById(UUID id);
}
