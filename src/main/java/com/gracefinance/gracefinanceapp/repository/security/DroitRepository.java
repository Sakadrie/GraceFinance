package com.gracefinance.gracefinanceapp.repository.security;

import com.gracefinance.gracefinanceapp.domain.security.Droit;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Droit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DroitRepository extends ReactiveCrudRepository<Droit, Long>, DroitRepositoryInternal {
    Flux<Droit> findAllBy(Pageable pageable);

    @Override
    <S extends Droit> Mono<S> save(S entity);

    @Override
    Flux<Droit> findAll();

    @Override
    Mono<Droit> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface DroitRepositoryInternal {
    <S extends Droit> Mono<S> save(S entity);

    Flux<Droit> findAllBy(Pageable pageable);

    Flux<Droit> findAll();

    Mono<Droit> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Droit> findAllBy(Pageable pageable, Criteria criteria);
}
