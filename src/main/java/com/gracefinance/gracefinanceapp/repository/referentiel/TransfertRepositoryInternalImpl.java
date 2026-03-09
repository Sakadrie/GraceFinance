package com.gracefinance.gracefinanceapp.repository.referentiel;

import com.gracefinance.gracefinanceapp.domain.referentiel.Transfert;
import com.gracefinance.gracefinanceapp.repository.CaisseSqlHelper;
import com.gracefinance.gracefinanceapp.repository.EntiteFinanciereSqlHelper;
import com.gracefinance.gracefinanceapp.repository.EntityManager;
import com.gracefinance.gracefinanceapp.repository.TransfertSqlHelper;
import com.gracefinance.gracefinanceapp.repository.rowmapper.CaisseRowMapper;
import com.gracefinance.gracefinanceapp.repository.rowmapper.ColumnConverter;
import com.gracefinance.gracefinanceapp.repository.rowmapper.EntiteFinanciereRowMapper;
import com.gracefinance.gracefinanceapp.repository.rowmapper.TransfertRowMapper;
import com.gracefinance.gracefinanceapp.service.criteria.referentiel.TransfertCriteria;
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
 * Spring Data R2DBC custom repository implementation for the Transfert entity.
 */
@SuppressWarnings("unused")
class TransfertRepositoryInternalImpl extends SimpleR2dbcRepository<Transfert, Long> implements TransfertRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final EntiteFinanciereRowMapper entitefinanciereMapper;
    private final CaisseRowMapper caisseMapper;
    private final TransfertRowMapper transfertMapper;
    private final ColumnConverter columnConverter;

    private static final Table entityTable = Table.aliased("transfert", EntityManager.ENTITY_ALIAS);
    private static final Table entiteFinanciereSourceTable = Table.aliased("entite_financiere", "entiteFinanciereSource");
    private static final Table caisseSourceTable = Table.aliased("caisse", "caisseSource");
    private static final Table caisseDestinationTable = Table.aliased("caisse", "caisseDestination");

    public TransfertRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        EntiteFinanciereRowMapper entitefinanciereMapper,
        CaisseRowMapper caisseMapper,
        TransfertRowMapper transfertMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter,
        ColumnConverter columnConverter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(Transfert.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.entitefinanciereMapper = entitefinanciereMapper;
        this.caisseMapper = caisseMapper;
        this.transfertMapper = transfertMapper;
        this.columnConverter = columnConverter;
    }

    @Override
    public Flux<Transfert> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<Transfert> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = TransfertSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(EntiteFinanciereSqlHelper.getColumns(entiteFinanciereSourceTable, "entiteFinanciereSource"));
        columns.addAll(CaisseSqlHelper.getColumns(caisseSourceTable, "caisseSource"));
        columns.addAll(CaisseSqlHelper.getColumns(caisseDestinationTable, "caisseDestination"));
        SelectFromAndJoinCondition selectFrom = Select.builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(entiteFinanciereSourceTable)
            .on(Column.create("entite_financiere_source_id", entityTable))
            .equals(Column.create("id", entiteFinanciereSourceTable))
            .leftOuterJoin(caisseSourceTable)
            .on(Column.create("caisse_source_id", entityTable))
            .equals(Column.create("id", caisseSourceTable))
            .leftOuterJoin(caisseDestinationTable)
            .on(Column.create("caisse_destination_id", entityTable))
            .equals(Column.create("id", caisseDestinationTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, Transfert.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<Transfert> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<Transfert> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private Transfert process(Row row, RowMetadata metadata) {
        Transfert entity = transfertMapper.apply(row, "e");
        entity.setEntiteFinanciereSource(entitefinanciereMapper.apply(row, "entiteFinanciereSource"));
        entity.setCaisseSource(caisseMapper.apply(row, "caisseSource"));
        entity.setCaisseDestination(caisseMapper.apply(row, "caisseDestination"));
        return entity;
    }

    @Override
    public <S extends Transfert> Mono<S> save(S entity) {
        return super.save(entity);
    }

    @Override
    public Flux<Transfert> findByCriteria(TransfertCriteria transfertCriteria, Pageable page) {
        return createQuery(page, buildConditions(transfertCriteria)).all();
    }

    @Override
    public Mono<Long> countByCriteria(TransfertCriteria criteria) {
        return findByCriteria(criteria, null)
            .collectList()
            .map(collectedList -> collectedList != null ? (long) collectedList.size() : (long) 0);
    }

    private Condition buildConditions(TransfertCriteria criteria) {
        ConditionBuilder builder = new ConditionBuilder(this.columnConverter);
        List<Condition> allConditions = new ArrayList<Condition>();
        if (criteria != null) {
            if (criteria.getId() != null) {
                builder.buildFilterConditionForField(criteria.getId(), entityTable.column("id"));
            }
            if (criteria.getCode() != null) {
                builder.buildFilterConditionForField(criteria.getCode(), entityTable.column("code"));
            }
            if (criteria.getDateTransfert() != null) {
                builder.buildFilterConditionForField(criteria.getDateTransfert(), entityTable.column("date_transfert"));
            }
            if (criteria.getMontant() != null) {
                builder.buildFilterConditionForField(criteria.getMontant(), entityTable.column("montant"));
            }
            if (criteria.getMotif() != null) {
                builder.buildFilterConditionForField(criteria.getMotif(), entityTable.column("motif"));
            }
            if (criteria.getTypeTransfert() != null) {
                builder.buildFilterConditionForField(criteria.getTypeTransfert(), entityTable.column("type_transfert"));
            }
            if (criteria.getStatut() != null) {
                builder.buildFilterConditionForField(criteria.getStatut(), entityTable.column("statut"));
            }
            if (criteria.getValiderPar() != null) {
                builder.buildFilterConditionForField(criteria.getValiderPar(), entityTable.column("valider_par"));
            }
            if (criteria.getDateValidation() != null) {
                builder.buildFilterConditionForField(criteria.getDateValidation(), entityTable.column("date_validation"));
            }
            if (criteria.getEntiteFinanciereSourceId() != null) {
                builder.buildFilterConditionForField(criteria.getEntiteFinanciereSourceId(), entiteFinanciereSourceTable.column("id"));
            }
            if (criteria.getCaisseSourceId() != null) {
                builder.buildFilterConditionForField(criteria.getCaisseSourceId(), caisseSourceTable.column("id"));
            }
            if (criteria.getCaisseDestinationId() != null) {
                builder.buildFilterConditionForField(criteria.getCaisseDestinationId(), caisseDestinationTable.column("id"));
            }
        }
        return builder.buildConditions();
    }
}
