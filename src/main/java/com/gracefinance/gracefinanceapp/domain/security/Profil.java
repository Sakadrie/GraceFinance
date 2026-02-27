package com.gracefinance.gracefinanceapp.domain.security;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Profil.
 */
@Table("profil")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Profil implements Serializable {

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

    @Column("description")
    private String description;

    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "profils" }, allowSetters = true)
    private Set<Droit> droits = new HashSet<>();

    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "user", "entiteFinanciere", "profils" }, allowSetters = true)
    private Set<AffectationUtilisateur> affectations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Profil id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Profil nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCode() {
        return this.code;
    }

    public Profil code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return this.description;
    }

    public Profil description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Droit> getDroits() {
        return this.droits;
    }

    public void setDroits(Set<Droit> droits) {
        this.droits = droits;
    }

    public Profil droits(Set<Droit> droits) {
        this.setDroits(droits);
        return this;
    }

    public Profil addDroit(Droit droit) {
        this.droits.add(droit);
        return this;
    }

    public Profil removeDroit(Droit droit) {
        this.droits.remove(droit);
        return this;
    }

    public Set<AffectationUtilisateur> getAffectations() {
        return this.affectations;
    }

    public void setAffectations(Set<AffectationUtilisateur> affectationUtilisateurs) {
        if (this.affectations != null) {
            this.affectations.forEach(i -> i.removeProfil(this));
        }
        if (affectationUtilisateurs != null) {
            affectationUtilisateurs.forEach(i -> i.addProfil(this));
        }
        this.affectations = affectationUtilisateurs;
    }

    public Profil affectations(Set<AffectationUtilisateur> affectationUtilisateurs) {
        this.setAffectations(affectationUtilisateurs);
        return this;
    }

    public Profil addAffectation(AffectationUtilisateur affectationUtilisateur) {
        this.affectations.add(affectationUtilisateur);
        affectationUtilisateur.getProfils().add(this);
        return this;
    }

    public Profil removeAffectation(AffectationUtilisateur affectationUtilisateur) {
        this.affectations.remove(affectationUtilisateur);
        affectationUtilisateur.getProfils().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Profil)) {
            return false;
        }
        return getId() != null && getId().equals(((Profil) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Profil{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
