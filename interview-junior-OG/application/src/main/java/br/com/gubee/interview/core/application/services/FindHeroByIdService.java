package br.com.gubee.interview.core.application.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gubee.interview.core.application.usecases.FindHeroByIdUseCase;
import br.com.gubee.interview.core.domain.hero.HeroRepository;
import br.com.gubee.interview.core.domain.hero.HeroResponseDTO;
import br.com.gubee.interview.core.domain.powerstats.PowerstatsRepository;
import br.com.gubee.interview.core.domain.utils.mappers.HeroMapper;

@Service
public class FindHeroByIdService implements FindHeroByIdUseCase {
    private final HeroRepository heroRepository;
    private final PowerstatsRepository powerstatsRepository;
    private final HeroMapper heroMapper;

    public FindHeroByIdService(HeroRepository heroRepository, PowerstatsRepository powerstatsRepository,
            HeroMapper heroMapper) {
        this.heroRepository = heroRepository;
        this.powerstatsRepository = powerstatsRepository;
        this.heroMapper = heroMapper;
    }

    @Override
    @Transactional
    public Optional<HeroResponseDTO> findHeroById(UUID id) {
        return heroRepository.findById(id)
                .flatMap(hero -> powerstatsRepository.findById(hero.getPowerStatsId())
                        .map(powerstats -> heroMapper.toDto(hero, powerstats)));
    }

}
