package br.com.gubee.interview.core.adapters.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import br.com.gubee.interview.core.adapters.outbound.entities.JpaPowerstatsEntity;
import br.com.gubee.interview.core.domain.powerstats.Powerstats;

@Mapper(componentModel = "spring")
public interface JpaToPowerstatsDomain {

    @Mappings({
        @Mapping(source = "jpa.id", target = "id"),
        @Mapping(source = "jpa.strength", target = "strength"),
        @Mapping(source = "jpa.agility", target = "agility"),
        @Mapping(source = "jpa.dexterity", target = "dexterity"),
        @Mapping(source = "jpa.intelligence", target = "intelligence"),
        @Mapping(source = "jpa.createdAt", target = "createdAt"),
        @Mapping(source = "jpa.updatedAt", target = "updatedAt")
    })
    Powerstats toDomain(JpaPowerstatsEntity jpa);
}