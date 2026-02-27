package com.gracefinance.gracefinanceapp.service.dto.security;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.gracefinance.gracefinanceapp.domain.security.Profil} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProfilDTO implements Serializable {

    private Long id;

    @NotNull(message = "must not be null")
    private String nom;

    @NotNull(message = "must not be null")
    private String code;

    private String description;

    private Set<DroitDTO> droits = new HashSet<>();

    private Set<AffectationUtilisateurDTO> affectations = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<DroitDTO> getDroits() {
        return droits;
    }

    public void setDroits(Set<DroitDTO> droits) {
        this.droits = droits;
    }

    public Set<AffectationUtilisateurDTO> getAffectations() {
        return affectations;
    }

    public void setAffectations(Set<AffectationUtilisateurDTO> affectations) {
        this.affectations = affectations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProfilDTO)) {
            return false;
        }

        ProfilDTO profilDTO = (ProfilDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, profilDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProfilDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", droits=" + getDroits() +
            ", affectations=" + getAffectations() +
            "}";
    }
}
