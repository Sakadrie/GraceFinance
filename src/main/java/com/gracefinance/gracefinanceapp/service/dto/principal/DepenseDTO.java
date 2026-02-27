package com.gracefinance.gracefinanceapp.service.dto.principal;

import com.gracefinance.gracefinanceapp.service.dto.referentiel.CategorieDTO;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.gracefinance.gracefinanceapp.domain.principal.Depense} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DepenseDTO implements Serializable {

    private Long id;

    @NotNull(message = "must not be null")
    private String code;

    @NotNull(message = "must not be null")
    private LocalDate dateDepense;

    @NotNull(message = "must not be null")
    private BigDecimal montant;

    @NotNull(message = "must not be null")
    private String motif;

    private String referencePiece;

    @NotNull(message = "must not be null")
    private String statut;

    private String validerPar;

    private Instant dateValidation;

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

    public LocalDate getDateDepense() {
        return dateDepense;
    }

    public void setDateDepense(LocalDate dateDepense) {
        this.dateDepense = dateDepense;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
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

    public String getValiderPar() {
        return validerPar;
    }

    public void setValiderPar(String validerPar) {
        this.validerPar = validerPar;
    }

    public Instant getDateValidation() {
        return dateValidation;
    }

    public void setDateValidation(Instant dateValidation) {
        this.dateValidation = dateValidation;
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
        if (!(o instanceof DepenseDTO)) {
            return false;
        }

        DepenseDTO depenseDTO = (DepenseDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, depenseDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DepenseDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", dateDepense='" + getDateDepense() + "'" +
            ", montant=" + getMontant() +
            ", motif='" + getMotif() + "'" +
            ", referencePiece='" + getReferencePiece() + "'" +
            ", statut='" + getStatut() + "'" +
            ", validerPar='" + getValiderPar() + "'" +
            ", dateValidation='" + getDateValidation() + "'" +
            ", entiteFinanciere=" + getEntiteFinanciere() +
            ", caisse=" + getCaisse() +
            ", categorie=" + getCategorie() +
            "}";
    }
}
