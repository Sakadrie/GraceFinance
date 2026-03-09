package com.gracefinance.gracefinanceapp.domain.principal;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A LigneEcriture.
 */
@Entity
@Table(name = "ligne_ecriture")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LigneEcriture implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "montant", nullable = false, precision = 21, scale = 2)
    private BigDecimal montant;

    @NotNull
    @Column(name = "sens", nullable = false)
    private String sens;

    @Column(name = "libelle")
    private String libelle;

    @ManyToOne(fetch = FetchType.LAZY)
    private EcritureComptable ecriture;

    @ManyToOne(fetch = FetchType.LAZY)
    private CompteComptable compte;

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

    public void setEcriture(EcritureComptable ecriture) {
        this.ecriture = ecriture;
    }

    public LigneEcriture ecriture(EcritureComptable ecriture) {
        this.setEcriture(ecriture);
        return this;
    }

    public CompteComptable getCompte() {
        return this.compte;
    }

    public void setCompte(CompteComptable compte) {
        this.compte = compte;
    }

    public LigneEcriture compte(CompteComptable compte) {
        this.setCompte(compte);
        return this;
    }

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
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "LigneEcriture{" +
            "id=" +
            getId() +
            ", montant=" +
            getMontant() +
            ", sens='" +
            getSens() +
            "'" +
            ", libelle='" +
            getLibelle() +
            "'" +
            "}"
        );
    }
}
