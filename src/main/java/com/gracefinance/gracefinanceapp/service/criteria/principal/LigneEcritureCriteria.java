package com.gracefinance.gracefinanceapp.service.criteria.principal;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.gracefinance.gracefinanceapp.domain.principal.LigneEcriture} entity. This class is used
 * in {@link com.gracefinance.gracefinanceapp.web.rest.principal.LigneEcritureResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ligne-ecritures?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LigneEcritureCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter montant;

    private StringFilter sens;

    private StringFilter libelle;

    private LongFilter ecritureId;

    private LongFilter compteId;

    private Boolean distinct;

    public LigneEcritureCriteria() {}

    public LigneEcritureCriteria(LigneEcritureCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.montant = other.optionalMontant().map(BigDecimalFilter::copy).orElse(null);
        this.sens = other.optionalSens().map(StringFilter::copy).orElse(null);
        this.libelle = other.optionalLibelle().map(StringFilter::copy).orElse(null);
        this.ecritureId = other.optionalEcritureId().map(LongFilter::copy).orElse(null);
        this.compteId = other.optionalCompteId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public LigneEcritureCriteria copy() {
        return new LigneEcritureCriteria(this);
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

    public StringFilter getSens() {
        return sens;
    }

    public Optional<StringFilter> optionalSens() {
        return Optional.ofNullable(sens);
    }

    public StringFilter sens() {
        if (sens == null) {
            setSens(new StringFilter());
        }
        return sens;
    }

    public void setSens(StringFilter sens) {
        this.sens = sens;
    }

    public StringFilter getLibelle() {
        return libelle;
    }

    public Optional<StringFilter> optionalLibelle() {
        return Optional.ofNullable(libelle);
    }

    public StringFilter libelle() {
        if (libelle == null) {
            setLibelle(new StringFilter());
        }
        return libelle;
    }

    public void setLibelle(StringFilter libelle) {
        this.libelle = libelle;
    }

    public LongFilter getEcritureId() {
        return ecritureId;
    }

    public Optional<LongFilter> optionalEcritureId() {
        return Optional.ofNullable(ecritureId);
    }

    public LongFilter ecritureId() {
        if (ecritureId == null) {
            setEcritureId(new LongFilter());
        }
        return ecritureId;
    }

    public void setEcritureId(LongFilter ecritureId) {
        this.ecritureId = ecritureId;
    }

    public LongFilter getCompteId() {
        return compteId;
    }

    public Optional<LongFilter> optionalCompteId() {
        return Optional.ofNullable(compteId);
    }

    public LongFilter compteId() {
        if (compteId == null) {
            setCompteId(new LongFilter());
        }
        return compteId;
    }

    public void setCompteId(LongFilter compteId) {
        this.compteId = compteId;
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
        final LigneEcritureCriteria that = (LigneEcritureCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(montant, that.montant) &&
            Objects.equals(sens, that.sens) &&
            Objects.equals(libelle, that.libelle) &&
            Objects.equals(ecritureId, that.ecritureId) &&
            Objects.equals(compteId, that.compteId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, montant, sens, libelle, ecritureId, compteId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LigneEcritureCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalMontant().map(f -> "montant=" + f + ", ").orElse("") +
            optionalSens().map(f -> "sens=" + f + ", ").orElse("") +
            optionalLibelle().map(f -> "libelle=" + f + ", ").orElse("") +
            optionalEcritureId().map(f -> "ecritureId=" + f + ", ").orElse("") +
            optionalCompteId().map(f -> "compteId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
