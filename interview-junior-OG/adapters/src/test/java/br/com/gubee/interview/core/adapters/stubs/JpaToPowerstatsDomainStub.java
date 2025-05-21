package br.com.gubee.interview.core.adapters.stubs;

import br.com.gubee.interview.core.adapters.outbound.entities.JpaPowerstatsEntity;
import br.com.gubee.interview.core.adapters.utils.mappers.JpaToPowerstatsDomain;
import br.com.gubee.interview.core.domain.powerstats.Powerstats;

public class JpaToPowerstatsDomainStub implements JpaToPowerstatsDomain {
    @Override
    public Powerstats toDomain(JpaPowerstatsEntity jpa) {
        Powerstats p = new Powerstats();
        p.setId(jpa.getId());
        p.setStrength(jpa.getStrength());
        p.setAgility(jpa.getAgility());
        p.setDexterity(jpa.getDexterity());
        p.setIntelligence(jpa.getIntelligence());
        p.setCreatedAt(jpa.getCreatedAt());
        p.setUpdatedAt(jpa.getUpdatedAt());
        return p;
    }
}
