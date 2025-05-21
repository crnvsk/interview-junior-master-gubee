package br.com.gubee.interview.core.application.services;

import br.com.gubee.interview.core.domain.utils.mappers.HeroMapper;
import br.com.gubee.interview.core.application.usecases.CreateHeroUseCase;
import br.com.gubee.interview.core.domain.hero.Hero;
import br.com.gubee.interview.core.domain.hero.HeroRepository;
import br.com.gubee.interview.core.domain.hero.HeroRequestDTO;
import br.com.gubee.interview.core.domain.hero.HeroResponseDTO;
import br.com.gubee.interview.core.domain.powerstats.Powerstats;
import br.com.gubee.interview.core.domain.powerstats.PowerstatsRepository;

import java.time.Instant;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateHeroService implements CreateHeroUseCase {
    private final HeroRepository heroRepository;
    private final PowerstatsRepository powerStatsRepository;
    private final HeroMapper heroMapper;

    public CreateHeroService(HeroRepository heroRepository, PowerstatsRepository powerstatsRepository,
            HeroMapper heroMapper) {
        this.heroRepository = heroRepository;
        this.powerStatsRepository = powerstatsRepository;
        this.heroMapper = heroMapper;
    }

    @Override
    @Transactional
    public HeroResponseDTO createHero(HeroRequestDTO heroRequestDTO) {

        Powerstats powerStats = new Powerstats(
                null,
                heroRequestDTO.powerStats().strength(),
                heroRequestDTO.powerStats().agility(),
                heroRequestDTO.powerStats().dexterity(),
                heroRequestDTO.powerStats().intelligence(),
                Instant.now(),
                Instant.now());

        Powerstats powerStatsResult = powerStatsRepository.save(powerStats);

        Hero hero = heroMapper.toEntity(heroRequestDTO);
        hero.setPowerStatsId(powerStatsResult.getId());
        hero.setCreatedAt(Instant.now());
        hero.setUpdatedAt(Instant.now());
        Hero savedHero = heroRepository.save(hero);

        Powerstats savedPowerStats = powerStatsRepository.findById(powerStatsResult.getId()).orElse(powerStats);
        return heroMapper.toDto(savedHero, savedPowerStats);
    }
}