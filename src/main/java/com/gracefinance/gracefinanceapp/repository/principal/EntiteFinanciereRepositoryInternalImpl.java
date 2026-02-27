package com.gracefinance.gracefinanceapp.repository.principal;

import com.gracefinance.gracefinanceapp.domain.principal.EntiteFinanciere;
import com.gracefinance.gracefinanceapp.repository.EntiteFinanciereSqlHelper;
import com.gracefinance.gracefinanceapp.repository.EntityManager;
import com.gracefinance.gracefinanceapp.repository.rowmapper.EntiteFinanciereRowMapper;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.convert.R2dbcConverter;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.repository.support.SimpleR2dbcRepository;
import org.springframework.data.relational.core.sql.Comparison;
import org.springframework.data.relational.core.sql.Condition;
import org.springframework.data.relational.core.sql.Conditions;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Select;
import org.springframework.data.relational.core.sql.SelectBuilder.SelectFromAndJoin;
import org.springframework.data.relational.core.sql.Table;
import org.springframework.data.relational.repository.support.MappingRelationalEntityInformation;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.RowsFetchSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC custom repository implementation for the EntiteFinanciere entity.
 */
@SuppressWarnings("unused")
class EntiteFinanciereRepositoryInternalImpl
    extends SimpleR2dbcRepository<EntiteFinanciere, Long>
    implements EntiteFinanciereRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final EntiteFinanciereRowMapper entitefinanciereMapper;

    private static final Table entityTable = Table.aliased("entite_financiere", EntityManager.ENTITY_ALIAS);

    private static final EntityManager.LinkTable egliseLieeLink = new EntityManager.LinkTable(
        "rel_entite_financiere__eglise_liee",
        "entite_financiere_id",
        "eglise_liee_id"
    );

    public EntiteFinanciereRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        EntiteFinanciereRowMapper entitefinanciereMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(EntiteFinanciere.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.entitefinanciereMapper = entitefinanciereMapper;
    }

    @Override
    public Flux<EntiteFinanciere> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<EntiteFinanciere> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = EntiteFinanciereSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        SelectFromAndJoin selectFrom = Select.builder().select(columns).from(entityTable);
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, EntiteFinanciere.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<EntiteFinanciere> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<EntiteFinanciere> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    @Override
    public Mono<EntiteFinanciere> findOneWithEagerRelationships(Long id) {
        return findById(id);
    }

    @Override
    public Flux<EntiteFinanciere> findAllWithEagerRelationships() {
        return findAll();
    }

    @Override
    public Flux<EntiteFinanciere> findAllWithEagerRelationships(Pageable page) {
        return findAllBy(page);
    }

    private EntiteFinanciere process(Row row, RowMetadata metadata) {
        EntiteFinanciere entity = entitefinanciereMapper.apply(row, "e");
        return entity;
    }

    @Override
    public <S extends EntiteFinanciere> Mono<S> save(S entity) {
        return super.save(entity).flatMap((S e) -> updateRelations(e));
    }

    protected <S extends EntiteFinanciere> Mono<S> updateRelations(S entity) {
        Mono<Void> result = entityManager
            .updateLinkTable(egliseLieeLink, entity.getId(), entity.getEgliseLiees().stream().map(EntiteFinanciere::getId))
            .then();
        return result.thenReturn(entity);
    }

    @Override
    public Mono<Void> deleteById(Long entityId) {
        return deleteRelations(entityId).then(super.deleteById(entityId));
    }

    protected Mono<Void> deleteRelations(Long entityId) {
        return entityManager.deleteFromLinkTable(egliseLieeLink, entityId);
    }
}
