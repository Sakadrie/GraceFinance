package com.gracefinance.gracefinanceapp.domain.security;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Profil.
 */
@Entity
@Table(name = "profil")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Profil implements Serializable {

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

    @Column(name = "description")
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_profil__droit",
        joinColumns = @JoinColumn(name = "profil_id"),
        inverseJoinColumns = @JoinColumn(name = "droit_id")
    )
    @JsonIgnoreProperties(value = { "profils" }, allowSetters = true)
    private Set<Droit> droits = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "profils")
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

    public Profil addAffectation(AffectationUtilisateur a) {
        this.affectations.add(a);
        a.getProfils().add(this);
        return this;
    }

    public Profil removeAffectation(AffectationUtilisateur a) {
        this.affectations.remove(a);
        a.getProfils().remove(this);
        return this;
    }

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
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "Profil{" +
            "id=" +
            getId() +
            ", nom='" +
            getNom() +
            "'" +
            ", code='" +
            getCode() +
            "'" +
            ", description='" +
            getDescription() +
            "'" +
            "}"
        );
    }
}
