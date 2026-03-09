package com.gracefinance.gracefinanceapp.service.criteria.principal;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.gracefinance.gracefinanceapp.domain.principal.EcritureComptable} entity. This class is used
 * in {@link com.gracefinance.gracefinanceapp.web.rest.principal.EcritureComptableResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ecriture-comptables?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EcritureComptableCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter dateComptable;

    private StringFilter numeroPiece;

    private StringFilter libelle;

    private StringFilter referenceExterne;

    private Boolean distinct;

    public EcritureComptableCriteria() {}

    public EcritureComptableCriteria(EcritureComptableCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.dateComptable = other.optionalDateComptable().map(LocalDateFilter::copy).orElse(null);
        this.numeroPiece = other.optionalNumeroPiece().map(StringFilter::copy).orElse(null);
        this.libelle = other.optionalLibelle().map(StringFilter::copy).orElse(null);
        this.referenceExterne = other.optionalReferenceExterne().map(StringFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public EcritureComptableCriteria copy() {
        return new EcritureComptableCriteria(this);
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

    public LocalDateFilter getDateComptable() {
        return dateComptable;
    }

    public Optional<LocalDateFilter> optionalDateComptable() {
        return Optional.ofNullable(dateComptable);
    }

    public LocalDateFilter dateComptable() {
        if (dateComptable == null) {
            setDateComptable(new LocalDateFilter());
        }
        return dateComptable;
    }

    public void setDateComptable(LocalDateFilter dateComptable) {
        this.dateComptable = dateComptable;
    }

    public StringFilter getNumeroPiece() {
        return numeroPiece;
    }

    public Optional<StringFilter> optionalNumeroPiece() {
        return Optional.ofNullable(numeroPiece);
    }

    public StringFilter numeroPiece() {
        if (numeroPiece == null) {
            setNumeroPiece(new StringFilter());
        }
        return numeroPiece;
    }

    public void setNumeroPiece(StringFilter numeroPiece) {
        this.numeroPiece = numeroPiece;
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

    public StringFilter getReferenceExterne() {
        return referenceExterne;
    }

    public Optional<StringFilter> optionalReferenceExterne() {
        return Optional.ofNullable(referenceExterne);
    }

    public StringFilter referenceExterne() {
        if (referenceExterne == null) {
            setReferenceExterne(new StringFilter());
        }
        return referenceExterne;
    }

    public void setReferenceExterne(StringFilter referenceExterne) {
        this.referenceExterne = referenceExterne;
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
        final EcritureComptableCriteria that = (EcritureComptableCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(dateComptable, that.dateComptable) &&
            Objects.equals(numeroPiece, that.numeroPiece) &&
            Objects.equals(libelle, that.libelle) &&
            Objects.equals(referenceExterne, that.referenceExterne) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateComptable, numeroPiece, libelle, referenceExterne, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EcritureComptableCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalDateComptable().map(f -> "dateComptable=" + f + ", ").orElse("") +
            optionalNumeroPiece().map(f -> "numeroPiece=" + f + ", ").orElse("") +
            optionalLibelle().map(f -> "libelle=" + f + ", ").orElse("") +
            optionalReferenceExterne().map(f -> "referenceExterne=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
