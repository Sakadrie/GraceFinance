package com.gracefinance.gracefinanceapp.service.dto.principal;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.gracefinance.gracefinanceapp.domain.principal.LigneEcriture} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LigneEcritureDTO implements Serializable {

    private Long id;

    @NotNull(message = "must not be null")
    private BigDecimal montant;

    @NotNull(message = "must not be null")
    private String sens;

    private String libelle;

    @NotNull
    private EcritureComptableDTO ecriture;

    @NotNull
    private CompteComptableDTO compte;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public String getSens() {
        return sens;
    }

    public void setSens(String sens) {
        this.sens = sens;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public EcritureComptableDTO getEcriture() {
        return ecriture;
    }

    public void setEcriture(EcritureComptableDTO ecriture) {
        this.ecriture = ecriture;
    }

    public CompteComptableDTO getCompte() {
        return compte;
    }

    public void setCompte(CompteComptableDTO compte) {
        this.compte = compte;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LigneEcritureDTO)) {
            return false;
        }

        LigneEcritureDTO ligneEcritureDTO = (LigneEcritureDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ligneEcritureDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LigneEcritureDTO{" +
            "id=" + getId() +
            ", montant=" + getMontant() +
            ", sens='" + getSens() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", ecriture=" + getEcriture() +
            ", compte=" + getCompte() +
            "}";
    }
}
