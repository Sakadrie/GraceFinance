package com.gracefinance.gracefinanceapp.service.criteria.security;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.gracefinance.gracefinanceapp.domain.security.AffectationUtilisateur} entity. This class is used
 * in {@link com.gracefinance.gracefinanceapp.web.rest.AffectationUtilisateurResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /affectation-utilisateurs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AffectationUtilisateurCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter actif;

    private LocalDateFilter dateAffectation;

    private LongFilter userId;

    private LongFilter entiteFinanciereId;

    private Boolean distinct;

    public AffectationUtilisateurCriteria() {}

    public AffectationUtilisateurCriteria(AffectationUtilisateurCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.actif = other.optionalActif().map(BooleanFilter::copy).orElse(null);
        this.dateAffectation = other.optionalDateAffectation().map(LocalDateFilter::copy).orElse(null);
        this.userId = other.optionalUserId().map(LongFilter::copy).orElse(null);
        this.entiteFinanciereId = other.optionalEntiteFinanciereId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public AffectationUtilisateurCriteria copy() {
        return new AffectationUtilisateurCriteria(this);
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

    public BooleanFilter getActif() {
        return actif;
    }

    public Optional<BooleanFilter> optionalActif() {
        return Optional.ofNullable(actif);
    }

    public BooleanFilter actif() {
        if (actif == null) {
            setActif(new BooleanFilter());
        }
        return actif;
    }

    public void setActif(BooleanFilter actif) {
        this.actif = actif;
    }

    public LocalDateFilter getDateAffectation() {
        return dateAffectation;
    }

    public Optional<LocalDateFilter> optionalDateAffectation() {
        return Optional.ofNullable(dateAffectation);
    }

    public LocalDateFilter dateAffectation() {
        if (dateAffectation == null) {
            setDateAffectation(new LocalDateFilter());
        }
        return dateAffectation;
    }

    public void setDateAffectation(LocalDateFilter dateAffectation) {
        this.dateAffectation = dateAffectation;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public Optional<LongFilter> optionalUserId() {
        return Optional.ofNullable(userId);
    }

    public LongFilter userId() {
        if (userId == null) {
            setUserId(new LongFilter());
        }
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
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
        final AffectationUtilisateurCriteria that = (AffectationUtilisateurCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(actif, that.actif) &&
            Objects.equals(dateAffectation, that.dateAffectation) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(entiteFinanciereId, that.entiteFinanciereId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, actif, dateAffectation, userId, entiteFinanciereId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AffectationUtilisateurCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalActif().map(f -> "actif=" + f + ", ").orElse("") +
            optionalDateAffectation().map(f -> "dateAffectation=" + f + ", ").orElse("") +
            optionalUserId().map(f -> "userId=" + f + ", ").orElse("") +
            optionalEntiteFinanciereId().map(f -> "entiteFinanciereId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
