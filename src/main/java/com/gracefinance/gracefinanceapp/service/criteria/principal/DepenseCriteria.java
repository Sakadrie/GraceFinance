package com.gracefinance.gracefinanceapp.service.criteria.principal;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BigDecimalFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.gracefinance.gracefinanceapp.domain.principal.Depense} entity. This class is used
 * in {@link com.gracefinance.gracefinanceapp.web.rest.principal.DepenseResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /depenses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DepenseCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private LocalDateFilter dateDepense;

    private BigDecimalFilter montant;

    private StringFilter motif;

    private StringFilter referencePiece;

    private StringFilter statut;

    private StringFilter validerPar;

    private InstantFilter dateValidation;

    private LongFilter entiteFinanciereId;

    private LongFilter caisseId;

    private LongFilter categorieId;

    private Boolean distinct;

    public DepenseCriteria() {}

    public DepenseCriteria(DepenseCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.code = other.optionalCode().map(StringFilter::copy).orElse(null);
        this.dateDepense = other.optionalDateDepense().map(LocalDateFilter::copy).orElse(null);
        this.montant = other.optionalMontant().map(BigDecimalFilter::copy).orElse(null);
        this.motif = other.optionalMotif().map(StringFilter::copy).orElse(null);
        this.referencePiece = other.optionalReferencePiece().map(StringFilter::copy).orElse(null);
        this.statut = other.optionalStatut().map(StringFilter::copy).orElse(null);
        this.validerPar = other.optionalValiderPar().map(StringFilter::copy).orElse(null);
        this.dateValidation = other.optionalDateValidation().map(InstantFilter::copy).orElse(null);
        this.entiteFinanciereId = other.optionalEntiteFinanciereId().map(LongFilter::copy).orElse(null);
        this.caisseId = other.optionalCaisseId().map(LongFilter::copy).orElse(null);
        this.categorieId = other.optionalCategorieId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public DepenseCriteria copy() {
        return new DepenseCriteria(this);
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

    public LocalDateFilter getDateDepense() {
        return dateDepense;
    }

    public Optional<LocalDateFilter> optionalDateDepense() {
        return Optional.ofNullable(dateDepense);
    }

    public LocalDateFilter dateDepense() {
        if (dateDepense == null) {
            setDateDepense(new LocalDateFilter());
        }
        return dateDepense;
    }

    public void setDateDepense(LocalDateFilter dateDepense) {
        this.dateDepense = dateDepense;
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

    public StringFilter getValiderPar() {
        return validerPar;
    }

    public Optional<StringFilter> optionalValiderPar() {
        return Optional.ofNullable(validerPar);
    }

    public StringFilter validerPar() {
        if (validerPar == null) {
            setValiderPar(new StringFilter());
        }
        return validerPar;
    }

    public void setValiderPar(StringFilter validerPar) {
        this.validerPar = validerPar;
    }

    public InstantFilter getDateValidation() {
        return dateValidation;
    }

    public Optional<InstantFilter> optionalDateValidation() {
        return Optional.ofNullable(dateValidation);
    }

    public InstantFilter dateValidation() {
        if (dateValidation == null) {
            setDateValidation(new InstantFilter());
        }
        return dateValidation;
    }

    public void setDateValidation(InstantFilter dateValidation) {
        this.dateValidation = dateValidation;
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
        final DepenseCriteria that = (DepenseCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(dateDepense, that.dateDepense) &&
            Objects.equals(montant, that.montant) &&
            Objects.equals(motif, that.motif) &&
            Objects.equals(referencePiece, that.referencePiece) &&
            Objects.equals(statut, that.statut) &&
            Objects.equals(validerPar, that.validerPar) &&
            Objects.equals(dateValidation, that.dateValidation) &&
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
            dateDepense,
            montant,
            motif,
            referencePiece,
            statut,
            validerPar,
            dateValidation,
            entiteFinanciereId,
            caisseId,
            categorieId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DepenseCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalCode().map(f -> "code=" + f + ", ").orElse("") +
            optionalDateDepense().map(f -> "dateDepense=" + f + ", ").orElse("") +
            optionalMontant().map(f -> "montant=" + f + ", ").orElse("") +
            optionalMotif().map(f -> "motif=" + f + ", ").orElse("") +
            optionalReferencePiece().map(f -> "referencePiece=" + f + ", ").orElse("") +
            optionalStatut().map(f -> "statut=" + f + ", ").orElse("") +
            optionalValiderPar().map(f -> "validerPar=" + f + ", ").orElse("") +
            optionalDateValidation().map(f -> "dateValidation=" + f + ", ").orElse("") +
            optionalEntiteFinanciereId().map(f -> "entiteFinanciereId=" + f + ", ").orElse("") +
            optionalCaisseId().map(f -> "caisseId=" + f + ", ").orElse("") +
            optionalCategorieId().map(f -> "categorieId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
