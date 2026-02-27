package com.gracefinance.gracefinanceapp.domain.security;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gracefinance.gracefinanceapp.domain.principal.EntiteFinanciere;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A AffectationUtilisateur.
 */
@Table("affectation_utilisateur")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AffectationUtilisateur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @NotNull(message = "must not be null")
    @Column("actif")
    private Boolean actif;

    @NotNull(message = "must not be null")
    @Column("date_affectation")
    private LocalDate dateAffectation;

    @org.springframework.data.annotation.Transient
    private User user;

    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "egliseLiees", "structureLiees" }, allowSetters = true)
    private EntiteFinanciere entiteFinanciere;

    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "droits", "affectations" }, allowSetters = true)
    private Set<Profil> profils = new HashSet<>();

    @Column("user_id")
    private Long userId;

    @Column("entite_financiere_id")
    private Long entiteFinanciereId;

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
        this.userId = user != null ? user.getId() : null;
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
        this.entiteFinanciereId = entiteFinanciere != null ? entiteFinanciere.getId() : null;
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

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long user) {
        this.userId = user;
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
        if (!(o instanceof AffectationUtilisateur)) {
            return false;
        }
        return getId() != null && getId().equals(((AffectationUtilisateur) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AffectationUtilisateur{" +
            "id=" + getId() +
            ", actif='" + getActif() + "'" +
            ", dateAffectation='" + getDateAffectation() + "'" +
            "}";
    }
}
