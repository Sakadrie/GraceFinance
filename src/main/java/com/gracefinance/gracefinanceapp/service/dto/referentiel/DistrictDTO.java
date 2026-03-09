package com.gracefinance.gracefinanceapp.service.dto.referentiel;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

public class DistrictDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    @NotNull
    private String code;

    private String description;

    @NotNull
    private Boolean actif;

    @NotNull
    private Long sousRegionId;

    private String sousRegionNom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public Long getSousRegionId() {
        return sousRegionId;
    }

    public void setSousRegionId(Long sousRegionId) {
        this.sousRegionId = sousRegionId;
    }

    public String getSousRegionNom() {
        return sousRegionNom;
    }

    public void setSousRegionNom(String sousRegionNom) {
        this.sousRegionNom = sousRegionNom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DistrictDTO)) return false;
        DistrictDTO that = (DistrictDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "DistrictDTO{id=" + id + ", nom='" + nom + "', code='" + code + "', sousRegionId=" + sousRegionId + "}";
    }
}
