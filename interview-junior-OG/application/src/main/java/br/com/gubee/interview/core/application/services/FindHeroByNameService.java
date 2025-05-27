package br.com.gubee.interview.core.application.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gubee.interview.core.application.usecases.FindHeroByNameUseCase;
import br.com.gubee.interview.core.domain.hero.HeroQueryPort;
import br.com.gubee.interview.core.domain.hero.HeroResponseDTO;
import br.com.gubee.interview.core.domain.powerstats.PowerstatsQueryPort;
import br.com.gubee.interview.core.domain.utils.mappers.HeroMapper;

@Service
public class FindHeroByNameService implements FindHeroByNameUseCase {
    private final HeroQueryPort heroQueryPort;
    private final PowerstatsQueryPort powerstatsQueryPort;
    private final HeroMapper heroMapper;

    public FindHeroByNameService(HeroQueryPort heroQueryPort, PowerstatsQueryPort powerstatsQueryPort,
            HeroMapper heroMapper) {
        this.heroQueryPort = heroQueryPort;
        this.powerstatsQueryPort = powerstatsQueryPort;
        this.heroMapper = heroMapper;
    }

    @Override
    @Transactional
    public Optional<HeroResponseDTO> findHeroByName(String name) {
        return heroQueryPort.findByName(name)
                .flatMap(hero -> powerstatsQueryPort.findById(hero.getPowerStatsId())
                        .map(powerstats -> heroMapper.toDto(hero, powerstats)));
    }
}