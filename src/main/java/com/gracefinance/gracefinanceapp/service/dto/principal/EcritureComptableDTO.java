package com.gracefinance.gracefinanceapp.service.dto.principal;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.gracefinance.gracefinanceapp.domain.principal.EcritureComptable} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EcritureComptableDTO implements Serializable {

    private Long id;

    @NotNull(message = "must not be null")
    private LocalDate dateComptable;

    @NotNull(message = "must not be null")
    private String numeroPiece;

    private String libelle;

    private String referenceExterne;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateComptable() {
        return dateComptable;
    }

    public void setDateComptable(LocalDate dateComptable) {
        this.dateComptable = dateComptable;
    }

    public String getNumeroPiece() {
        return numeroPiece;
    }

    public void setNumeroPiece(String numeroPiece) {
        this.numeroPiece = numeroPiece;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getReferenceExterne() {
        return referenceExterne;
    }

    public void setReferenceExterne(String referenceExterne) {
        this.referenceExterne = referenceExterne;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EcritureComptableDTO)) {
            return false;
        }

        EcritureComptableDTO ecritureComptableDTO = (EcritureComptableDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ecritureComptableDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EcritureComptableDTO{" +
            "id=" + getId() +
            ", dateComptable='" + getDateComptable() + "'" +
            ", numeroPiece='" + getNumeroPiece() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", referenceExterne='" + getReferenceExterne() + "'" +
            "}";
    }
}
