package com.gracefinance.gracefinanceapp.domain.security;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gracefinance.gracefinanceapp.domain.principal.EntiteFinanciere;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A AffectationUtilisateur.
 */
@Entity
@Table(name = "affectation_utilisateur")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AffectationUtilisateur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "actif", nullable = false)
    private Boolean actif;

    @NotNull
    @Column(name = "date_affectation", nullable = false)
    private LocalDate dateAffectation;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "egliseLiees", "structureLiees" }, allowSetters = true)
    private EntiteFinanciere entiteFinanciere;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_affectation_utilisateur__profil",
        joinColumns = @JoinColumn(name = "affectation_utilisateur_id"),
        inverseJoinColumns = @JoinColumn(name = "profil_id")
    )
    @JsonIgnoreProperties(value = { "droits", "affectations" }, allowSetters = true)
    private Set<Profil> profils = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return this.id;
    }

    public AffectationUtilisateur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getActif() {
        return this.actif;
    }

    public AffectationUtilisateur actif(Boolean actif) {
        this.setActif(actif);
        return this;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public LocalDate getDateAffectation() {
        return this.dateAffectation;
    }

    public AffectationUtilisateur dateAffectation(LocalDate dateAffectation) {
        this.setDateAffectation(dateAffectation);
        return this;
    }

    public void setDateAffectation(LocalDate dateAffectation) {
        this.dateAffectation = dateAffectation;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AffectationUtilisateur user(User user) {
        this.setUser(user);
        return this;
    }

    public EntiteFinanciere getEntiteFinanciere() {
        return this.entiteFinanciere;
    }

    public void setEntiteFinanciere(EntiteFinanciere entiteFinanciere) {
        this.entiteFinanciere = entiteFinanciere;
    }

    public AffectationUtilisateur entiteFinanciere(EntiteFinanciere entiteFinanciere) {
        this.setEntiteFinanciere(entiteFinanciere);
        return this;
    }

    public Set<Profil> getProfils() {
        return this.profils;
    }

    public void setProfils(Set<Profil> profils) {
        this.profils = profils;
    }

    public AffectationUtilisateur profils(Set<Profil> profils) {
        this.setProfils(profils);
        return this;
    }

    public AffectationUtilisateur addProfil(Profil profil) {
        this.profils.add(profil);
        return this;
    }

    public AffectationUtilisateur removeProfil(Profil profil) {
        this.profils.remove(profil);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AffectationUtilisateur)) {
            return false;
        }
        return getId() != null && getId().equals(((AffectationUtilisateur) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "AffectationUtilisateur{" +
            "id=" +
            getId() +
            ", actif='" +
            getActif() +
            "'" +
            ", dateAffectation='" +
            getDateAffectation() +
            "'" +
            "}"
        );
    }
}
