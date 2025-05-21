package br.com.gubee.interview.core.application.usecases;

import java.util.Optional;

import br.com.gubee.interview.core.domain.hero.HeroResponseDTO;

public interface FindHeroByNameUseCase {

    public Optional<HeroResponseDTO> findHeroByName(String name);
}
