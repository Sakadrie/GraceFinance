package com.gracefinance.gracefinanceapp.domain.principal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * APPLICATION: Gestion Financière d'Église (Norme SYSCOHADA)
 * Version: 1.0
 */
@Table("entite_financiere")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EntiteFinanciere implements Serializable {

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

    @Column("description")
    private String description;

    @NotNull(message = "must not be null")
    @Column("actif")
    private Boolean actif;

    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "egliseLiees", "structureLiees" }, allowSetters = true)
    private Set<EntiteFinanciere> egliseLiees = new HashSet<>();

    @org.springframework.data.annotation.Transient
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

    public EntiteFinanciere addEgliseLiee(EntiteFinanciere entiteFinanciere) {
        this.egliseLiees.add(entiteFinanciere);
        return this;
    }

    public EntiteFinanciere removeEgliseLiee(EntiteFinanciere entiteFinanciere) {
        this.egliseLiees.remove(entiteFinanciere);
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

    public EntiteFinanciere addStructureLiee(EntiteFinanciere entiteFinanciere) {
        this.structureLiees.add(entiteFinanciere);
        entiteFinanciere.getEgliseLiees().add(this);
        return this;
    }

    public EntiteFinanciere removeStructureLiee(EntiteFinanciere entiteFinanciere) {
        this.structureLiees.remove(entiteFinanciere);
        entiteFinanciere.getEgliseLiees().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

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
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EntiteFinanciere{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", code='" + getCode() + "'" +
            ", type='" + getType() + "'" +
            ", description='" + getDescription() + "'" +
            ", actif='" + getActif() + "'" +
            "}";
    }
}
