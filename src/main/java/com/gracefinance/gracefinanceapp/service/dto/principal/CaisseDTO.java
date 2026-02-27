package com.gracefinance.gracefinanceapp.service.dto.principal;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.gracefinance.gracefinanceapp.domain.principal.Caisse} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CaisseDTO implements Serializable {

    private Long id;

    @NotNull(message = "must not be null")
    private String nom;

    @NotNull(message = "must not be null")
    private String code;

    @NotNull(message = "must not be null")
    private String type;

    @NotNull(message = "must not be null")
    private String devise;

    @NotNull(message = "must not be null")
    private BigDecimal solde;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDevise() {
        return devise;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }

    public BigDecimal getSolde() {
        return solde;
    }

    public void setSolde(BigDecimal solde) {
        this.solde = solde;
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
        if (!(o instanceof CaisseDTO)) {
            return false;
        }

        CaisseDTO caisseDTO = (CaisseDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, caisseDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CaisseDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", code='" + getCode() + "'" +
            ", type='" + getType() + "'" +
            ", devise='" + getDevise() + "'" +
            ", solde=" + getSolde() +
            ", actif='" + getActif() + "'" +
            ", entiteFinanciere=" + getEntiteFinanciere() +
            "}";
    }
}
