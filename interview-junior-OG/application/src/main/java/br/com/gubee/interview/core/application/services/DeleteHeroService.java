package br.com.gubee.interview.core.application.services;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gubee.interview.core.application.usecases.DeleteHeroUseCase;
import br.com.gubee.interview.core.domain.hero.HeroRepository;

@Service
public class DeleteHeroService implements DeleteHeroUseCase {
    private final HeroRepository heroRepository;

    public DeleteHeroService(HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
    }

    @Override
    @Transactional
    public void deleteHero(UUID id) {
        heroRepository.delete(id);
    }

}
