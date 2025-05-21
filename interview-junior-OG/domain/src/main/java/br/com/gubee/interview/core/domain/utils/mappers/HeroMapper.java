package br.com.gubee.interview.core.domain.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import br.com.gubee.interview.core.domain.hero.Hero;
import br.com.gubee.interview.core.domain.hero.HeroRequestDTO;
import br.com.gubee.interview.core.domain.hero.HeroResponseDTO;
import br.com.gubee.interview.core.domain.powerstats.Powerstats;
import br.com.gubee.interview.core.domain.powerstats.PowerstatsDTO;

@Mapper(componentModel = "default")
public interface HeroMapper {

	@Mappings({
			@Mapping(target = "id", ignore = true),
			@Mapping(source = "name", target = "name"),
			@Mapping(source = "race", target = "race"),
			@Mapping(target = "powerStatsId", ignore = true),
			@Mapping(target = "createdAt", ignore = true),
			@Mapping(target = "updatedAt", ignore = true),
			@Mapping(source = "enabled", target = "enabled")
	})
	Hero toEntity(HeroRequestDTO dto);

	default HeroResponseDTO toDto(Hero hero, Powerstats powerStats) {
		PowerstatsDTO powerstatsDTO = new PowerstatsDTO(
				powerStats.getStrength(),
				powerStats.getAgility(),
				powerStats.getDexterity(),
				powerStats.getIntelligence());
		return new HeroResponseDTO(
				hero.getId(),
				hero.getName(),
				hero.getRace(),
				powerstatsDTO,
				hero.getCreatedAt(),
				hero.getUpdatedAt(),
				hero.isEnabled());
	}
}