package com.gracefinance.gracefinanceapp.domain.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.gracefinance.gracefinanceapp.domain.principal.Caisse} entity. This class is used
 * in {@link com.gracefinance.gracefinanceapp.web.rest.principal.CaisseResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /caisses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CaisseCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nom;

    private StringFilter code;

    private StringFilter type;

    private StringFilter devise;

    private BigDecimalFilter solde;

    private BooleanFilter actif;

    private LongFilter entiteFinanciereId;

    private Boolean distinct;

    public CaisseCriteria() {}

    public CaisseCriteria(CaisseCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.nom = other.optionalNom().map(StringFilter::copy).orElse(null);
        this.code = other.optionalCode().map(StringFilter::copy).orElse(null);
        this.type = other.optionalType().map(StringFilter::copy).orElse(null);
        this.devise = other.optionalDevise().map(StringFilter::copy).orElse(null);
        this.solde = other.optionalSolde().map(BigDecimalFilter::copy).orElse(null);
        this.actif = other.optionalActif().map(BooleanFilter::copy).orElse(null);
        this.entiteFinanciereId = other.optionalEntiteFinanciereId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public CaisseCriteria copy() {
        return new CaisseCriteria(this);
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

    public StringFilter getNom() {
        return nom;
    }

    public Optional<StringFilter> optionalNom() {
        return Optional.ofNullable(nom);
    }

    public StringFilter nom() {
        if (nom == null) {
            setNom(new StringFilter());
        }
        return nom;
    }

    public void setNom(StringFilter nom) {
        this.nom = nom;
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

    public StringFilter getType() {
        return type;
    }

    public Optional<StringFilter> optionalType() {
        return Optional.ofNullable(type);
    }

    public StringFilter type() {
        if (type == null) {
            setType(new StringFilter());
        }
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
    }

    public StringFilter getDevise() {
        return devise;
    }

    public Optional<StringFilter> optionalDevise() {
        return Optional.ofNullable(devise);
    }

    public StringFilter devise() {
        if (devise == null) {
            setDevise(new StringFilter());
        }
        return devise;
    }

    public void setDevise(StringFilter devise) {
        this.devise = devise;
    }

    public BigDecimalFilter getSolde() {
        return solde;
    }

    public Optional<BigDecimalFilter> optionalSolde() {
        return Optional.ofNullable(solde);
    }

    public BigDecimalFilter solde() {
        if (solde == null) {
            setSolde(new BigDecimalFilter());
        }
        return solde;
    }

    public void setSolde(BigDecimalFilter solde) {
        this.solde = solde;
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
        final CaisseCriteria that = (CaisseCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(code, that.code) &&
            Objects.equals(type, that.type) &&
            Objects.equals(devise, that.devise) &&
            Objects.equals(solde, that.solde) &&
            Objects.equals(actif, that.actif) &&
            Objects.equals(entiteFinanciereId, that.entiteFinanciereId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, code, type, devise, solde, actif, entiteFinanciereId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CaisseCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalNom().map(f -> "nom=" + f + ", ").orElse("") +
            optionalCode().map(f -> "code=" + f + ", ").orElse("") +
            optionalType().map(f -> "type=" + f + ", ").orElse("") +
            optionalDevise().map(f -> "devise=" + f + ", ").orElse("") +
            optionalSolde().map(f -> "solde=" + f + ", ").orElse("") +
            optionalActif().map(f -> "actif=" + f + ", ").orElse("") +
            optionalEntiteFinanciereId().map(f -> "entiteFinanciereId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
