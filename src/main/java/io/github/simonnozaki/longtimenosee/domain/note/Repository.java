package io.github.simonnozaki.longtimenosee.domain.note;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface Repository extends CrudRepository<Entity, Long> {
    @Override
    @NonNull
    List<Entity> findAll();

    @Override
    @NonNull
    Optional<Entity> findById(Long id);

    @Override
    @NonNull
    <S extends Entity> S save(S entity);

    @Override
    void deleteById(Long id);

    @Override
    boolean existsById(Long id);
}
