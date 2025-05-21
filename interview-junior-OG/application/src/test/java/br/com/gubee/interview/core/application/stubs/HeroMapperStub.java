package br.com.gubee.interview.core.application.stubs;

import br.com.gubee.interview.core.domain.hero.Hero;
import br.com.gubee.interview.core.domain.hero.HeroRequestDTO;
import br.com.gubee.interview.core.domain.hero.HeroResponseDTO;
import br.com.gubee.interview.core.domain.powerstats.Powerstats;
import br.com.gubee.interview.core.domain.powerstats.PowerstatsDTO;
import br.com.gubee.interview.core.domain.utils.mappers.HeroMapper;

public class HeroMapperStub implements HeroMapper {

    @Override
    public Hero toEntity(HeroRequestDTO dto) {
        Hero hero = new Hero();
        hero.setName(dto.name());
        hero.setRace(dto.race());
        hero.setEnabled(dto.enabled() != null ? dto.enabled() : true);
        return hero;
    }

    @Override
    public HeroResponseDTO toDto(Hero hero, Powerstats powerStats) {
        PowerstatsDTO dto = new PowerstatsDTO(
                powerStats.getStrength(),
                powerStats.getAgility(),
                powerStats.getDexterity(),
                powerStats.getIntelligence()
        );
        return new HeroResponseDTO(
                hero.getId(),
                hero.getName(),
                hero.getRace(),
                dto,
                hero.getCreatedAt(),
                hero.getUpdatedAt(),
                hero.isEnabled()
        );
    }
}