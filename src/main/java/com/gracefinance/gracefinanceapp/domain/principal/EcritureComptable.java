package com.gracefinance.gracefinanceapp.domain.principal;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A EcritureComptable.
 */
@Entity
@Table(name = "ecriture_comptable")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EcritureComptable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "date_comptable", nullable = false)
    private LocalDate dateComptable;

    @NotNull
    @Column(name = "numero_piece", nullable = false)
    private String numeroPiece;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "reference_externe")
    private String referenceExterne;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return this.id;
    }

    public EcritureComptable id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateComptable() {
        return this.dateComptable;
    }

    public EcritureComptable dateComptable(LocalDate dateComptable) {
        this.setDateComptable(dateComptable);
        return this;
    }

    public void setDateComptable(LocalDate dateComptable) {
        this.dateComptable = dateComptable;
    }

    public String getNumeroPiece() {
        return this.numeroPiece;
    }

    public EcritureComptable numeroPiece(String numeroPiece) {
        this.setNumeroPiece(numeroPiece);
        return this;
    }

    public void setNumeroPiece(String numeroPiece) {
        this.numeroPiece = numeroPiece;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public EcritureComptable libelle(String libelle) {
        this.setLibelle(libelle);
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getReferenceExterne() {
        return this.referenceExterne;
    }

    public EcritureComptable referenceExterne(String referenceExterne) {
        this.setReferenceExterne(referenceExterne);
        return this;
    }

    public void setReferenceExterne(String referenceExterne) {
        this.referenceExterne = referenceExterne;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EcritureComptable)) {
            return false;
        }
        return getId() != null && getId().equals(((EcritureComptable) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "EcritureComptable{" +
            "id=" +
            getId() +
            ", dateComptable='" +
            getDateComptable() +
            "'" +
            ", numeroPiece='" +
            getNumeroPiece() +
            "'" +
            ", libelle='" +
            getLibelle() +
            "'" +
            ", referenceExterne='" +
            getReferenceExterne() +
            "'" +
            "}"
        );
    }
}
