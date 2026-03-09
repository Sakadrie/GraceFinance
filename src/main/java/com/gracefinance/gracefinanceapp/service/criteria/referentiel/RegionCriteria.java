package com.gracefinance.gracefinanceapp.service.criteria.referentiel;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

public class RegionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;
    private StringFilter nom;
    private StringFilter code;
    private StringFilter description;
    private BooleanFilter actif;

    public RegionCriteria() {}

    public RegionCriteria(RegionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.actif = other.actif == null ? null : other.actif.copy();
    }

    @Override
    public RegionCriteria copy() {
        return new RegionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter id() {
        if (id == null) id = new LongFilter();
        return id;
    }

    public StringFilter getNom() {
        return nom;
    }

    public void setNom(StringFilter nom) {
        this.nom = nom;
    }

    public StringFilter nom() {
        if (nom == null) nom = new StringFilter();
        return nom;
    }

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public StringFilter code() {
        if (code == null) code = new StringFilter();
        return code;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter description() {
        if (description == null) description = new StringFilter();
        return description;
    }

    public BooleanFilter getActif() {
        return actif;
    }

    public void setActif(BooleanFilter actif) {
        this.actif = actif;
    }

    public BooleanFilter actif() {
        if (actif == null) actif = new BooleanFilter();
        return actif;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegionCriteria)) return false;
        RegionCriteria that = (RegionCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(code, that.code) &&
            Objects.equals(description, that.description) &&
            Objects.equals(actif, that.actif)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, code, description, actif);
    }

    @Override
    public String toString() {
        return (
            "RegionCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nom != null ? "nom=" + nom + ", " : "") +
            (code != null ? "code=" + code + ", " : "") +
            (actif != null ? "actif=" + actif : "") +
            "}"
        );
    }
}
