package com.gracefinance.gracefinanceapp.domain.principal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Caisse.
 */
@Table("caisse")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Caisse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @NotNull(message = "must not be null")
    @Column("nom")
    private String nom;

    @NotNull(message = "must not be null")
    @Column("code")
    private String code;

    @NotNull(message = "must not be null")
    @Column("type")
    private String type;

    @NotNull(message = "must not be null")
    @Column("devise")
    private String devise;

    @NotNull(message = "must not be null")
    @Column("solde")
    private BigDecimal solde;

    @NotNull(message = "must not be null")
    @Column("actif")
    private Boolean actif;

    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "egliseLiees", "structureLiees" }, allowSetters = true)
    private EntiteFinanciere entiteFinanciere;

    @Column("entite_financiere_id")
    private Long entiteFinanciereId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Caisse id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Caisse nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCode() {
        return this.code;
    }

    public Caisse code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return this.type;
    }

    public Caisse type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDevise() {
        return this.devise;
    }

    public Caisse devise(String devise) {
        this.setDevise(devise);
        return this;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }

    public BigDecimal getSolde() {
        return this.solde;
    }

    public Caisse solde(BigDecimal solde) {
        this.setSolde(solde);
        return this;
    }

    public void setSolde(BigDecimal solde) {
        this.solde = solde != null ? solde.stripTrailingZeros() : null;
    }

    public Boolean getActif() {
        return this.actif;
    }

    public Caisse actif(Boolean actif) {
        this.setActif(actif);
        return this;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public EntiteFinanciere getEntiteFinanciere() {
        return this.entiteFinanciere;
    }

    public void setEntiteFinanciere(EntiteFinanciere entiteFinanciere) {
        this.entiteFinanciere = entiteFinanciere;
        this.entiteFinanciereId = entiteFinanciere != null ? entiteFinanciere.getId() : null;
    }

    public Caisse entiteFinanciere(EntiteFinanciere entiteFinanciere) {
        this.setEntiteFinanciere(entiteFinanciere);
        return this;
    }

    public Long getEntiteFinanciereId() {
        return this.entiteFinanciereId;
    }

    public void setEntiteFinanciereId(Long entiteFinanciere) {
        this.entiteFinanciereId = entiteFinanciere;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Caisse)) {
            return false;
        }
        return getId() != null && getId().equals(((Caisse) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Caisse{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", code='" + getCode() + "'" +
            ", type='" + getType() + "'" +
            ", devise='" + getDevise() + "'" +
            ", solde=" + getSolde() +
            ", actif='" + getActif() + "'" +
            "}";
    }
}
