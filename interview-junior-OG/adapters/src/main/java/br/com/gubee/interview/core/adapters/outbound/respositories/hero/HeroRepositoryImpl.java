package br.com.gubee.interview.core.adapters.outbound.respositories.hero;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.gubee.interview.core.adapters.outbound.entities.JpaHeroEntity;
import br.com.gubee.interview.core.adapters.outbound.entities.JpaPowerstatsEntity;
import br.com.gubee.interview.core.adapters.outbound.respositories.powerstats.JpaPowerstatsRepository;
import br.com.gubee.interview.core.adapters.utils.mappers.JpaToHeroDomain;
import br.com.gubee.interview.core.domain.hero.Hero;
import br.com.gubee.interview.core.domain.hero.HeroRepository;

@Repository
public class HeroRepositoryImpl implements HeroRepository {
    private final JpaHeroRepository jpaHeroRepository;
    private final JpaPowerstatsRepository jpaPowerstatsRepository;

    @Autowired
    private JpaToHeroDomain jpaToHeroDomain;

    public HeroRepositoryImpl(JpaHeroRepository jpaHeroRepository, JpaPowerstatsRepository jpaPowerstatsRepository) {
        this.jpaHeroRepository = jpaHeroRepository;
        this.jpaPowerstatsRepository = jpaPowerstatsRepository;
    }

    @Override
    public Hero save(Hero hero) {
        JpaPowerstatsEntity powerstatsEntity = null;
        if (hero.getPowerStatsId() != null) {
            powerstatsEntity = jpaPowerstatsRepository.findById(hero.getPowerStatsId())
                    .orElseThrow(() -> new RuntimeException("Powerstats not found"));
        }
        JpaHeroEntity heroEntity = new JpaHeroEntity(hero);
        heroEntity.setPowerStats(powerstatsEntity);
        JpaHeroEntity savedEntity = this.jpaHeroRepository.save(heroEntity);
        return jpaToHeroDomain.toDomain(savedEntity);
    }

    @Override
    public Optional<Hero> findById(UUID id) {
        Optional<JpaHeroEntity> heroEntity = this.jpaHeroRepository.findById(id);
        return heroEntity.map(jpaToHeroDomain::toDomain);
    }

    @Override
    public Optional<Hero> findByName(String name) {
        Optional<JpaHeroEntity> heroEntity = this.jpaHeroRepository.findByName(name);
        return heroEntity.map(jpaToHeroDomain::toDomain);
    }

    @Override
    public void update(UUID id, Hero updatedHero) {
        Optional<JpaHeroEntity> heroEntity = this.jpaHeroRepository.findById(id);
        if (heroEntity.isPresent()) {
            JpaHeroEntity entity = heroEntity.get();
            entity.setName(updatedHero.getName());
            entity.setRace(updatedHero.getRace());
            entity.setEnabled(updatedHero.isEnabled());
            JpaPowerstatsEntity powerstatsEntity = null;
            if (updatedHero.getPowerStatsId() != null) {
                powerstatsEntity = jpaPowerstatsRepository.findById(updatedHero.getPowerStatsId())
                        .orElseThrow(() -> new RuntimeException("Powerstats not found"));
            }
            entity.setPowerStats(powerstatsEntity);
            entity.setUpdatedAt(updatedHero.getUpdatedAt());
            this.jpaHeroRepository.save(entity);
        } else {
            throw new RuntimeException("Hero not found");
        }
    }

    @Override
    public void delete(UUID id) {
        Optional<JpaHeroEntity> heroEntity = this.jpaHeroRepository.findById(id);
        if (heroEntity.isPresent()) {
            this.jpaHeroRepository.delete(heroEntity.get());
        } else {
            throw new RuntimeException("Hero not found");
        }
    }

    public void setJpaHeroDomain(JpaToHeroDomain mapper) {
        this.jpaToHeroDomain = mapper;
    }
}
