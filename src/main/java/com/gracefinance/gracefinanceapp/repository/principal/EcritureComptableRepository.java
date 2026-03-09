package com.gracefinance.gracefinanceapp.repository.principal;

import com.gracefinance.gracefinanceapp.domain.principal.EcritureComptable;
import com.gracefinance.gracefinanceapp.service.criteria.principal.EcritureComptableCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the EcritureComptable entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EcritureComptableRepository extends ReactiveCrudRepository<EcritureComptable, Long>, EcritureComptableRepositoryInternal {
    Flux<EcritureComptable> findAllBy(Pageable pageable);

    @Override
    <S extends EcritureComptable> Mono<S> save(S entity);

    @Override
    Flux<EcritureComptable> findAll();

    @Override
    Mono<EcritureComptable> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface EcritureComptableRepositoryInternal {
    <S extends EcritureComptable> Mono<S> save(S entity);

    Flux<EcritureComptable> findAllBy(Pageable pageable);

    Flux<EcritureComptable> findAll();

    Mono<EcritureComptable> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<EcritureComptable> findAllBy(Pageable pageable, Criteria criteria);
    Flux<EcritureComptable> findByCriteria(EcritureComptableCriteria criteria, Pageable pageable);

    Mono<Long> countByCriteria(EcritureComptableCriteria criteria);
}
