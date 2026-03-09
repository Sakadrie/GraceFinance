package com.gracefinance.gracefinanceapp.domain.principal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gracefinance.gracefinanceapp.domain.referentiel.Categorie;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

/**
 * A Depense.
 */
@Entity
@Table(name = "depense")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Depense implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "date_depense", nullable = false)
    private LocalDate dateDepense;

    @NotNull
    @Column(name = "montant", nullable = false, precision = 21, scale = 2)
    private BigDecimal montant;

    @NotNull
    @Column(name = "motif", nullable = false)
    private String motif;

    @Column(name = "reference_piece")
    private String referencePiece;

    @NotNull
    @Column(name = "statut", nullable = false)
    private String statut;

    @Column(name = "valider_par")
    private String validerPar;

    @Column(name = "date_validation")
    private Instant dateValidation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "egliseLiees", "structureLiees" }, allowSetters = true)
    private EntiteFinanciere entiteFinanciere;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "entiteFinanciere" }, allowSetters = true)
    private Caisse caisse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "entiteFinanciere" }, allowSetters = true)
    private Categorie categorie;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return this.id;
    }

    public Depense id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Depense code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getDateDepense() {
        return this.dateDepense;
    }

    public Depense dateDepense(LocalDate dateDepense) {
        this.setDateDepense(dateDepense);
        return this;
    }

    public void setDateDepense(LocalDate dateDepense) {
        this.dateDepense = dateDepense;
    }

    public BigDecimal getMontant() {
        return this.montant;
    }

    public Depense montant(BigDecimal montant) {
        this.setMontant(montant);
        return this;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant != null ? montant.stripTrailingZeros() : null;
    }

    public String getMotif() {
        return this.motif;
    }

    public Depense motif(String motif) {
        this.setMotif(motif);
        return this;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getReferencePiece() {
        return this.referencePiece;
    }

    public Depense referencePiece(String referencePiece) {
        this.setReferencePiece(referencePiece);
        return this;
    }

    public void setReferencePiece(String referencePiece) {
        this.referencePiece = referencePiece;
    }

    public String getStatut() {
        return this.statut;
    }

    public Depense statut(String statut) {
        this.setStatut(statut);
        return this;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getValiderPar() {
        return this.validerPar;
    }

    public Depense validerPar(String validerPar) {
        this.setValiderPar(validerPar);
        return this;
    }

    public void setValiderPar(String validerPar) {
        this.validerPar = validerPar;
    }

    public Instant getDateValidation() {
        return this.dateValidation;
    }

    public Depense dateValidation(Instant dateValidation) {
        this.setDateValidation(dateValidation);
        return this;
    }

    public void setDateValidation(Instant dateValidation) {
        this.dateValidation = dateValidation;
    }

    public EntiteFinanciere getEntiteFinanciere() {
        return this.entiteFinanciere;
    }

    public void setEntiteFinanciere(EntiteFinanciere entiteFinanciere) {
        this.entiteFinanciere = entiteFinanciere;
    }

    public Depense entiteFinanciere(EntiteFinanciere entiteFinanciere) {
        this.setEntiteFinanciere(entiteFinanciere);
        return this;
    }

    public Caisse getCaisse() {
        return this.caisse;
    }

    public void setCaisse(Caisse caisse) {
        this.caisse = caisse;
    }

    public Depense caisse(Caisse caisse) {
        this.setCaisse(caisse);
        return this;
    }

    public Categorie getCategorie() {
        return this.categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Depense categorie(Categorie categorie) {
        this.setCategorie(categorie);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Depense)) {
            return false;
        }
        return getId() != null && getId().equals(((Depense) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "Depense{" +
            "id=" +
            getId() +
            ", code='" +
            getCode() +
            "'" +
            ", dateDepense='" +
            getDateDepense() +
            "'" +
            ", montant=" +
            getMontant() +
            ", motif='" +
            getMotif() +
            "'" +
            ", referencePiece='" +
            getReferencePiece() +
            "'" +
            ", statut='" +
            getStatut() +
            "'" +
            ", validerPar='" +
            getValiderPar() +
            "'" +
            ", dateValidation='" +
            getDateValidation() +
            "'" +
            "}"
        );
    }
}
