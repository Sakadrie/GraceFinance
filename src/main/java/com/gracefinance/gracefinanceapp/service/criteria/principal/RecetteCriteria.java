package com.gracefinance.gracefinanceapp.service.criteria.principal;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.gracefinance.gracefinanceapp.domain.principal.Recette} entity. This class is used
 * in {@link com.gracefinance.gracefinanceapp.web.rest.principal.RecetteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /recettes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RecetteCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private LocalDateFilter dateRecette;

    private BigDecimalFilter montant;

    private StringFilter typeRecette;

    private BooleanFilter anonyme;

    private StringFilter membreNom;

    private StringFilter motif;

    private StringFilter referencePiece;

    private StringFilter statut;

    private LongFilter entiteFinanciereId;

    private LongFilter caisseId;

    private LongFilter categorieId;

    private Boolean distinct;

    public RecetteCriteria() {}

    public RecetteCriteria(RecetteCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.code = other.optionalCode().map(StringFilter::copy).orElse(null);
        this.dateRecette = other.optionalDateRecette().map(LocalDateFilter::copy).orElse(null);
        this.montant = other.optionalMontant().map(BigDecimalFilter::copy).orElse(null);
        this.typeRecette = other.optionalTypeRecette().map(StringFilter::copy).orElse(null);
        this.anonyme = other.optionalAnonyme().map(BooleanFilter::copy).orElse(null);
        this.membreNom = other.optionalMembreNom().map(StringFilter::copy).orElse(null);
        this.motif = other.optionalMotif().map(StringFilter::copy).orElse(null);
        this.referencePiece = other.optionalReferencePiece().map(StringFilter::copy).orElse(null);
        this.statut = other.optionalStatut().map(StringFilter::copy).orElse(null);
        this.entiteFinanciereId = other.optionalEntiteFinanciereId().map(LongFilter::copy).orElse(null);
        this.caisseId = other.optionalCaisseId().map(LongFilter::copy).orElse(null);
        this.categorieId = other.optionalCategorieId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public RecetteCriteria copy() {
        return new RecetteCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCode() {
        return code;
    }

    public Optional<StringFilter> optionalCode() {
        return Optional.ofNullable(code);
    }

    public StringFilter code() {
        if (code == null) {
            setCode(new StringFilter());
        }
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public LocalDateFilter getDateRecette() {
        return dateRecette;
    }

    public Optional<LocalDateFilter> optionalDateRecette() {
        return Optional.ofNullable(dateRecette);
    }

    public LocalDateFilter dateRecette() {
        if (dateRecette == null) {
            setDateRecette(new LocalDateFilter());
        }
        return dateRecette;
    }

    public void setDateRecette(LocalDateFilter dateRecette) {
        this.dateRecette = dateRecette;
    }

    public BigDecimalFilter getMontant() {
        return montant;
    }

    public Optional<BigDecimalFilter> optionalMontant() {
        return Optional.ofNullable(montant);
    }

    public BigDecimalFilter montant() {
        if (montant == null) {
            setMontant(new BigDecimalFilter());
        }
        return montant;
    }

    public void setMontant(BigDecimalFilter montant) {
        this.montant = montant;
    }

    public StringFilter getTypeRecette() {
        return typeRecette;
    }

    public Optional<StringFilter> optionalTypeRecette() {
        return Optional.ofNullable(typeRecette);
    }

    public StringFilter typeRecette() {
        if (typeRecette == null) {
            setTypeRecette(new StringFilter());
        }
        return typeRecette;
    }

    public void setTypeRecette(StringFilter typeRecette) {
        this.typeRecette = typeRecette;
    }

    public BooleanFilter getAnonyme() {
        return anonyme;
    }

    public Optional<BooleanFilter> optionalAnonyme() {
        return Optional.ofNullable(anonyme);
    }

    public BooleanFilter anonyme() {
        if (anonyme == null) {
            setAnonyme(new BooleanFilter());
        }
        return anonyme;
    }

    public void setAnonyme(BooleanFilter anonyme) {
        this.anonyme = anonyme;
    }

    public StringFilter getMembreNom() {
        return membreNom;
    }

    public Optional<StringFilter> optionalMembreNom() {
        return Optional.ofNullable(membreNom);
    }

    public StringFilter membreNom() {
        if (membreNom == null) {
            setMembreNom(new StringFilter());
        }
        return membreNom;
    }

    public void setMembreNom(StringFilter membreNom) {
        this.membreNom = membreNom;
    }

    public StringFilter getMotif() {
        return motif;
    }

    public Optional<StringFilter> optionalMotif() {
        return Optional.ofNullable(motif);
    }

    public StringFilter motif() {
        if (motif == null) {
            setMotif(new StringFilter());
        }
        return motif;
    }

    public void setMotif(StringFilter motif) {
        this.motif = motif;
    }

    public StringFilter getReferencePiece() {
        return referencePiece;
    }

    public Optional<StringFilter> optionalReferencePiece() {
        return Optional.ofNullable(referencePiece);
    }

    public StringFilter referencePiece() {
        if (referencePiece == null) {
            setReferencePiece(new StringFilter());
        }
        return referencePiece;
    }

    public void setReferencePiece(StringFilter referencePiece) {
        this.referencePiece = referencePiece;
    }

    public StringFilter getStatut() {
        return statut;
    }

    public Optional<StringFilter> optionalStatut() {
        return Optional.ofNullable(statut);
    }

    public StringFilter statut() {
        if (statut == null) {
            setStatut(new StringFilter());
        }
        return statut;
    }

    public void setStatut(StringFilter statut) {
        this.statut = statut;
    }

    public LongFilter getEntiteFinanciereId() {
        return entiteFinanciereId;
    }

    public Optional<LongFilter> optionalEntiteFinanciereId() {
        return Optional.ofNullable(entiteFinanciereId);
    }

    public LongFilter entiteFinanciereId() {
        if (entiteFinanciereId == null) {
            setEntiteFinanciereId(new LongFilter());
        }
        return entiteFinanciereId;
    }

    public void setEntiteFinanciereId(LongFilter entiteFinanciereId) {
        this.entiteFinanciereId = entiteFinanciereId;
    }

    public LongFilter getCaisseId() {
        return caisseId;
    }

    public Optional<LongFilter> optionalCaisseId() {
        return Optional.ofNullable(caisseId);
    }

    public LongFilter caisseId() {
        if (caisseId == null) {
            setCaisseId(new LongFilter());
        }
        return caisseId;
    }

    public void setCaisseId(LongFilter caisseId) {
        this.caisseId = caisseId;
    }

    public LongFilter getCategorieId() {
        return categorieId;
    }

    public Optional<LongFilter> optionalCategorieId() {
        return Optional.ofNullable(categorieId);
    }

    public LongFilter categorieId() {
        if (categorieId == null) {
            setCategorieId(new LongFilter());
        }
        return categorieId;
    }

    public void setCategorieId(LongFilter categorieId) {
        this.categorieId = categorieId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RecetteCriteria that = (RecetteCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(dateRecette, that.dateRecette) &&
            Objects.equals(montant, that.montant) &&
            Objects.equals(typeRecette, that.typeRecette) &&
            Objects.equals(anonyme, that.anonyme) &&
            Objects.equals(membreNom, that.membreNom) &&
            Objects.equals(motif, that.motif) &&
            Objects.equals(referencePiece, that.referencePiece) &&
            Objects.equals(statut, that.statut) &&
            Objects.equals(entiteFinanciereId, that.entiteFinanciereId) &&
            Objects.equals(caisseId, that.caisseId) &&
            Objects.equals(categorieId, that.categorieId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            code,
            dateRecette,
            montant,
            typeRecette,
            anonyme,
            membreNom,
            motif,
            referencePiece,
            statut,
            entiteFinanciereId,
            caisseId,
            categorieId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RecetteCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalCode().map(f -> "code=" + f + ", ").orElse("") +
            optionalDateRecette().map(f -> "dateRecette=" + f + ", ").orElse("") +
            optionalMontant().map(f -> "montant=" + f + ", ").orElse("") +
            optionalTypeRecette().map(f -> "typeRecette=" + f + ", ").orElse("") +
            optionalAnonyme().map(f -> "anonyme=" + f + ", ").orElse("") +
            optionalMembreNom().map(f -> "membreNom=" + f + ", ").orElse("") +
            optionalMotif().map(f -> "motif=" + f + ", ").orElse("") +
            optionalReferencePiece().map(f -> "referencePiece=" + f + ", ").orElse("") +
            optionalStatut().map(f -> "statut=" + f + ", ").orElse("") +
            optionalEntiteFinanciereId().map(f -> "entiteFinanciereId=" + f + ", ").orElse("") +
            optionalCaisseId().map(f -> "caisseId=" + f + ", ").orElse("") +
            optionalCategorieId().map(f -> "categorieId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
