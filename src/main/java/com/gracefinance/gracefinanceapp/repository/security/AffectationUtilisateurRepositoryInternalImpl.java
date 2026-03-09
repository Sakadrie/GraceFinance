package com.gracefinance.gracefinanceapp.repository.security;

import com.gracefinance.gracefinanceapp.domain.security.AffectationUtilisateur;
import com.gracefinance.gracefinanceapp.domain.security.Profil;
import com.gracefinance.gracefinanceapp.repository.EntiteFinanciereSqlHelper;
import com.gracefinance.gracefinanceapp.repository.EntityManager;
import com.gracefinance.gracefinanceapp.repository.EntityManager.LinkTable;
import com.gracefinance.gracefinanceapp.repository.UserSqlHelper;
import com.gracefinance.gracefinanceapp.repository.rowmapper.AffectationUtilisateurRowMapper;
import com.gracefinance.gracefinanceapp.repository.rowmapper.ColumnConverter;
import com.gracefinance.gracefinanceapp.repository.rowmapper.EntiteFinanciereRowMapper;
import com.gracefinance.gracefinanceapp.repository.rowmapper.UserRowMapper;
import com.gracefinance.gracefinanceapp.service.criteria.security.AffectationUtilisateurCriteria;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.convert.R2dbcConverter;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.repository.support.SimpleR2dbcRepository;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Comparison;
import org.springframework.data.relational.core.sql.Condition;
import org.springframework.data.relational.core.sql.Conditions;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Select;
import org.springframework.data.relational.core.sql.SelectBuilder.SelectFromAndJoinCondition;
import org.springframework.data.relational.core.sql.Table;
import org.springframework.data.relational.repository.support.MappingRelationalEntityInformation;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.RowsFetchSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.jhipster.service.ConditionBuilder;

/**
 * Spring Data R2DBC custom repository implementation for the AffectationUtilisateur entity.
 */
@SuppressWarnings("unused")
class AffectationUtilisateurRepositoryInternalImpl
    extends SimpleR2dbcRepository<AffectationUtilisateur, Long>
    implements AffectationUtilisateurRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final UserRowMapper userMapper;
    private final EntiteFinanciereRowMapper entitefinanciereMapper;
    private final AffectationUtilisateurRowMapper affectationutilisateurMapper;
    private final ColumnConverter columnConverter;

    private static final Table entityTable = Table.aliased("affectation_utilisateur", EntityManager.ENTITY_ALIAS);
    private static final Table userTable = Table.aliased("jhi_user", "e_user");
    private static final Table entiteFinanciereTable = Table.aliased("entite_financiere", "entiteFinanciere");

    private static final EntityManager.LinkTable profilLink = new EntityManager.LinkTable(
        "rel_affectation_utilisateur__profil",
        "affectation_utilisateur_id",
        "profil_id"
    );

    public AffectationUtilisateurRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        UserRowMapper userMapper,
        EntiteFinanciereRowMapper entitefinanciereMapper,
        AffectationUtilisateurRowMapper affectationutilisateurMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter,
        ColumnConverter columnConverter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(AffectationUtilisateur.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.userMapper = userMapper;
        this.entitefinanciereMapper = entitefinanciereMapper;
        this.affectationutilisateurMapper = affectationutilisateurMapper;
        this.columnConverter = columnConverter;
    }

    @Override
    public Flux<AffectationUtilisateur> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<AffectationUtilisateur> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = AffectationUtilisateurSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(UserSqlHelper.getColumns(userTable, "user"));
        columns.addAll(EntiteFinanciereSqlHelper.getColumns(entiteFinanciereTable, "entiteFinanciere"));
        SelectFromAndJoinCondition selectFrom = Select.builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(userTable)
            .on(Column.create("user_id", entityTable))
            .equals(Column.create("id", userTable))
            .leftOuterJoin(entiteFinanciereTable)
            .on(Column.create("entite_financiere_id", entityTable))
            .equals(Column.create("id", entiteFinanciereTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, AffectationUtilisateur.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<AffectationUtilisateur> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<AffectationUtilisateur> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    @Override
    public Mono<AffectationUtilisateur> findOneWithEagerRelationships(Long id) {
        return findById(id);
    }

    @Override
    public Flux<AffectationUtilisateur> findAllWithEagerRelationships() {
        return findAll();
    }

    @Override
    public Flux<AffectationUtilisateur> findAllWithEagerRelationships(Pageable page) {
        return findAllBy(page);
    }

    private AffectationUtilisateur process(Row row, RowMetadata metadata) {
        AffectationUtilisateur entity = affectationutilisateurMapper.apply(row, "e");
        entity.setUser(userMapper.apply(row, "user"));
        entity.setEntiteFinanciere(entitefinanciereMapper.apply(row, "entiteFinanciere"));
        return entity;
    }

    @Override
    public <S extends AffectationUtilisateur> Mono<S> save(S entity) {
        return super.save(entity).flatMap((S e) -> updateRelations(e));
    }

    protected <S extends AffectationUtilisateur> Mono<S> updateRelations(S entity) {
        Mono<Void> result = entityManager
            .updateLinkTable(profilLink, entity.getId(), entity.getProfils().stream().map(Profil::getId))
            .then();
        return result.thenReturn(entity);
    }

    @Override
    public Mono<Void> deleteById(Long entityId) {
        return deleteRelations(entityId).then(super.deleteById(entityId));
    }

    protected Mono<Void> deleteRelations(Long entityId) {
        return entityManager.deleteFromLinkTable(profilLink, entityId);
    }

    @Override
    public Flux<AffectationUtilisateur> findByCriteria(AffectationUtilisateurCriteria affectationUtilisateurCriteria, Pageable page) {
        return createQuery(page, buildConditions(affectationUtilisateurCriteria)).all();
    }

    @Override
    public Mono<Long> countByCriteria(AffectationUtilisateurCriteria criteria) {
        return findByCriteria(criteria, null)
            .collectList()
            .map(collectedList -> collectedList != null ? (long) collectedList.size() : (long) 0);
    }

    private Condition buildConditions(AffectationUtilisateurCriteria criteria) {
        ConditionBuilder builder = new ConditionBuilder(this.columnConverter);
        List<Condition> allConditions = new ArrayList<Condition>();
        if (criteria != null) {
            if (criteria.getId() != null) {
                builder.buildFilterConditionForField(criteria.getId(), entityTable.column("id"));
            }
            if (criteria.getActif() != null) {
                builder.buildFilterConditionForField(criteria.getActif(), entityTable.column("actif"));
            }
            if (criteria.getDateAffectation() != null) {
                builder.buildFilterConditionForField(criteria.getDateAffectation(), entityTable.column("date_affectation"));
            }
            if (criteria.getUserId() != null) {
                builder.buildFilterConditionForField(criteria.getUserId(), userTable.column("id"));
            }
            if (criteria.getEntiteFinanciereId() != null) {
                builder.buildFilterConditionForField(criteria.getEntiteFinanciereId(), entiteFinanciereTable.column("id"));
            }
        }
        return builder.buildConditions();
    }
}
