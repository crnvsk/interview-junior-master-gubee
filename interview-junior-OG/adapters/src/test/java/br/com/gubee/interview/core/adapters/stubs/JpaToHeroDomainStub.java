package br.com.gubee.interview.core.adapters.stubs;

import br.com.gubee.interview.core.adapters.outbound.entities.JpaHeroEntity;
import br.com.gubee.interview.core.adapters.utils.mappers.JpaToHeroDomain;
import br.com.gubee.interview.core.domain.hero.Hero;

public class JpaToHeroDomainStub implements JpaToHeroDomain {
        @Override
        public Hero toDomain(JpaHeroEntity jpa) {
            Hero h = new Hero();
            h.setId(jpa.getId());
            h.setName(jpa.getName());
            h.setRace(jpa.getRace());
            h.setPowerStatsId(jpa.getPowerStats() != null ? jpa.getPowerStats().getId() : null);
            h.setCreatedAt(jpa.getCreatedAt());
            h.setUpdatedAt(jpa.getUpdatedAt());
            h.setEnabled(jpa.isEnabled());
            return h;
        }
    }
