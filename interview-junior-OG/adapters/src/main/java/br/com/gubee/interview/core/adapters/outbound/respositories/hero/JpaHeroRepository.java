package br.com.gubee.interview.core.adapters.outbound.respositories.hero;

import br.com.gubee.interview.core.adapters.outbound.entities.JpaHeroEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaHeroRepository extends JpaRepository<JpaHeroEntity, UUID> {
    
    Optional<JpaHeroEntity> findByName(String name);
}