package br.com.gubee.interview.core.application.services;

import java.time.Instant;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gubee.interview.core.application.usecases.CreateHeroUseCase;
import br.com.gubee.interview.core.domain.hero.Hero;
import br.com.gubee.interview.core.domain.hero.HeroCommandPort;
import br.com.gubee.interview.core.domain.hero.HeroRequestDTO;
import br.com.gubee.interview.core.domain.hero.HeroResponseDTO;
import br.com.gubee.interview.core.domain.powerstats.Powerstats;
import br.com.gubee.interview.core.domain.powerstats.PowerstatsCommandPort;
import br.com.gubee.interview.core.domain.powerstats.PowerstatsQueryPort;
import br.com.gubee.interview.core.domain.utils.mappers.HeroMapper;

@Service
public class CreateHeroService implements CreateHeroUseCase {
    private final HeroCommandPort heroCommandPort;
    private final PowerstatsCommandPort powerstatsCommandPort;
    private final PowerstatsQueryPort powerstatsQueryPort;
    private final HeroMapper heroMapper;

    public CreateHeroService(
            HeroCommandPort heroCommandPort,
            PowerstatsCommandPort powerstatsCommandPort,
            PowerstatsQueryPort powerstatsQueryPort,
            HeroMapper heroMapper) {
        this.heroCommandPort = heroCommandPort;
        this.powerstatsCommandPort = powerstatsCommandPort;
        this.powerstatsQueryPort = powerstatsQueryPort;
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

        Powerstats powerStatsResult = powerstatsCommandPort.save(powerStats);

        Hero hero = heroMapper.toEntity(heroRequestDTO);
        hero.setPowerStatsId(powerStatsResult.getId());
        hero.setCreatedAt(Instant.now());
        hero.setUpdatedAt(Instant.now());
        Hero savedHero = heroCommandPort.save(hero);

        Powerstats savedPowerStats = powerstatsQueryPort.findById(powerStatsResult.getId()).orElse(powerStats);
        return heroMapper.toDto(savedHero, savedPowerStats);
    }
}