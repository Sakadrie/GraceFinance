package com.gracefinance.gracefinanceapp.domain.security;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Droit.
 */
@Entity
@Table(name = "droit")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Droit implements Serializable {

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

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "droits")
    @JsonIgnoreProperties(value = { "droits", "affectations" }, allowSetters = true)
    private Set<Profil> profils = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return this.id;
    }

    public Droit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Droit nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCode() {
        return this.code;
    }

    public Droit code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return this.description;
    }

    public Droit description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Profil> getProfils() {
        return this.profils;
    }

    public void setProfils(Set<Profil> profils) {
        if (this.profils != null) {
            this.profils.forEach(i -> i.removeDroit(this));
        }
        if (profils != null) {
            profils.forEach(i -> i.addDroit(this));
        }
        this.profils = profils;
    }

    public Droit profils(Set<Profil> profils) {
        this.setProfils(profils);
        return this;
    }

    public Droit addProfil(Profil profil) {
        this.profils.add(profil);
        profil.getDroits().add(this);
        return this;
    }

    public Droit removeProfil(Profil profil) {
        this.profils.remove(profil);
        profil.getDroits().remove(this);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Droit)) {
            return false;
        }
        return getId() != null && getId().equals(((Droit) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "Droit{" +
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
