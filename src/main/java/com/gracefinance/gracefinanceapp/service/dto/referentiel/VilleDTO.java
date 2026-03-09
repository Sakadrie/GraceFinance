package com.gracefinance.gracefinanceapp.service.dto.referentiel;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

public class VilleDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    @NotNull
    private String code;

    private String description;

    @NotNull
    private Boolean actif;

    @NotNull
    private Long districtId;

    private String districtNom;

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

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public String getDistrictNom() {
        return districtNom;
    }

    public void setDistrictNom(String districtNom) {
        this.districtNom = districtNom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VilleDTO)) return false;
        VilleDTO that = (VilleDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "VilleDTO{id=" + id + ", nom='" + nom + "', code='" + code + "', districtId=" + districtId + "}";
    }
}
