package com.gracefinance.gracefinanceapp.domain.referentiel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gracefinance.gracefinanceapp.domain.principal.EntiteFinanciere;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Categorie.
 */
@Entity
@Table(name = "categorie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Categorie implements Serializable {

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
    @Column(name = "type_categorie", nullable = false)
    private String typeCategorie;

    @Column(name = "description")
    private String description;

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

    public Categorie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Categorie nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCode() {
        return this.code;
    }

    public Categorie code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTypeCategorie() {
        return this.typeCategorie;
    }

    public Categorie typeCategorie(String typeCategorie) {
        this.setTypeCategorie(typeCategorie);
        return this;
    }

    public void setTypeCategorie(String typeCategorie) {
        this.typeCategorie = typeCategorie;
    }

    public String getDescription() {
        return this.description;
    }

    public Categorie description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActif() {
        return this.actif;
    }

    public Categorie actif(Boolean actif) {
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

    public Categorie entiteFinanciere(EntiteFinanciere entiteFinanciere) {
        this.setEntiteFinanciere(entiteFinanciere);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Categorie)) {
            return false;
        }
        return getId() != null && getId().equals(((Categorie) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "Categorie{" +
            "id=" +
            getId() +
            ", nom='" +
            getNom() +
            "'" +
            ", code='" +
            getCode() +
            "'" +
            ", typeCategorie='" +
            getTypeCategorie() +
            "'" +
            ", description='" +
            getDescription() +
            "'" +
            ", actif='" +
            getActif() +
            "'" +
            "}"
        );
    }
}
