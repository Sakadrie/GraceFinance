package com.gracefinance.gracefinanceapp.repository.principal;

import com.gracefinance.gracefinanceapp.domain.criteria.CompteComptableCriteria;
import com.gracefinance.gracefinanceapp.domain.principal.CompteComptable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the CompteComptable entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompteComptableRepository extends ReactiveCrudRepository<CompteComptable, Long>, CompteComptableRepositoryInternal {
    Flux<CompteComptable> findAllBy(Pageable pageable);

    @Override
    <S extends CompteComptable> Mono<S> save(S entity);

    @Override
    Flux<CompteComptable> findAll();

    @Override
    Mono<CompteComptable> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface CompteComptableRepositoryInternal {
    <S extends CompteComptable> Mono<S> save(S entity);

    Flux<CompteComptable> findAllBy(Pageable pageable);

    Flux<CompteComptable> findAll();

    Mono<CompteComptable> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<CompteComptable> findAllBy(Pageable pageable, Criteria criteria);
    Flux<CompteComptable> findByCriteria(CompteComptableCriteria criteria, Pageable pageable);

    Mono<Long> countByCriteria(CompteComptableCriteria criteria);
}
