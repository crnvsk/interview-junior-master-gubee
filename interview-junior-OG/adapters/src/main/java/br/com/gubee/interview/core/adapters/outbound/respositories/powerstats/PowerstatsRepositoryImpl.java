package br.com.gubee.interview.core.adapters.outbound.respositories.powerstats;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.gubee.interview.core.adapters.outbound.entities.JpaPowerstatsEntity;
import br.com.gubee.interview.core.adapters.utils.mappers.JpaToPowerstatsDomain;
import br.com.gubee.interview.core.domain.powerstats.Powerstats;
import br.com.gubee.interview.core.domain.powerstats.PowerstatsRepository;

@Repository
public class PowerstatsRepositoryImpl implements PowerstatsRepository {
    private final JpaPowerstatsRepository jpaPowerstatsRepository;

    @Autowired
    private JpaToPowerstatsDomain jpaToPowerstatsDomain;

    public PowerstatsRepositoryImpl(JpaPowerstatsRepository jpaPowerstatsRepository) {
        this.jpaPowerstatsRepository = jpaPowerstatsRepository;
    }

    @Override
    public Powerstats save(Powerstats powerstats) {
        JpaPowerstatsEntity entity = new JpaPowerstatsEntity(powerstats);
        JpaPowerstatsEntity saved = jpaPowerstatsRepository.save(entity);
        return jpaToPowerstatsDomain.toDomain(saved);
    }

    @Override
    public Optional<Powerstats> findById(UUID id) {
        return jpaPowerstatsRepository.findById(id)
                .map(jpaToPowerstatsDomain::toDomain);
    }

    @Override
    public Powerstats update(UUID id, Powerstats powerstats) {
        JpaPowerstatsEntity entity = jpaPowerstatsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Powerstats not found"));
        entity.setStrength(powerstats.getStrength());
        entity.setAgility(powerstats.getAgility());
        entity.setDexterity(powerstats.getDexterity());
        entity.setIntelligence(powerstats.getIntelligence());
        entity.setUpdatedAt(powerstats.getUpdatedAt());
        JpaPowerstatsEntity saved = jpaPowerstatsRepository.save(entity);
        return jpaToPowerstatsDomain.toDomain(saved);
    }

    public void setJpaToPowerstatsDomain(JpaToPowerstatsDomain mapper) {
        this.jpaToPowerstatsDomain = mapper;
    }
}