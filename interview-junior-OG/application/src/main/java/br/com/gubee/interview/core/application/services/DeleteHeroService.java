package br.com.gubee.interview.core.application.services;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gubee.interview.core.application.usecases.DeleteHeroUseCase;
import br.com.gubee.interview.core.domain.hero.HeroCommandPort;

@Service
public class DeleteHeroService implements DeleteHeroUseCase {
    private final HeroCommandPort heroCommandPort;

    public DeleteHeroService(HeroCommandPort heroCommandPort) {
        this.heroCommandPort = heroCommandPort;
    }

    @Override
    @Transactional
    public void deleteHero(UUID id) {
        heroCommandPort.delete(id);
    }
}