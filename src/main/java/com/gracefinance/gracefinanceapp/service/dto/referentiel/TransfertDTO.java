package com.gracefinance.gracefinanceapp.service.dto.referentiel;

import com.gracefinance.gracefinanceapp.service.dto.principal.CaisseDTO;
import com.gracefinance.gracefinanceapp.service.dto.principal.EntiteFinanciereDTO;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.gracefinance.gracefinanceapp.domain.referentiel.Transfert} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TransfertDTO implements Serializable {

    private Long id;

    @NotNull(message = "must not be null")
    private String code;

    @NotNull(message = "must not be null")
    private LocalDate dateTransfert;

    @NotNull(message = "must not be null")
    private BigDecimal montant;

    private String motif;

    @NotNull(message = "must not be null")
    private String typeTransfert;

    @NotNull(message = "must not be null")
    private String statut;

    private String validerPar;

    private Instant dateValidation;

    @NotNull
    private EntiteFinanciereDTO entiteFinanciereSource;

    @NotNull
    private CaisseDTO caisseSource;

    @NotNull
    private CaisseDTO caisseDestination;

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

    public LocalDate getDateTransfert() {
        return dateTransfert;
    }

    public void setDateTransfert(LocalDate dateTransfert) {
        this.dateTransfert = dateTransfert;
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

    public String getTypeTransfert() {
        return typeTransfert;
    }

    public void setTypeTransfert(String typeTransfert) {
        this.typeTransfert = typeTransfert;
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

    public EntiteFinanciereDTO getEntiteFinanciereSource() {
        return entiteFinanciereSource;
    }

    public void setEntiteFinanciereSource(EntiteFinanciereDTO entiteFinanciereSource) {
        this.entiteFinanciereSource = entiteFinanciereSource;
    }

    public CaisseDTO getCaisseSource() {
        return caisseSource;
    }

    public void setCaisseSource(CaisseDTO caisseSource) {
        this.caisseSource = caisseSource;
    }

    public CaisseDTO getCaisseDestination() {
        return caisseDestination;
    }

    public void setCaisseDestination(CaisseDTO caisseDestination) {
        this.caisseDestination = caisseDestination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransfertDTO)) {
            return false;
        }

        TransfertDTO transfertDTO = (TransfertDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, transfertDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransfertDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", dateTransfert='" + getDateTransfert() + "'" +
            ", montant=" + getMontant() +
            ", motif='" + getMotif() + "'" +
            ", typeTransfert='" + getTypeTransfert() + "'" +
            ", statut='" + getStatut() + "'" +
            ", validerPar='" + getValiderPar() + "'" +
            ", dateValidation='" + getDateValidation() + "'" +
            ", entiteFinanciereSource=" + getEntiteFinanciereSource() +
            ", caisseSource=" + getCaisseSource() +
            ", caisseDestination=" + getCaisseDestination() +
            "}";
    }
}
