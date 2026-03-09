package com.gracefinance.gracefinanceapp.service.criteria.referentiel;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

public class SousRegionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;
    private StringFilter nom;
    private StringFilter code;
    private StringFilter description;
    private BooleanFilter actif;
    private LongFilter regionId;

    public SousRegionCriteria() {}

    public SousRegionCriteria(SousRegionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.actif = other.actif == null ? null : other.actif.copy();
        this.regionId = other.regionId == null ? null : other.regionId.copy();
    }

    @Override
    public SousRegionCriteria copy() {
        return new SousRegionCriteria(this);
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

    public LongFilter getRegionId() {
        return regionId;
    }

    public void setRegionId(LongFilter regionId) {
        this.regionId = regionId;
    }

    public LongFilter regionId() {
        if (regionId == null) regionId = new LongFilter();
        return regionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SousRegionCriteria)) return false;
        SousRegionCriteria that = (SousRegionCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(code, that.code) &&
            Objects.equals(actif, that.actif) &&
            Objects.equals(regionId, that.regionId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, code, description, actif, regionId);
    }

    @Override
    public String toString() {
        return (
            "SousRegionCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nom != null ? "nom=" + nom + ", " : "") +
            (regionId != null ? "regionId=" + regionId : "") +
            "}"
        );
    }
}
