package com.gracefinance.gracefinanceapp.service.criteria.referentiel;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.gracefinance.gracefinanceapp.domain.referentiel.Categorie} entity. This class is used
 * in {@link com.gracefinance.gracefinanceapp.web.rest.referentiel.CategorieResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /categories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CategorieCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nom;

    private StringFilter code;

    private StringFilter typeCategorie;

    private StringFilter description;

    private BooleanFilter actif;

    private LongFilter entiteFinanciereId;

    private Boolean distinct;

    public CategorieCriteria() {}

    public CategorieCriteria(CategorieCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.nom = other.optionalNom().map(StringFilter::copy).orElse(null);
        this.code = other.optionalCode().map(StringFilter::copy).orElse(null);
        this.typeCategorie = other.optionalTypeCategorie().map(StringFilter::copy).orElse(null);
        this.description = other.optionalDescription().map(StringFilter::copy).orElse(null);
        this.actif = other.optionalActif().map(BooleanFilter::copy).orElse(null);
        this.entiteFinanciereId = other.optionalEntiteFinanciereId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public CategorieCriteria copy() {
        return new CategorieCriteria(this);
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

    public StringFilter getTypeCategorie() {
        return typeCategorie;
    }

    public Optional<StringFilter> optionalTypeCategorie() {
        return Optional.ofNullable(typeCategorie);
    }

    public StringFilter typeCategorie() {
        if (typeCategorie == null) {
            setTypeCategorie(new StringFilter());
        }
        return typeCategorie;
    }

    public void setTypeCategorie(StringFilter typeCategorie) {
        this.typeCategorie = typeCategorie;
    }

    public StringFilter getDescription() {
        return description;
    }

    public Optional<StringFilter> optionalDescription() {
        return Optional.ofNullable(description);
    }

    public StringFilter description() {
        if (description == null) {
            setDescription(new StringFilter());
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
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
        final CategorieCriteria that = (CategorieCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(code, that.code) &&
            Objects.equals(typeCategorie, that.typeCategorie) &&
            Objects.equals(description, that.description) &&
            Objects.equals(actif, that.actif) &&
            Objects.equals(entiteFinanciereId, that.entiteFinanciereId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, code, typeCategorie, description, actif, entiteFinanciereId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategorieCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalNom().map(f -> "nom=" + f + ", ").orElse("") +
            optionalCode().map(f -> "code=" + f + ", ").orElse("") +
            optionalTypeCategorie().map(f -> "typeCategorie=" + f + ", ").orElse("") +
            optionalDescription().map(f -> "description=" + f + ", ").orElse("") +
            optionalActif().map(f -> "actif=" + f + ", ").orElse("") +
            optionalEntiteFinanciereId().map(f -> "entiteFinanciereId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
