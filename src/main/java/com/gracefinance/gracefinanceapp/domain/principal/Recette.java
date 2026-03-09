package com.gracefinance.gracefinanceapp.domain.principal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gracefinance.gracefinanceapp.domain.referentiel.Categorie;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A Recette.
 */
@Entity
@Table(name = "recette")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Recette implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "date_recette", nullable = false)
    private LocalDate dateRecette;

    @NotNull
    @Column(name = "montant", nullable = false, precision = 21, scale = 2)
    private BigDecimal montant;

    @NotNull
    @Column(name = "type_recette", nullable = false)
    private String typeRecette;

    @NotNull
    @Column(name = "anonyme", nullable = false)
    private Boolean anonyme;

    @Column(name = "membre_nom")
    private String membreNom;

    @Column(name = "motif")
    private String motif;

    @Column(name = "reference_piece")
    private String referencePiece;

    @NotNull
    @Column(name = "statut", nullable = false)
    private String statut;

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

    public Recette id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Recette code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getDateRecette() {
        return this.dateRecette;
    }

    public Recette dateRecette(LocalDate dateRecette) {
        this.setDateRecette(dateRecette);
        return this;
    }

    public void setDateRecette(LocalDate dateRecette) {
        this.dateRecette = dateRecette;
    }

    public BigDecimal getMontant() {
        return this.montant;
    }

    public Recette montant(BigDecimal montant) {
        this.setMontant(montant);
        return this;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant != null ? montant.stripTrailingZeros() : null;
    }

    public String getTypeRecette() {
        return this.typeRecette;
    }

    public Recette typeRecette(String typeRecette) {
        this.setTypeRecette(typeRecette);
        return this;
    }

    public void setTypeRecette(String typeRecette) {
        this.typeRecette = typeRecette;
    }

    public Boolean getAnonyme() {
        return this.anonyme;
    }

    public Recette anonyme(Boolean anonyme) {
        this.setAnonyme(anonyme);
        return this;
    }

    public void setAnonyme(Boolean anonyme) {
        this.anonyme = anonyme;
    }

    public String getMembreNom() {
        return this.membreNom;
    }

    public Recette membreNom(String membreNom) {
        this.setMembreNom(membreNom);
        return this;
    }

    public void setMembreNom(String membreNom) {
        this.membreNom = membreNom;
    }

    public String getMotif() {
        return this.motif;
    }

    public Recette motif(String motif) {
        this.setMotif(motif);
        return this;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getReferencePiece() {
        return this.referencePiece;
    }

    public Recette referencePiece(String referencePiece) {
        this.setReferencePiece(referencePiece);
        return this;
    }

    public void setReferencePiece(String referencePiece) {
        this.referencePiece = referencePiece;
    }

    public String getStatut() {
        return this.statut;
    }

    public Recette statut(String statut) {
        this.setStatut(statut);
        return this;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public EntiteFinanciere getEntiteFinanciere() {
        return this.entiteFinanciere;
    }

    public void setEntiteFinanciere(EntiteFinanciere entiteFinanciere) {
        this.entiteFinanciere = entiteFinanciere;
    }

    public Recette entiteFinanciere(EntiteFinanciere e) {
        this.setEntiteFinanciere(e);
        return this;
    }

    public Caisse getCaisse() {
        return this.caisse;
    }

    public void setCaisse(Caisse caisse) {
        this.caisse = caisse;
    }

    public Recette caisse(Caisse caisse) {
        this.setCaisse(caisse);
        return this;
    }

    public Categorie getCategorie() {
        return this.categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Recette categorie(Categorie categorie) {
        this.setCategorie(categorie);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Recette)) {
            return false;
        }
        return getId() != null && getId().equals(((Recette) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "Recette{" +
            "id=" +
            getId() +
            ", code='" +
            getCode() +
            "'" +
            ", dateRecette='" +
            getDateRecette() +
            "'" +
            ", montant=" +
            getMontant() +
            ", typeRecette='" +
            getTypeRecette() +
            "'" +
            ", anonyme='" +
            getAnonyme() +
            "'" +
            ", membreNom='" +
            getMembreNom() +
            "'" +
            ", motif='" +
            getMotif() +
            "'" +
            ", referencePiece='" +
            getReferencePiece() +
            "'" +
            ", statut='" +
            getStatut() +
            "'" +
            "}"
        );
    }
}
