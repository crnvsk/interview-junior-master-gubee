package br.com.gubee.interview.core.adapters.stubs;

import br.com.gubee.interview.core.adapters.outbound.entities.JpaHeroEntity;
import br.com.gubee.interview.core.adapters.outbound.respositories.hero.JpaHeroRepository;

import java.util.*;

public class JpaHeroRepositoryStub implements JpaHeroRepository {
    private final Map<UUID, JpaHeroEntity> storage = new HashMap<>();

    @Override
    public <S extends JpaHeroEntity> S save(S entity) {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        storage.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<JpaHeroEntity> findById(UUID id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Optional<JpaHeroEntity> findByName(String name) {
        return storage.values().stream()
                .filter(hero -> hero.getName().equals(name))
                .findFirst();
    }

    @Override
    public void delete(JpaHeroEntity entity) {
        storage.remove(entity.getId());
    }

    @Override public List<JpaHeroEntity> findAll() { throw new UnsupportedOperationException(); }
    @Override public void deleteById(UUID uuid) { throw new UnsupportedOperationException(); }
    @Override public void deleteAll() { throw new UnsupportedOperationException(); }
    @Override public boolean existsById(UUID uuid) { throw new UnsupportedOperationException(); }
    @Override public long count() { throw new UnsupportedOperationException(); }
    @Override public List<JpaHeroEntity> findAllById(Iterable<UUID> uuids) { throw new UnsupportedOperationException(); }
    @Override public <S extends JpaHeroEntity> List<S> saveAll(Iterable<S> entities) { throw new UnsupportedOperationException(); }
    @Override public void flush() { throw new UnsupportedOperationException(); }
    @Override public <S extends JpaHeroEntity> S saveAndFlush(S entity) { throw new UnsupportedOperationException(); }
    @Override public void deleteInBatch(Iterable<JpaHeroEntity> entities) { throw new UnsupportedOperationException(); }
    @Override public void deleteAllInBatch() { throw new UnsupportedOperationException(); }
    @Override public JpaHeroEntity getOne(UUID uuid) { throw new UnsupportedOperationException(); }
    @Override public JpaHeroEntity getById(UUID uuid) { throw new UnsupportedOperationException(); }
    @Override public <S extends JpaHeroEntity> boolean exists(org.springframework.data.domain.Example<S> example) { throw new UnsupportedOperationException(); }
    @Override public void deleteAll(Iterable<? extends JpaHeroEntity> entities) { throw new UnsupportedOperationException(); }
    @Override public <S extends JpaHeroEntity, R> R findBy(org.springframework.data.domain.Example<S> example, java.util.function.Function<org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery<S>, R> queryFunction) { throw new UnsupportedOperationException(); }
    @Override public void deleteAllByIdInBatch(Iterable<UUID> uuids) { throw new UnsupportedOperationException(); }
    @Override public void deleteAllInBatch(Iterable<JpaHeroEntity> entities) { throw new UnsupportedOperationException(); }
    @Override public <S extends JpaHeroEntity> List<S> findAll(org.springframework.data.domain.Example<S> example) { throw new UnsupportedOperationException(); }
    @Override public <S extends JpaHeroEntity> List<S> findAll(org.springframework.data.domain.Example<S> example, org.springframework.data.domain.Sort sort) { throw new UnsupportedOperationException(); }
    @Override public <S extends JpaHeroEntity> org.springframework.data.domain.Page<S> findAll(org.springframework.data.domain.Example<S> example, org.springframework.data.domain.Pageable pageable) { throw new UnsupportedOperationException(); }
    @Override public List<JpaHeroEntity> findAll(org.springframework.data.domain.Sort sort) { throw new UnsupportedOperationException(); }
    @Override public org.springframework.data.domain.Page<JpaHeroEntity> findAll(org.springframework.data.domain.Pageable pageable) { throw new UnsupportedOperationException(); }
    @Override public <S extends JpaHeroEntity> long count(org.springframework.data.domain.Example<S> example) { throw new UnsupportedOperationException(); }
    @Override public <S extends JpaHeroEntity> Optional<S> findOne(org.springframework.data.domain.Example<S> example) { throw new UnsupportedOperationException(); }
    @Override public void deleteAllById(Iterable<? extends UUID> uuids) { throw new UnsupportedOperationException(); }
    @Override public <S extends JpaHeroEntity> List<S> saveAllAndFlush(Iterable<S> entities) { throw new UnsupportedOperationException(); }
    @Override public JpaHeroEntity getReferenceById(UUID uuid) { throw new UnsupportedOperationException(); }
}