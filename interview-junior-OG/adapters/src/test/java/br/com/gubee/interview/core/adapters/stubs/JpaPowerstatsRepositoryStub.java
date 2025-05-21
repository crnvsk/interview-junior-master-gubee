package br.com.gubee.interview.core.adapters.stubs;

import br.com.gubee.interview.core.adapters.outbound.entities.JpaPowerstatsEntity;
import br.com.gubee.interview.core.adapters.outbound.respositories.powerstats.JpaPowerstatsRepository;

import java.util.*;

public class JpaPowerstatsRepositoryStub implements JpaPowerstatsRepository {
    private final Map<UUID, JpaPowerstatsEntity> storage = new HashMap<>();

    @Override
    public <S extends JpaPowerstatsEntity> S save(S entity) {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        storage.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<JpaPowerstatsEntity> findById(UUID id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public void delete(JpaPowerstatsEntity entity) {
        storage.remove(entity.getId());
    }

    @Override public List<JpaPowerstatsEntity> findAll() { throw new UnsupportedOperationException(); }
    @Override public void deleteById(UUID uuid) { throw new UnsupportedOperationException(); }
    @Override public void deleteAll() { throw new UnsupportedOperationException(); }
    @Override public boolean existsById(UUID uuid) { throw new UnsupportedOperationException(); }
    @Override public long count() { throw new UnsupportedOperationException(); }
    @Override public List<JpaPowerstatsEntity> findAllById(Iterable<UUID> uuids) { throw new UnsupportedOperationException(); }
    @Override public <S extends JpaPowerstatsEntity> List<S> saveAll(Iterable<S> entities) { throw new UnsupportedOperationException(); }
    @Override public void flush() { throw new UnsupportedOperationException(); }
    @Override public <S extends JpaPowerstatsEntity> S saveAndFlush(S entity) { throw new UnsupportedOperationException(); }
    @Override public void deleteInBatch(Iterable<JpaPowerstatsEntity> entities) { throw new UnsupportedOperationException(); }
    @Override public void deleteAllInBatch() { throw new UnsupportedOperationException(); }
    @Override public JpaPowerstatsEntity getOne(UUID uuid) { throw new UnsupportedOperationException(); }
    @Override public JpaPowerstatsEntity getById(UUID uuid) { throw new UnsupportedOperationException(); }
    @Override public <S extends JpaPowerstatsEntity> boolean exists(org.springframework.data.domain.Example<S> example) { throw new UnsupportedOperationException(); }
    @Override public void deleteAll(Iterable<? extends JpaPowerstatsEntity> entities) { throw new UnsupportedOperationException(); }
    @Override public <S extends JpaPowerstatsEntity, R> R findBy(org.springframework.data.domain.Example<S> example, java.util.function.Function<org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery<S>, R> queryFunction) { throw new UnsupportedOperationException(); }
    @Override public void deleteAllByIdInBatch(Iterable<UUID> uuids) { throw new UnsupportedOperationException(); }
    @Override public void deleteAllInBatch(Iterable<JpaPowerstatsEntity> entities) { throw new UnsupportedOperationException(); }
    @Override public <S extends JpaPowerstatsEntity> List<S> findAll(org.springframework.data.domain.Example<S> example) { throw new UnsupportedOperationException(); }
    @Override public <S extends JpaPowerstatsEntity> List<S> findAll(org.springframework.data.domain.Example<S> example, org.springframework.data.domain.Sort sort) { throw new UnsupportedOperationException(); }
    @Override public <S extends JpaPowerstatsEntity> org.springframework.data.domain.Page<S> findAll(org.springframework.data.domain.Example<S> example, org.springframework.data.domain.Pageable pageable) { throw new UnsupportedOperationException(); }
    @Override public List<JpaPowerstatsEntity> findAll(org.springframework.data.domain.Sort sort) { throw new UnsupportedOperationException(); }
    @Override public org.springframework.data.domain.Page<JpaPowerstatsEntity> findAll(org.springframework.data.domain.Pageable pageable) { throw new UnsupportedOperationException(); }
    @Override public <S extends JpaPowerstatsEntity> long count(org.springframework.data.domain.Example<S> example) { throw new UnsupportedOperationException(); }
    @Override public <S extends JpaPowerstatsEntity> Optional<S> findOne(org.springframework.data.domain.Example<S> example) { throw new UnsupportedOperationException(); }
    @Override public void deleteAllById(Iterable<? extends UUID> uuids) { throw new UnsupportedOperationException(); }
    @Override public <S extends JpaPowerstatsEntity> List<S> saveAllAndFlush(Iterable<S> entities) { throw new UnsupportedOperationException(); }
    @Override public JpaPowerstatsEntity getReferenceById(UUID uuid) { throw new UnsupportedOperationException(); }
}