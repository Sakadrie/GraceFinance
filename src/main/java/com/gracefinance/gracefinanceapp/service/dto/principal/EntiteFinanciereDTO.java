package com.gracefinance.gracefinanceapp.service.dto.principal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.gracefinance.gracefinanceapp.domain.principal.EntiteFinanciere} entity.
 */
@Schema(description = "APPLICATION: Gestion Financière d'Église (Norme SYSCOHADA)\nVersion: 1.0")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EntiteFinanciereDTO implements Serializable {

    private Long id;

    @NotNull(message = "must not be null")
    private String nom;

    @NotNull(message = "must not be null")
    private String code;

    @NotNull(message = "must not be null")
    private String type;

    private String description;

    @NotNull(message = "must not be null")
    private Boolean actif;

    private Set<EntiteFinanciereDTO> egliseLiees = new HashSet<>();

    private Set<EntiteFinanciereDTO> structureLiees = new HashSet<>();

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Set<EntiteFinanciereDTO> getEgliseLiees() {
        return egliseLiees;
    }

    public void setEgliseLiees(Set<EntiteFinanciereDTO> egliseLiees) {
        this.egliseLiees = egliseLiees;
    }

    public Set<EntiteFinanciereDTO> getStructureLiees() {
        return structureLiees;
    }

    public void setStructureLiees(Set<EntiteFinanciereDTO> structureLiees) {
        this.structureLiees = structureLiees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EntiteFinanciereDTO)) {
            return false;
        }

        EntiteFinanciereDTO entiteFinanciereDTO = (EntiteFinanciereDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, entiteFinanciereDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EntiteFinanciereDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", code='" + getCode() + "'" +
            ", type='" + getType() + "'" +
            ", description='" + getDescription() + "'" +
            ", actif='" + getActif() + "'" +
            ", egliseLiees=" + getEgliseLiees() +
            ", structureLiees=" + getStructureLiees() +
            "}";
    }
}
