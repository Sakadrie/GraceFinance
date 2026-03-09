package com.gracefinance.gracefinanceapp.service.criteria.referentiel;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

public class DistrictCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;
    private StringFilter nom;
    private StringFilter code;
    private StringFilter description;
    private BooleanFilter actif;
    private LongFilter sousRegionId;

    public DistrictCriteria() {}

    public DistrictCriteria(DistrictCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.actif = other.actif == null ? null : other.actif.copy();
        this.sousRegionId = other.sousRegionId == null ? null : other.sousRegionId.copy();
    }

    @Override
    public DistrictCriteria copy() {
        return new DistrictCriteria(this);
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

    public LongFilter getSousRegionId() {
        return sousRegionId;
    }

    public void setSousRegionId(LongFilter sousRegionId) {
        this.sousRegionId = sousRegionId;
    }

    public LongFilter sousRegionId() {
        if (sousRegionId == null) sousRegionId = new LongFilter();
        return sousRegionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DistrictCriteria)) return false;
        DistrictCriteria that = (DistrictCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(code, that.code) &&
            Objects.equals(actif, that.actif) &&
            Objects.equals(sousRegionId, that.sousRegionId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, code, description, actif, sousRegionId);
    }

    @Override
    public String toString() {
        return (
            "DistrictCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nom != null ? "nom=" + nom + ", " : "") +
            (sousRegionId != null ? "sousRegionId=" + sousRegionId : "") +
            "}"
        );
    }
}
