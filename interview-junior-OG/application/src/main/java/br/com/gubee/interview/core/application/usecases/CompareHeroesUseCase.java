package br.com.gubee.interview.core.application.usecases;

import java.util.UUID;

import br.com.gubee.interview.core.domain.hero.HeroComparisonResponseDTO;

public interface CompareHeroesUseCase {

    HeroComparisonResponseDTO compare(UUID hero1Id, UUID hero2Id);
}
