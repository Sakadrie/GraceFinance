package com.gracefinance.gracefinanceapp.domain.principal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gracefinance.gracefinanceapp.domain.referentiel.Categorie;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Depense.
 */
@Table("depense")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Depense implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @NotNull(message = "must not be null")
    @Column("code")
    private String code;

    @NotNull(message = "must not be null")
    @Column("date_depense")
    private LocalDate dateDepense;

    @NotNull(message = "must not be null")
    @Column("montant")
    private BigDecimal montant;

    @NotNull(message = "must not be null")
    @Column("motif")
    private String motif;

    @Column("reference_piece")
    private String referencePiece;

    @NotNull(message = "must not be null")
    @Column("statut")
    private String statut;

    @Column("valider_par")
    private String validerPar;

    @Column("date_validation")
    private Instant dateValidation;

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
        this.entiteFinanciereId = entiteFinanciere != null ? entiteFinanciere.getId() : null;
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
        this.caisseId = caisse != null ? caisse.getId() : null;
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
        this.categorieId = categorie != null ? categorie.getId() : null;
    }

    public Depense categorie(Categorie categorie) {
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
        if (!(o instanceof Depense)) {
            return false;
        }
        return getId() != null && getId().equals(((Depense) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Depense{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", dateDepense='" + getDateDepense() + "'" +
            ", montant=" + getMontant() +
            ", motif='" + getMotif() + "'" +
            ", referencePiece='" + getReferencePiece() + "'" +
            ", statut='" + getStatut() + "'" +
            ", validerPar='" + getValiderPar() + "'" +
            ", dateValidation='" + getDateValidation() + "'" +
            "}";
    }
}
