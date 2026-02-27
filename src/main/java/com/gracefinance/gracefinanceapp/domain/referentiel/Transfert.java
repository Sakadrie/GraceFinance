package com.gracefinance.gracefinanceapp.domain.referentiel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gracefinance.gracefinanceapp.domain.principal.Caisse;
import com.gracefinance.gracefinanceapp.domain.principal.EntiteFinanciere;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Transfert.
 */
@Table("transfert")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Transfert implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @NotNull(message = "must not be null")
    @Column("code")
    private String code;

    @NotNull(message = "must not be null")
    @Column("date_transfert")
    private LocalDate dateTransfert;

    @NotNull(message = "must not be null")
    @Column("montant")
    private BigDecimal montant;

    @Column("motif")
    private String motif;

    @NotNull(message = "must not be null")
    @Column("type_transfert")
    private String typeTransfert;

    @NotNull(message = "must not be null")
    @Column("statut")
    private String statut;

    @Column("valider_par")
    private String validerPar;

    @Column("date_validation")
    private Instant dateValidation;

    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "egliseLiees", "structureLiees" }, allowSetters = true)
    private EntiteFinanciere entiteFinanciereSource;

    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "entiteFinanciere" }, allowSetters = true)
    private Caisse caisseSource;

    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "entiteFinanciere" }, allowSetters = true)
    private Caisse caisseDestination;

    @Column("entite_financiere_source_id")
    private Long entiteFinanciereSourceId;

    @Column("caisse_source_id")
    private Long caisseSourceId;

    @Column("caisse_destination_id")
    private Long caisseDestinationId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Transfert id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Transfert code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getDateTransfert() {
        return this.dateTransfert;
    }

    public Transfert dateTransfert(LocalDate dateTransfert) {
        this.setDateTransfert(dateTransfert);
        return this;
    }

    public void setDateTransfert(LocalDate dateTransfert) {
        this.dateTransfert = dateTransfert;
    }

    public BigDecimal getMontant() {
        return this.montant;
    }

    public Transfert montant(BigDecimal montant) {
        this.setMontant(montant);
        return this;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant != null ? montant.stripTrailingZeros() : null;
    }

    public String getMotif() {
        return this.motif;
    }

    public Transfert motif(String motif) {
        this.setMotif(motif);
        return this;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getTypeTransfert() {
        return this.typeTransfert;
    }

    public Transfert typeTransfert(String typeTransfert) {
        this.setTypeTransfert(typeTransfert);
        return this;
    }

    public void setTypeTransfert(String typeTransfert) {
        this.typeTransfert = typeTransfert;
    }

    public String getStatut() {
        return this.statut;
    }

    public Transfert statut(String statut) {
        this.setStatut(statut);
        return this;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getValiderPar() {
        return this.validerPar;
    }

    public Transfert validerPar(String validerPar) {
        this.setValiderPar(validerPar);
        return this;
    }

    public void setValiderPar(String validerPar) {
        this.validerPar = validerPar;
    }

    public Instant getDateValidation() {
        return this.dateValidation;
    }

    public Transfert dateValidation(Instant dateValidation) {
        this.setDateValidation(dateValidation);
        return this;
    }

    public void setDateValidation(Instant dateValidation) {
        this.dateValidation = dateValidation;
    }

    public EntiteFinanciere getEntiteFinanciereSource() {
        return this.entiteFinanciereSource;
    }

    public void setEntiteFinanciereSource(EntiteFinanciere entiteFinanciere) {
        this.entiteFinanciereSource = entiteFinanciere;
        this.entiteFinanciereSourceId = entiteFinanciere != null ? entiteFinanciere.getId() : null;
    }

    public Transfert entiteFinanciereSource(EntiteFinanciere entiteFinanciere) {
        this.setEntiteFinanciereSource(entiteFinanciere);
        return this;
    }

    public Caisse getCaisseSource() {
        return this.caisseSource;
    }

    public void setCaisseSource(Caisse caisse) {
        this.caisseSource = caisse;
        this.caisseSourceId = caisse != null ? caisse.getId() : null;
    }

    public Transfert caisseSource(Caisse caisse) {
        this.setCaisseSource(caisse);
        return this;
    }

    public Caisse getCaisseDestination() {
        return this.caisseDestination;
    }

    public void setCaisseDestination(Caisse caisse) {
        this.caisseDestination = caisse;
        this.caisseDestinationId = caisse != null ? caisse.getId() : null;
    }

    public Transfert caisseDestination(Caisse caisse) {
        this.setCaisseDestination(caisse);
        return this;
    }

    public Long getEntiteFinanciereSourceId() {
        return this.entiteFinanciereSourceId;
    }

    public void setEntiteFinanciereSourceId(Long entiteFinanciere) {
        this.entiteFinanciereSourceId = entiteFinanciere;
    }

    public Long getCaisseSourceId() {
        return this.caisseSourceId;
    }

    public void setCaisseSourceId(Long caisse) {
        this.caisseSourceId = caisse;
    }

    public Long getCaisseDestinationId() {
        return this.caisseDestinationId;
    }

    public void setCaisseDestinationId(Long caisse) {
        this.caisseDestinationId = caisse;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transfert)) {
            return false;
        }
        return getId() != null && getId().equals(((Transfert) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Transfert{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", dateTransfert='" + getDateTransfert() + "'" +
            ", montant=" + getMontant() +
            ", motif='" + getMotif() + "'" +
            ", typeTransfert='" + getTypeTransfert() + "'" +
            ", statut='" + getStatut() + "'" +
            ", validerPar='" + getValiderPar() + "'" +
            ", dateValidation='" + getDateValidation() + "'" +
            "}";
    }
}
