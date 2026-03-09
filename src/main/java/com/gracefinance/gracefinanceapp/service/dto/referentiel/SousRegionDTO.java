package com.gracefinance.gracefinanceapp.service.dto.referentiel;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

public class SousRegionDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    @NotNull
    private String code;

    private String description;

    @NotNull
    private Boolean actif;

    @NotNull
    private Long regionId;

    private String regionNom;

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

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getRegionNom() {
        return regionNom;
    }

    public void setRegionNom(String regionNom) {
        this.regionNom = regionNom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SousRegionDTO)) return false;
        SousRegionDTO that = (SousRegionDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "SousRegionDTO{id=" + id + ", nom='" + nom + "', code='" + code + "', regionId=" + regionId + "}";
    }
}
