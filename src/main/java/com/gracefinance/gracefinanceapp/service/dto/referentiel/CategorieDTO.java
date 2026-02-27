package com.gracefinance.gracefinanceapp.service.dto.referentiel;

import com.gracefinance.gracefinanceapp.service.dto.principal.EntiteFinanciereDTO;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.gracefinance.gracefinanceapp.domain.referentiel.Categorie} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CategorieDTO implements Serializable {

    private Long id;

    @NotNull(message = "must not be null")
    private String nom;

    @NotNull(message = "must not be null")
    private String code;

    @NotNull(message = "must not be null")
    private String typeCategorie;

    private String description;

    @NotNull(message = "must not be null")
    private Boolean actif;

    @NotNull
    private EntiteFinanciereDTO entiteFinanciere;

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

    public String getTypeCategorie() {
        return typeCategorie;
    }

    public void setTypeCategorie(String typeCategorie) {
        this.typeCategorie = typeCategorie;
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

    public EntiteFinanciereDTO getEntiteFinanciere() {
        return entiteFinanciere;
    }

    public void setEntiteFinanciere(EntiteFinanciereDTO entiteFinanciere) {
        this.entiteFinanciere = entiteFinanciere;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategorieDTO)) {
            return false;
        }

        CategorieDTO categorieDTO = (CategorieDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, categorieDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategorieDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", code='" + getCode() + "'" +
            ", typeCategorie='" + getTypeCategorie() + "'" +
            ", description='" + getDescription() + "'" +
            ", actif='" + getActif() + "'" +
            ", entiteFinanciere=" + getEntiteFinanciere() +
            "}";
    }
}
