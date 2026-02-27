package com.gracefinance.gracefinanceapp.domain.principal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gracefinance.gracefinanceapp.domain.referentiel.Categorie;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Recette.
 */
@Table("recette")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Recette implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @NotNull(message = "must not be null")
    @Column("code")
    private String code;

    @NotNull(message = "must not be null")
    @Column("date_recette")
    private LocalDate dateRecette;

    @NotNull(message = "must not be null")
    @Column("montant")
    private BigDecimal montant;

    @NotNull(message = "must not be null")
    @Column("type_recette")
    private String typeRecette;

    @NotNull(message = "must not be null")
    @Column("anonyme")
    private Boolean anonyme;

    @Column("membre_nom")
    private String membreNom;

    @Column("motif")
    private String motif;

    @Column("reference_piece")
    private String referencePiece;

    @NotNull(message = "must not be null")
    @Column("statut")
    private String statut;

    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "egliseLiees", "structureLiees" }, allowSetters = true)
    private EntiteFinanciere entiteFinanciere;

    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "entiteFinanciere" }, allowSetters = true)
    private Caisse caisse;

    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "entiteFinanciere" }, allowSetters = true)
    private Categorie categorie;

    @Column("entite_financiere_id")
    private Long entiteFinanciereId;

    @Column("caisse_id")
    private Long caisseId;

    @Column("categorie_id")
    private Long categorieId;

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
        this.entiteFinanciereId = entiteFinanciere != null ? entiteFinanciere.getId() : null;
    }

    public Recette entiteFinanciere(EntiteFinanciere entiteFinanciere) {
        this.setEntiteFinanciere(entiteFinanciere);
        return this;
    }

    public Caisse getCaisse() {
        return this.caisse;
    }

    public void setCaisse(Caisse caisse) {
        this.caisse = caisse;
        this.caisseId = caisse != null ? caisse.getId() : null;
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
        this.categorieId = categorie != null ? categorie.getId() : null;
    }

    public Recette categorie(Categorie categorie) {
        this.setCategorie(categorie);
        return this;
    }

    public Long getEntiteFinanciereId() {
        return this.entiteFinanciereId;
    }

    public void setEntiteFinanciereId(Long entiteFinanciere) {
        this.entiteFinanciereId = entiteFinanciere;
    }

    public Long getCaisseId() {
        return this.caisseId;
    }

    public void setCaisseId(Long caisse) {
        this.caisseId = caisse;
    }

    public Long getCategorieId() {
        return this.categorieId;
    }

    public void setCategorieId(Long categorie) {
        this.categorieId = categorie;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

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
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Recette{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", dateRecette='" + getDateRecette() + "'" +
            ", montant=" + getMontant() +
            ", typeRecette='" + getTypeRecette() + "'" +
            ", anonyme='" + getAnonyme() + "'" +
            ", membreNom='" + getMembreNom() + "'" +
            ", motif='" + getMotif() + "'" +
            ", referencePiece='" + getReferencePiece() + "'" +
            ", statut='" + getStatut() + "'" +
            "}";
    }
}
