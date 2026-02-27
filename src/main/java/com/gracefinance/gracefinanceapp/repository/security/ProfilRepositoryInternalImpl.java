package com.gracefinance.gracefinanceapp.repository.security;

import com.gracefinance.gracefinanceapp.domain.security.Droit;
import com.gracefinance.gracefinanceapp.domain.security.Profil;
import com.gracefinance.gracefinanceapp.repository.EntityManager;
import com.gracefinance.gracefinanceapp.repository.EntityManager.LinkTable;
import com.gracefinance.gracefinanceapp.repository.ProfilSqlHelper;
import com.gracefinance.gracefinanceapp.repository.rowmapper.ProfilRowMapper;
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
 * Spring Data R2DBC custom repository implementation for the Profil entity.
 */
@SuppressWarnings("unused")
class ProfilRepositoryInternalImpl extends SimpleR2dbcRepository<Profil, Long> implements ProfilRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final ProfilRowMapper profilMapper;

    private static final Table entityTable = Table.aliased("profil", EntityManager.ENTITY_ALIAS);

    private static final EntityManager.LinkTable droitLink = new EntityManager.LinkTable("rel_profil__droit", "profil_id", "droit_id");

    public ProfilRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        ProfilRowMapper profilMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(Profil.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.profilMapper = profilMapper;
    }

    @Override
    public Flux<Profil> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<Profil> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = ProfilSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        SelectFromAndJoin selectFrom = Select.builder().select(columns).from(entityTable);
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, Profil.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<Profil> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<Profil> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    @Override
    public Mono<Profil> findOneWithEagerRelationships(Long id) {
        return findById(id);
    }

    @Override
    public Flux<Profil> findAllWithEagerRelationships() {
        return findAll();
    }

    @Override
    public Flux<Profil> findAllWithEagerRelationships(Pageable page) {
        return findAllBy(page);
    }

    private Profil process(Row row, RowMetadata metadata) {
        Profil entity = profilMapper.apply(row, "e");
        return entity;
    }

    @Override
    public <S extends Profil> Mono<S> save(S entity) {
        return super.save(entity).flatMap((S e) -> updateRelations(e));
    }

    protected <S extends Profil> Mono<S> updateRelations(S entity) {
        Mono<Void> result = entityManager.updateLinkTable(droitLink, entity.getId(), entity.getDroits().stream().map(Droit::getId)).then();
        return result.thenReturn(entity);
    }

    @Override
    public Mono<Void> deleteById(Long entityId) {
        return deleteRelations(entityId).then(super.deleteById(entityId));
    }

    protected Mono<Void> deleteRelations(Long entityId) {
        return entityManager.deleteFromLinkTable(droitLink, entityId);
    }
}
