package com.gracefinance.gracefinanceapp.service.dto.security;

import com.gracefinance.gracefinanceapp.service.dto.principal.EntiteFinanciereDTO;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.gracefinance.gracefinanceapp.domain.security.AffectationUtilisateur} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AffectationUtilisateurDTO implements Serializable {

    private Long id;

    @NotNull(message = "must not be null")
    private Boolean actif;

    @NotNull(message = "must not be null")
    private LocalDate dateAffectation;

    @NotNull
    private UserDTO user;

    @NotNull
    private EntiteFinanciereDTO entiteFinanciere;

    private Set<ProfilDTO> profils = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public LocalDate getDateAffectation() {
        return dateAffectation;
    }

    public void setDateAffectation(LocalDate dateAffectation) {
        this.dateAffectation = dateAffectation;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public EntiteFinanciereDTO getEntiteFinanciere() {
        return entiteFinanciere;
    }

    public void setEntiteFinanciere(EntiteFinanciereDTO entiteFinanciere) {
        this.entiteFinanciere = entiteFinanciere;
    }

    public Set<ProfilDTO> getProfils() {
        return profils;
    }

    public void setProfils(Set<ProfilDTO> profils) {
        this.profils = profils;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AffectationUtilisateurDTO)) {
            return false;
        }

        AffectationUtilisateurDTO affectationUtilisateurDTO = (AffectationUtilisateurDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, affectationUtilisateurDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AffectationUtilisateurDTO{" +
            "id=" + getId() +
            ", actif='" + getActif() + "'" +
            ", dateAffectation='" + getDateAffectation() + "'" +
            ", user=" + getUser() +
            ", entiteFinanciere=" + getEntiteFinanciere() +
            ", profils=" + getProfils() +
            "}";
    }
}
