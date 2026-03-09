package com.gracefinance.gracefinanceapp.domain.principal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gracefinance.gracefinanceapp.domain.referentiel.Ville;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * APPLICATION: Gestion Financière d'Église (Norme SYSCOHADA) Version: 1.0
 */
@Entity
@Table(name = "entite_financiere")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EntiteFinanciere implements Serializable {

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

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "actif", nullable = false)
    private Boolean actif;

    @ManyToOne
    @JoinColumn(name = "ville_id")
    private Ville ville;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_entite_financiere__eglise_liee",
        joinColumns = @JoinColumn(name = "entite_financiere_id"),
        inverseJoinColumns = @JoinColumn(name = "eglise_liee_id")
    )
    @JsonIgnoreProperties(value = { "egliseLiees", "structureLiees" }, allowSetters = true)
    private Set<EntiteFinanciere> egliseLiees = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "egliseLiees")
    @JsonIgnoreProperties(value = { "egliseLiees", "structureLiees" }, allowSetters = true)
    private Set<EntiteFinanciere> structureLiees = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return this.id;
    }

    public EntiteFinanciere id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public EntiteFinanciere nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCode() {
        return this.code;
    }

    public EntiteFinanciere code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return this.type;
    }

    public EntiteFinanciere type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return this.description;
    }

    public EntiteFinanciere description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActif() {
        return this.actif;
    }

    public EntiteFinanciere actif(Boolean actif) {
        this.setActif(actif);
        return this;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public Set<EntiteFinanciere> getEgliseLiees() {
        return this.egliseLiees;
    }

    public void setEgliseLiees(Set<EntiteFinanciere> entiteFinancieres) {
        this.egliseLiees = entiteFinancieres;
    }

    public EntiteFinanciere egliseLiees(Set<EntiteFinanciere> entiteFinancieres) {
        this.setEgliseLiees(entiteFinancieres);
        return this;
    }

    public EntiteFinanciere addEgliseLiee(EntiteFinanciere e) {
        this.egliseLiees.add(e);
        return this;
    }

    public EntiteFinanciere removeEgliseLiee(EntiteFinanciere e) {
        this.egliseLiees.remove(e);
        return this;
    }

    public Set<EntiteFinanciere> getStructureLiees() {
        return this.structureLiees;
    }

    public void setStructureLiees(Set<EntiteFinanciere> entiteFinancieres) {
        if (this.structureLiees != null) {
            this.structureLiees.forEach(i -> i.removeEgliseLiee(this));
        }
        if (entiteFinancieres != null) {
            entiteFinancieres.forEach(i -> i.addEgliseLiee(this));
        }
        this.structureLiees = entiteFinancieres;
    }

    public EntiteFinanciere structureLiees(Set<EntiteFinanciere> entiteFinancieres) {
        this.setStructureLiees(entiteFinancieres);
        return this;
    }

    public EntiteFinanciere addStructureLiee(EntiteFinanciere e) {
        this.structureLiees.add(e);
        e.getEgliseLiees().add(this);
        return this;
    }

    public EntiteFinanciere removeStructureLiee(EntiteFinanciere e) {
        this.structureLiees.remove(e);
        e.getEgliseLiees().remove(this);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EntiteFinanciere)) {
            return false;
        }
        return getId() != null && getId().equals(((EntiteFinanciere) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "EntiteFinanciere{" +
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
