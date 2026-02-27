package com.gracefinance.gracefinanceapp.service.dto.principal;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.gracefinance.gracefinanceapp.domain.principal.CompteComptable} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CompteComptableDTO implements Serializable {

    private Long id;

    @NotNull(message = "must not be null")
    private String code;

    @NotNull(message = "must not be null")
    private String libelle;

    @NotNull(message = "must not be null")
    private Integer classe;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Integer getClasse() {
        return classe;
    }

    public void setClasse(Integer classe) {
        this.classe = classe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompteComptableDTO)) {
            return false;
        }

        CompteComptableDTO compteComptableDTO = (CompteComptableDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, compteComptableDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompteComptableDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", classe=" + getClasse() +
            "}";
    }
}
