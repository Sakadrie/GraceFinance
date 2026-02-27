package com.gracefinance.gracefinanceapp.domain.principal;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A LigneEcriture.
 */
@Table("ligne_ecriture")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LigneEcriture implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @NotNull(message = "must not be null")
    @Column("montant")
    private BigDecimal montant;

    @NotNull(message = "must not be null")
    @Column("sens")
    private String sens;

    @Column("libelle")
    private String libelle;

    @org.springframework.data.annotation.Transient
    private EcritureComptable ecriture;

    @org.springframework.data.annotation.Transient
    private CompteComptable compte;

    @Column("ecriture_id")
    private Long ecritureId;

    @Column("compte_id")
    private Long compteId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LigneEcriture id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMontant() {
        return this.montant;
    }

    public LigneEcriture montant(BigDecimal montant) {
        this.setMontant(montant);
        return this;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant != null ? montant.stripTrailingZeros() : null;
    }

    public String getSens() {
        return this.sens;
    }

    public LigneEcriture sens(String sens) {
        this.setSens(sens);
        return this;
    }

    public void setSens(String sens) {
        this.sens = sens;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public LigneEcriture libelle(String libelle) {
        this.setLibelle(libelle);
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public EcritureComptable getEcriture() {
        return this.ecriture;
    }

    public void setEcriture(EcritureComptable ecritureComptable) {
        this.ecriture = ecritureComptable;
        this.ecritureId = ecritureComptable != null ? ecritureComptable.getId() : null;
    }

    public LigneEcriture ecriture(EcritureComptable ecritureComptable) {
        this.setEcriture(ecritureComptable);
        return this;
    }

    public CompteComptable getCompte() {
        return this.compte;
    }

    public void setCompte(CompteComptable compteComptable) {
        this.compte = compteComptable;
        this.compteId = compteComptable != null ? compteComptable.getId() : null;
    }

    public LigneEcriture compte(CompteComptable compteComptable) {
        this.setCompte(compteComptable);
        return this;
    }

    public Long getEcritureId() {
        return this.ecritureId;
    }

    public void setEcritureId(Long ecritureComptable) {
        this.ecritureId = ecritureComptable;
    }

    public Long getCompteId() {
        return this.compteId;
    }

    public void setCompteId(Long compteComptable) {
        this.compteId = compteComptable;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LigneEcriture)) {
            return false;
        }
        return getId() != null && getId().equals(((LigneEcriture) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LigneEcriture{" +
            "id=" + getId() +
            ", montant=" + getMontant() +
            ", sens='" + getSens() + "'" +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
