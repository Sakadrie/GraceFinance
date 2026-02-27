package com.gracefinance.gracefinanceapp.domain.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.gracefinance.gracefinanceapp.domain.referentiel.Transfert} entity. This class is used
 * in {@link com.gracefinance.gracefinanceapp.web.rest.referentiel.TransfertResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /transferts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TransfertCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private LocalDateFilter dateTransfert;

    private BigDecimalFilter montant;

    private StringFilter motif;

    private StringFilter typeTransfert;

    private StringFilter statut;

    private StringFilter validerPar;

    private InstantFilter dateValidation;

    private LongFilter entiteFinanciereSourceId;

    private LongFilter caisseSourceId;

    private LongFilter caisseDestinationId;

    private Boolean distinct;

    public TransfertCriteria() {}

    public TransfertCriteria(TransfertCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.code = other.optionalCode().map(StringFilter::copy).orElse(null);
        this.dateTransfert = other.optionalDateTransfert().map(LocalDateFilter::copy).orElse(null);
        this.montant = other.optionalMontant().map(BigDecimalFilter::copy).orElse(null);
        this.motif = other.optionalMotif().map(StringFilter::copy).orElse(null);
        this.typeTransfert = other.optionalTypeTransfert().map(StringFilter::copy).orElse(null);
        this.statut = other.optionalStatut().map(StringFilter::copy).orElse(null);
        this.validerPar = other.optionalValiderPar().map(StringFilter::copy).orElse(null);
        this.dateValidation = other.optionalDateValidation().map(InstantFilter::copy).orElse(null);
        this.entiteFinanciereSourceId = other.optionalEntiteFinanciereSourceId().map(LongFilter::copy).orElse(null);
        this.caisseSourceId = other.optionalCaisseSourceId().map(LongFilter::copy).orElse(null);
        this.caisseDestinationId = other.optionalCaisseDestinationId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public TransfertCriteria copy() {
        return new TransfertCriteria(this);
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

    public LocalDateFilter getDateTransfert() {
        return dateTransfert;
    }

    public Optional<LocalDateFilter> optionalDateTransfert() {
        return Optional.ofNullable(dateTransfert);
    }

    public LocalDateFilter dateTransfert() {
        if (dateTransfert == null) {
            setDateTransfert(new LocalDateFilter());
        }
        return dateTransfert;
    }

    public void setDateTransfert(LocalDateFilter dateTransfert) {
        this.dateTransfert = dateTransfert;
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

    public StringFilter getTypeTransfert() {
        return typeTransfert;
    }

    public Optional<StringFilter> optionalTypeTransfert() {
        return Optional.ofNullable(typeTransfert);
    }

    public StringFilter typeTransfert() {
        if (typeTransfert == null) {
            setTypeTransfert(new StringFilter());
        }
        return typeTransfert;
    }

    public void setTypeTransfert(StringFilter typeTransfert) {
        this.typeTransfert = typeTransfert;
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

    public LongFilter getEntiteFinanciereSourceId() {
        return entiteFinanciereSourceId;
    }

    public Optional<LongFilter> optionalEntiteFinanciereSourceId() {
        return Optional.ofNullable(entiteFinanciereSourceId);
    }

    public LongFilter entiteFinanciereSourceId() {
        if (entiteFinanciereSourceId == null) {
            setEntiteFinanciereSourceId(new LongFilter());
        }
        return entiteFinanciereSourceId;
    }

    public void setEntiteFinanciereSourceId(LongFilter entiteFinanciereSourceId) {
        this.entiteFinanciereSourceId = entiteFinanciereSourceId;
    }

    public LongFilter getCaisseSourceId() {
        return caisseSourceId;
    }

    public Optional<LongFilter> optionalCaisseSourceId() {
        return Optional.ofNullable(caisseSourceId);
    }

    public LongFilter caisseSourceId() {
        if (caisseSourceId == null) {
            setCaisseSourceId(new LongFilter());
        }
        return caisseSourceId;
    }

    public void setCaisseSourceId(LongFilter caisseSourceId) {
        this.caisseSourceId = caisseSourceId;
    }

    public LongFilter getCaisseDestinationId() {
        return caisseDestinationId;
    }

    public Optional<LongFilter> optionalCaisseDestinationId() {
        return Optional.ofNullable(caisseDestinationId);
    }

    public LongFilter caisseDestinationId() {
        if (caisseDestinationId == null) {
            setCaisseDestinationId(new LongFilter());
        }
        return caisseDestinationId;
    }

    public void setCaisseDestinationId(LongFilter caisseDestinationId) {
        this.caisseDestinationId = caisseDestinationId;
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
        final TransfertCriteria that = (TransfertCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(dateTransfert, that.dateTransfert) &&
            Objects.equals(montant, that.montant) &&
            Objects.equals(motif, that.motif) &&
            Objects.equals(typeTransfert, that.typeTransfert) &&
            Objects.equals(statut, that.statut) &&
            Objects.equals(validerPar, that.validerPar) &&
            Objects.equals(dateValidation, that.dateValidation) &&
            Objects.equals(entiteFinanciereSourceId, that.entiteFinanciereSourceId) &&
            Objects.equals(caisseSourceId, that.caisseSourceId) &&
            Objects.equals(caisseDestinationId, that.caisseDestinationId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            code,
            dateTransfert,
            montant,
            motif,
            typeTransfert,
            statut,
            validerPar,
            dateValidation,
            entiteFinanciereSourceId,
            caisseSourceId,
            caisseDestinationId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransfertCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalCode().map(f -> "code=" + f + ", ").orElse("") +
            optionalDateTransfert().map(f -> "dateTransfert=" + f + ", ").orElse("") +
            optionalMontant().map(f -> "montant=" + f + ", ").orElse("") +
            optionalMotif().map(f -> "motif=" + f + ", ").orElse("") +
            optionalTypeTransfert().map(f -> "typeTransfert=" + f + ", ").orElse("") +
            optionalStatut().map(f -> "statut=" + f + ", ").orElse("") +
            optionalValiderPar().map(f -> "validerPar=" + f + ", ").orElse("") +
            optionalDateValidation().map(f -> "dateValidation=" + f + ", ").orElse("") +
            optionalEntiteFinanciereSourceId().map(f -> "entiteFinanciereSourceId=" + f + ", ").orElse("") +
            optionalCaisseSourceId().map(f -> "caisseSourceId=" + f + ", ").orElse("") +
            optionalCaisseDestinationId().map(f -> "caisseDestinationId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
