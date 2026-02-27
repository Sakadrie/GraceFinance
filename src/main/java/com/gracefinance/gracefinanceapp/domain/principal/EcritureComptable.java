package com.gracefinance.gracefinanceapp.domain.principal;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A EcritureComptable.
 */
@Table("ecriture_comptable")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EcritureComptable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @NotNull(message = "must not be null")
    @Column("date_comptable")
    private LocalDate dateComptable;

    @NotNull(message = "must not be null")
    @Column("numero_piece")
    private String numeroPiece;

    @Column("libelle")
    private String libelle;

    @Column("reference_externe")
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

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

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
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EcritureComptable{" +
            "id=" + getId() +
            ", dateComptable='" + getDateComptable() + "'" +
            ", numeroPiece='" + getNumeroPiece() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", referenceExterne='" + getReferenceExterne() + "'" +
            "}";
    }
}
