package com.gracefinance.gracefinanceapp.domain.principal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A Caisse.
 */
@Entity
@Table(name = "caisse")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Caisse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    @NotNull
    @Column(name = "devise", nullable = false)
    private String devise;

    @NotNull
    @Column(name = "solde", nullable = false, precision = 21, scale = 2)
    private BigDecimal solde;

    @NotNull
    @Column(name = "actif", nullable = false)
    private Boolean actif;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "egliseLiees", "structureLiees" }, allowSetters = true)
    private EntiteFinanciere entiteFinanciere;

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
    }

    public Caisse entiteFinanciere(EntiteFinanciere entiteFinanciere) {
        this.setEntiteFinanciere(entiteFinanciere);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Caisse)) return false;
        return getId() != null && getId().equals(((Caisse) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "Caisse{" +
            "id=" +
            getId() +
            ", nom='" +
            getNom() +
            "'" +
            ", code='" +
            getCode() +
            "'" +
            ", type='" +
            getType() +
            "'" +
            ", devise='" +
            getDevise() +
            "'" +
            ", solde=" +
            getSolde() +
            ", actif='" +
            getActif() +
            "'" +
            "}"
        );
    }
}
