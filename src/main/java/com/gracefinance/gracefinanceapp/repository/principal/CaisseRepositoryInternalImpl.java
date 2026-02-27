package com.gracefinance.gracefinanceapp.repository.principal;

import com.gracefinance.gracefinanceapp.domain.criteria.CaisseCriteria;
import com.gracefinance.gracefinanceapp.domain.principal.Caisse;
import com.gracefinance.gracefinanceapp.repository.CaisseSqlHelper;
import com.gracefinance.gracefinanceapp.repository.EntiteFinanciereSqlHelper;
import com.gracefinance.gracefinanceapp.repository.EntityManager;
import com.gracefinance.gracefinanceapp.repository.rowmapper.CaisseRowMapper;
import com.gracefinance.gracefinanceapp.repository.rowmapper.ColumnConverter;
import com.gracefinance.gracefinanceapp.repository.rowmapper.EntiteFinanciereRowMapper;
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
 * Spring Data R2DBC custom repository implementation for the Caisse entity.
 */
@SuppressWarnings("unused")
class CaisseRepositoryInternalImpl extends SimpleR2dbcRepository<Caisse, Long> implements CaisseRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final EntiteFinanciereRowMapper entitefinanciereMapper;
    private final CaisseRowMapper caisseMapper;
    private final ColumnConverter columnConverter;

    private static final Table entityTable = Table.aliased("caisse", EntityManager.ENTITY_ALIAS);
    private static final Table entiteFinanciereTable = Table.aliased("entite_financiere", "entiteFinanciere");

    public CaisseRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        EntiteFinanciereRowMapper entitefinanciereMapper,
        CaisseRowMapper caisseMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter,
        ColumnConverter columnConverter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(Caisse.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.entitefinanciereMapper = entitefinanciereMapper;
        this.caisseMapper = caisseMapper;
        this.columnConverter = columnConverter;
    }

    @Override
    public Flux<Caisse> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<Caisse> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = CaisseSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(EntiteFinanciereSqlHelper.getColumns(entiteFinanciereTable, "entiteFinanciere"));
        SelectFromAndJoinCondition selectFrom = Select.builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(entiteFinanciereTable)
            .on(Column.create("entite_financiere_id", entityTable))
            .equals(Column.create("id", entiteFinanciereTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, Caisse.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<Caisse> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<Caisse> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private Caisse process(Row row, RowMetadata metadata) {
        Caisse entity = caisseMapper.apply(row, "e");
        entity.setEntiteFinanciere(entitefinanciereMapper.apply(row, "entiteFinanciere"));
        return entity;
    }

    @Override
    public <S extends Caisse> Mono<S> save(S entity) {
        return super.save(entity);
    }

    @Override
    public Flux<Caisse> findByCriteria(CaisseCriteria caisseCriteria, Pageable page) {
        return createQuery(page, buildConditions(caisseCriteria)).all();
    }

    @Override
    public Mono<Long> countByCriteria(CaisseCriteria criteria) {
        return findByCriteria(criteria, null)
            .collectList()
            .map(collectedList -> collectedList != null ? (long) collectedList.size() : (long) 0);
    }

    private Condition buildConditions(CaisseCriteria criteria) {
        ConditionBuilder builder = new ConditionBuilder(this.columnConverter);
        List<Condition> allConditions = new ArrayList<Condition>();
        if (criteria != null) {
            if (criteria.getId() != null) {
                builder.buildFilterConditionForField(criteria.getId(), entityTable.column("id"));
            }
            if (criteria.getNom() != null) {
                builder.buildFilterConditionForField(criteria.getNom(), entityTable.column("nom"));
            }
            if (criteria.getCode() != null) {
                builder.buildFilterConditionForField(criteria.getCode(), entityTable.column("code"));
            }
            if (criteria.getType() != null) {
                builder.buildFilterConditionForField(criteria.getType(), entityTable.column("type"));
            }
            if (criteria.getDevise() != null) {
                builder.buildFilterConditionForField(criteria.getDevise(), entityTable.column("devise"));
            }
            if (criteria.getSolde() != null) {
                builder.buildFilterConditionForField(criteria.getSolde(), entityTable.column("solde"));
            }
            if (criteria.getActif() != null) {
                builder.buildFilterConditionForField(criteria.getActif(), entityTable.column("actif"));
            }
            if (criteria.getEntiteFinanciereId() != null) {
                builder.buildFilterConditionForField(criteria.getEntiteFinanciereId(), entiteFinanciereTable.column("id"));
            }
        }
        return builder.buildConditions();
    }
}
