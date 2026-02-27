package com.gracefinance.gracefinanceapp.service.dto.principal;

import com.gracefinance.gracefinanceapp.service.dto.referentiel.CategorieDTO;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.gracefinance.gracefinanceapp.domain.principal.Recette} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RecetteDTO implements Serializable {

    private Long id;

    @NotNull(message = "must not be null")
    private String code;

    @NotNull(message = "must not be null")
    private LocalDate dateRecette;

    @NotNull(message = "must not be null")
    private BigDecimal montant;

    @NotNull(message = "must not be null")
    private String typeRecette;

    @NotNull(message = "must not be null")
    private Boolean anonyme;

    private String membreNom;

    private String motif;

    private String referencePiece;

    @NotNull(message = "must not be null")
    private String statut;

    @NotNull
    private EntiteFinanciereDTO entiteFinanciere;

    @NotNull
    private CaisseDTO caisse;

    @NotNull
    private CategorieDTO categorie;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getDateRecette() {
        return dateRecette;
    }

    public void setDateRecette(LocalDate dateRecette) {
        this.dateRecette = dateRecette;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public String getTypeRecette() {
        return typeRecette;
    }

    public void setTypeRecette(String typeRecette) {
        this.typeRecette = typeRecette;
    }

    public Boolean getAnonyme() {
        return anonyme;
    }

    public void setAnonyme(Boolean anonyme) {
        this.anonyme = anonyme;
    }

    public String getMembreNom() {
        return membreNom;
    }

    public void setMembreNom(String membreNom) {
        this.membreNom = membreNom;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getReferencePiece() {
        return referencePiece;
    }

    public void setReferencePiece(String referencePiece) {
        this.referencePiece = referencePiece;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public EntiteFinanciereDTO getEntiteFinanciere() {
        return entiteFinanciere;
    }

    public void setEntiteFinanciere(EntiteFinanciereDTO entiteFinanciere) {
        this.entiteFinanciere = entiteFinanciere;
    }

    public CaisseDTO getCaisse() {
        return caisse;
    }

    public void setCaisse(CaisseDTO caisse) {
        this.caisse = caisse;
    }

    public CategorieDTO getCategorie() {
        return categorie;
    }

    public void setCategorie(CategorieDTO categorie) {
        this.categorie = categorie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RecetteDTO)) {
            return false;
        }

        RecetteDTO recetteDTO = (RecetteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, recetteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RecetteDTO{" +
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
            ", entiteFinanciere=" + getEntiteFinanciere() +
            ", caisse=" + getCaisse() +
            ", categorie=" + getCategorie() +
            "}";
    }
}
