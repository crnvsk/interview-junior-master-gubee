package br.com.gubee.interview.core.application.usecases;

import java.util.UUID;

import br.com.gubee.interview.core.domain.hero.HeroRequestDTO;

public interface UpdateHeroUseCase {

    public void updateHero(UUID id, HeroRequestDTO heroRequestDTO);
}
