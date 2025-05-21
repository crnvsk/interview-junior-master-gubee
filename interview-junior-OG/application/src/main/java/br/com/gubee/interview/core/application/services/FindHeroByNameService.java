package br.com.gubee.interview.core.application.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gubee.interview.core.application.usecases.FindHeroByNameUseCase;
import br.com.gubee.interview.core.domain.hero.HeroRepository;
import br.com.gubee.interview.core.domain.hero.HeroResponseDTO;
import br.com.gubee.interview.core.domain.powerstats.PowerstatsRepository;
import br.com.gubee.interview.core.domain.utils.mappers.HeroMapper;

@Service
public class FindHeroByNameService implements FindHeroByNameUseCase {
    private final HeroRepository heroRepository;
    private final PowerstatsRepository powerstatsRepository;
    private final HeroMapper heroMapper;

    public FindHeroByNameService(HeroRepository heroRepository, PowerstatsRepository powerstatsRepository,
            HeroMapper heroMapper) {
        this.heroRepository = heroRepository;
        this.powerstatsRepository = powerstatsRepository;
        this.heroMapper = heroMapper;
    }

    @Override
    @Transactional
    public Optional<HeroResponseDTO> findHeroByName(String name) {
        return heroRepository.findByName(name)
                .flatMap(hero -> powerstatsRepository.findById(hero.getPowerStatsId())
                        .map(powerstats -> heroMapper.toDto(hero, powerstats)));
    }

}
