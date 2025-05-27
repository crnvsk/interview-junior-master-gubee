package br.com.gubee.interview.core.application.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gubee.interview.core.application.usecases.FindHeroByIdUseCase;
import br.com.gubee.interview.core.domain.hero.HeroQueryPort;
import br.com.gubee.interview.core.domain.hero.HeroResponseDTO;
import br.com.gubee.interview.core.domain.powerstats.PowerstatsQueryPort;
import br.com.gubee.interview.core.domain.utils.mappers.HeroMapper;

@Service
public class FindHeroByIdService implements FindHeroByIdUseCase {
    private final HeroQueryPort heroQueryPort;
    private final PowerstatsQueryPort powerstatsQueryPort;
    private final HeroMapper heroMapper;

    public FindHeroByIdService(HeroQueryPort heroQueryPort, PowerstatsQueryPort powerstatsQueryPort,
            HeroMapper heroMapper) {
        this.heroQueryPort = heroQueryPort;
        this.powerstatsQueryPort = powerstatsQueryPort;
        this.heroMapper = heroMapper;
    }

    @Override
    @Transactional
    public Optional<HeroResponseDTO> findHeroById(UUID id) {
        return heroQueryPort.findById(id)
                .flatMap(hero -> powerstatsQueryPort.findById(hero.getPowerStatsId())
                        .map(powerstats -> heroMapper.toDto(hero, powerstats)));
    }
}