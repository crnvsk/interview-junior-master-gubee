package br.com.gubee.interview.core.adapters.outbound.respositories.powerstats;

import br.com.gubee.interview.core.adapters.outbound.entities.JpaPowerstatsEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaPowerstatsRepository extends JpaRepository<JpaPowerstatsEntity, UUID> {
}