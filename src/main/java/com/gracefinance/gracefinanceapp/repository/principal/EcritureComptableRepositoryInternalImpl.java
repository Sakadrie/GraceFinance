package com.gracefinance.gracefinanceapp.repository.principal;

import com.gracefinance.gracefinanceapp.domain.criteria.EcritureComptableCriteria;
import com.gracefinance.gracefinanceapp.domain.principal.EcritureComptable;
import com.gracefinance.gracefinanceapp.repository.EcritureComptableSqlHelper;
import com.gracefinance.gracefinanceapp.repository.EntityManager;
import com.gracefinance.gracefinanceapp.repository.rowmapper.ColumnConverter;
import com.gracefinance.gracefinanceapp.repository.rowmapper.EcritureComptableRowMapper;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import java.util.ArrayList;
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
import tech.jhipster.service.ConditionBuilder;

/**
 * Spring Data R2DBC custom repository implementation for the EcritureComptable entity.
 */
@SuppressWarnings("unused")
class EcritureComptableRepositoryInternalImpl
    extends SimpleR2dbcRepository<EcritureComptable, Long>
    implements EcritureComptableRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final EcritureComptableRowMapper ecriturecomptableMapper;
    private final ColumnConverter columnConverter;

    private static final Table entityTable = Table.aliased("ecriture_comptable", EntityManager.ENTITY_ALIAS);

    public EcritureComptableRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        EcritureComptableRowMapper ecriturecomptableMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter,
        ColumnConverter columnConverter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(EcritureComptable.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.ecriturecomptableMapper = ecriturecomptableMapper;
        this.columnConverter = columnConverter;
    }

    @Override
    public Flux<EcritureComptable> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<EcritureComptable> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = EcritureComptableSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        SelectFromAndJoin selectFrom = Select.builder().select(columns).from(entityTable);
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, EcritureComptable.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<EcritureComptable> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<EcritureComptable> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private EcritureComptable process(Row row, RowMetadata metadata) {
        EcritureComptable entity = ecriturecomptableMapper.apply(row, "e");
        return entity;
    }

    @Override
    public <S extends EcritureComptable> Mono<S> save(S entity) {
        return super.save(entity);
    }

    @Override
    public Flux<EcritureComptable> findByCriteria(EcritureComptableCriteria ecritureComptableCriteria, Pageable page) {
        return createQuery(page, buildConditions(ecritureComptableCriteria)).all();
    }

    @Override
    public Mono<Long> countByCriteria(EcritureComptableCriteria criteria) {
        return findByCriteria(criteria, null)
            .collectList()
            .map(collectedList -> collectedList != null ? (long) collectedList.size() : (long) 0);
    }

    private Condition buildConditions(EcritureComptableCriteria criteria) {
        ConditionBuilder builder = new ConditionBuilder(this.columnConverter);
        List<Condition> allConditions = new ArrayList<Condition>();
        if (criteria != null) {
            if (criteria.getId() != null) {
                builder.buildFilterConditionForField(criteria.getId(), entityTable.column("id"));
            }
            if (criteria.getDateComptable() != null) {
                builder.buildFilterConditionForField(criteria.getDateComptable(), entityTable.column("date_comptable"));
            }
            if (criteria.getNumeroPiece() != null) {
                builder.buildFilterConditionForField(criteria.getNumeroPiece(), entityTable.column("numero_piece"));
            }
            if (criteria.getLibelle() != null) {
                builder.buildFilterConditionForField(criteria.getLibelle(), entityTable.column("libelle"));
            }
            if (criteria.getReferenceExterne() != null) {
                builder.buildFilterConditionForField(criteria.getReferenceExterne(), entityTable.column("reference_externe"));
            }
        }
        return builder.buildConditions();
    }
}
