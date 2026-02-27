package com.gracefinance.gracefinanceapp.repository.principal;

import com.gracefinance.gracefinanceapp.domain.criteria.LigneEcritureCriteria;
import com.gracefinance.gracefinanceapp.domain.principal.LigneEcriture;
import com.gracefinance.gracefinanceapp.repository.CompteComptableSqlHelper;
import com.gracefinance.gracefinanceapp.repository.EcritureComptableSqlHelper;
import com.gracefinance.gracefinanceapp.repository.EntityManager;
import com.gracefinance.gracefinanceapp.repository.LigneEcritureSqlHelper;
import com.gracefinance.gracefinanceapp.repository.rowmapper.ColumnConverter;
import com.gracefinance.gracefinanceapp.repository.rowmapper.CompteComptableRowMapper;
import com.gracefinance.gracefinanceapp.repository.rowmapper.EcritureComptableRowMapper;
import com.gracefinance.gracefinanceapp.repository.rowmapper.LigneEcritureRowMapper;
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
 * Spring Data R2DBC custom repository implementation for the LigneEcriture entity.
 */
@SuppressWarnings("unused")
class LigneEcritureRepositoryInternalImpl extends SimpleR2dbcRepository<LigneEcriture, Long> implements LigneEcritureRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final EcritureComptableRowMapper ecriturecomptableMapper;
    private final CompteComptableRowMapper comptecomptableMapper;
    private final LigneEcritureRowMapper ligneecritureMapper;
    private final ColumnConverter columnConverter;

    private static final Table entityTable = Table.aliased("ligne_ecriture", EntityManager.ENTITY_ALIAS);
    private static final Table ecritureTable = Table.aliased("ecriture_comptable", "ecriture");
    private static final Table compteTable = Table.aliased("compte_comptable", "compte");

    public LigneEcritureRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        EcritureComptableRowMapper ecriturecomptableMapper,
        CompteComptableRowMapper comptecomptableMapper,
        LigneEcritureRowMapper ligneecritureMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter,
        ColumnConverter columnConverter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(LigneEcriture.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.ecriturecomptableMapper = ecriturecomptableMapper;
        this.comptecomptableMapper = comptecomptableMapper;
        this.ligneecritureMapper = ligneecritureMapper;
        this.columnConverter = columnConverter;
    }

    @Override
    public Flux<LigneEcriture> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<LigneEcriture> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = LigneEcritureSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(EcritureComptableSqlHelper.getColumns(ecritureTable, "ecriture"));
        columns.addAll(CompteComptableSqlHelper.getColumns(compteTable, "compte"));
        SelectFromAndJoinCondition selectFrom = Select.builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(ecritureTable)
            .on(Column.create("ecriture_id", entityTable))
            .equals(Column.create("id", ecritureTable))
            .leftOuterJoin(compteTable)
            .on(Column.create("compte_id", entityTable))
            .equals(Column.create("id", compteTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, LigneEcriture.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<LigneEcriture> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<LigneEcriture> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private LigneEcriture process(Row row, RowMetadata metadata) {
        LigneEcriture entity = ligneecritureMapper.apply(row, "e");
        entity.setEcriture(ecriturecomptableMapper.apply(row, "ecriture"));
        entity.setCompte(comptecomptableMapper.apply(row, "compte"));
        return entity;
    }

    @Override
    public <S extends LigneEcriture> Mono<S> save(S entity) {
        return super.save(entity);
    }

    @Override
    public Flux<LigneEcriture> findByCriteria(LigneEcritureCriteria ligneEcritureCriteria, Pageable page) {
        return createQuery(page, buildConditions(ligneEcritureCriteria)).all();
    }

    @Override
    public Mono<Long> countByCriteria(LigneEcritureCriteria criteria) {
        return findByCriteria(criteria, null)
            .collectList()
            .map(collectedList -> collectedList != null ? (long) collectedList.size() : (long) 0);
    }

    private Condition buildConditions(LigneEcritureCriteria criteria) {
        ConditionBuilder builder = new ConditionBuilder(this.columnConverter);
        List<Condition> allConditions = new ArrayList<Condition>();
        if (criteria != null) {
            if (criteria.getId() != null) {
                builder.buildFilterConditionForField(criteria.getId(), entityTable.column("id"));
            }
            if (criteria.getMontant() != null) {
                builder.buildFilterConditionForField(criteria.getMontant(), entityTable.column("montant"));
            }
            if (criteria.getSens() != null) {
                builder.buildFilterConditionForField(criteria.getSens(), entityTable.column("sens"));
            }
            if (criteria.getLibelle() != null) {
                builder.buildFilterConditionForField(criteria.getLibelle(), entityTable.column("libelle"));
            }
            if (criteria.getEcritureId() != null) {
                builder.buildFilterConditionForField(criteria.getEcritureId(), ecritureTable.column("id"));
            }
            if (criteria.getCompteId() != null) {
                builder.buildFilterConditionForField(criteria.getCompteId(), compteTable.column("id"));
            }
        }
        return builder.buildConditions();
    }
}
