package br.com.gubee.interview.core.application.usecases;

import br.com.gubee.interview.core.domain.hero.HeroRequestDTO;
import br.com.gubee.interview.core.domain.hero.HeroResponseDTO;

public interface CreateHeroUseCase {
    
    public HeroResponseDTO createHero(HeroRequestDTO heroRequestDTO);
}
