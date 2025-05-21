package br.com.gubee.interview.core.adapters.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import br.com.gubee.interview.core.adapters.outbound.entities.JpaHeroEntity;
import br.com.gubee.interview.core.domain.hero.Hero;

@Mapper(componentModel = "spring")
public interface JpaToHeroDomain {

    @Mappings({
        @Mapping(source = "jpa.id", target = "id"),
        @Mapping(source = "jpa.name", target = "name"),
        @Mapping(source = "jpa.race", target = "race"),
        @Mapping(source = "jpa.powerStats.id", target = "powerStatsId"),
        @Mapping(source = "jpa.createdAt", target = "createdAt"),
        @Mapping(source = "jpa.updatedAt", target = "updatedAt"),
        @Mapping(source = "jpa.enabled", target = "enabled")
    })
    Hero toDomain(JpaHeroEntity jpa);
    
}
