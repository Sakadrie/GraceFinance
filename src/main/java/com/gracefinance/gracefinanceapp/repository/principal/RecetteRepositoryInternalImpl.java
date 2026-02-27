package com.gracefinance.gracefinanceapp.repository.principal;

import com.gracefinance.gracefinanceapp.domain.criteria.RecetteCriteria;
import com.gracefinance.gracefinanceapp.domain.principal.Recette;
import com.gracefinance.gracefinanceapp.repository.CaisseSqlHelper;
import com.gracefinance.gracefinanceapp.repository.CategorieSqlHelper;
import com.gracefinance.gracefinanceapp.repository.EntiteFinanciereSqlHelper;
import com.gracefinance.gracefinanceapp.repository.EntityManager;
import com.gracefinance.gracefinanceapp.repository.RecetteSqlHelper;
import com.gracefinance.gracefinanceapp.repository.rowmapper.CaisseRowMapper;
import com.gracefinance.gracefinanceapp.repository.rowmapper.CategorieRowMapper;
import com.gracefinance.gracefinanceapp.repository.rowmapper.ColumnConverter;
import com.gracefinance.gracefinanceapp.repository.rowmapper.EntiteFinanciereRowMapper;
import com.gracefinance.gracefinanceapp.repository.rowmapper.RecetteRowMapper;
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
 * Spring Data R2DBC custom repository implementation for the Recette entity.
 */
@SuppressWarnings("unused")
class RecetteRepositoryInternalImpl extends SimpleR2dbcRepository<Recette, Long> implements RecetteRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final EntiteFinanciereRowMapper entitefinanciereMapper;
    private final CaisseRowMapper caisseMapper;
    private final CategorieRowMapper categorieMapper;
    private final RecetteRowMapper recetteMapper;
    private final ColumnConverter columnConverter;

    private static final Table entityTable = Table.aliased("recette", EntityManager.ENTITY_ALIAS);
    private static final Table entiteFinanciereTable = Table.aliased("entite_financiere", "entiteFinanciere");
    private static final Table caisseTable = Table.aliased("caisse", "caisse");
    private static final Table categorieTable = Table.aliased("categorie", "categorie");

    public RecetteRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        EntiteFinanciereRowMapper entitefinanciereMapper,
        CaisseRowMapper caisseMapper,
        CategorieRowMapper categorieMapper,
        RecetteRowMapper recetteMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter,
        ColumnConverter columnConverter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(Recette.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.entitefinanciereMapper = entitefinanciereMapper;
        this.caisseMapper = caisseMapper;
        this.categorieMapper = categorieMapper;
        this.recetteMapper = recetteMapper;
        this.columnConverter = columnConverter;
    }

    @Override
    public Flux<Recette> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<Recette> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = RecetteSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(EntiteFinanciereSqlHelper.getColumns(entiteFinanciereTable, "entiteFinanciere"));
        columns.addAll(CaisseSqlHelper.getColumns(caisseTable, "caisse"));
        columns.addAll(CategorieSqlHelper.getColumns(categorieTable, "categorie"));
        SelectFromAndJoinCondition selectFrom = Select.builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(entiteFinanciereTable)
            .on(Column.create("entite_financiere_id", entityTable))
            .equals(Column.create("id", entiteFinanciereTable))
            .leftOuterJoin(caisseTable)
            .on(Column.create("caisse_id", entityTable))
            .equals(Column.create("id", caisseTable))
            .leftOuterJoin(categorieTable)
            .on(Column.create("categorie_id", entityTable))
            .equals(Column.create("id", categorieTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, Recette.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<Recette> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<Recette> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private Recette process(Row row, RowMetadata metadata) {
        Recette entity = recetteMapper.apply(row, "e");
        entity.setEntiteFinanciere(entitefinanciereMapper.apply(row, "entiteFinanciere"));
        entity.setCaisse(caisseMapper.apply(row, "caisse"));
        entity.setCategorie(categorieMapper.apply(row, "categorie"));
        return entity;
    }

    @Override
    public <S extends Recette> Mono<S> save(S entity) {
        return super.save(entity);
    }

    @Override
    public Flux<Recette> findByCriteria(RecetteCriteria recetteCriteria, Pageable page) {
        return createQuery(page, buildConditions(recetteCriteria)).all();
    }

    @Override
    public Mono<Long> countByCriteria(RecetteCriteria criteria) {
        return findByCriteria(criteria, null)
            .collectList()
            .map(collectedList -> collectedList != null ? (long) collectedList.size() : (long) 0);
    }

    private Condition buildConditions(RecetteCriteria criteria) {
        ConditionBuilder builder = new ConditionBuilder(this.columnConverter);
        List<Condition> allConditions = new ArrayList<Condition>();
        if (criteria != null) {
            if (criteria.getId() != null) {
                builder.buildFilterConditionForField(criteria.getId(), entityTable.column("id"));
            }
            if (criteria.getCode() != null) {
                builder.buildFilterConditionForField(criteria.getCode(), entityTable.column("code"));
            }
            if (criteria.getDateRecette() != null) {
                builder.buildFilterConditionForField(criteria.getDateRecette(), entityTable.column("date_recette"));
            }
            if (criteria.getMontant() != null) {
                builder.buildFilterConditionForField(criteria.getMontant(), entityTable.column("montant"));
            }
            if (criteria.getTypeRecette() != null) {
                builder.buildFilterConditionForField(criteria.getTypeRecette(), entityTable.column("type_recette"));
            }
            if (criteria.getAnonyme() != null) {
                builder.buildFilterConditionForField(criteria.getAnonyme(), entityTable.column("anonyme"));
            }
            if (criteria.getMembreNom() != null) {
                builder.buildFilterConditionForField(criteria.getMembreNom(), entityTable.column("membre_nom"));
            }
            if (criteria.getMotif() != null) {
                builder.buildFilterConditionForField(criteria.getMotif(), entityTable.column("motif"));
            }
            if (criteria.getReferencePiece() != null) {
                builder.buildFilterConditionForField(criteria.getReferencePiece(), entityTable.column("reference_piece"));
            }
            if (criteria.getStatut() != null) {
                builder.buildFilterConditionForField(criteria.getStatut(), entityTable.column("statut"));
            }
            if (criteria.getEntiteFinanciereId() != null) {
                builder.buildFilterConditionForField(criteria.getEntiteFinanciereId(), entiteFinanciereTable.column("id"));
            }
            if (criteria.getCaisseId() != null) {
                builder.buildFilterConditionForField(criteria.getCaisseId(), caisseTable.column("id"));
            }
            if (criteria.getCategorieId() != null) {
                builder.buildFilterConditionForField(criteria.getCategorieId(), categorieTable.column("id"));
            }
        }
        return builder.buildConditions();
    }
}
