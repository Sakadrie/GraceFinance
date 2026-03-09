package com.gracefinance.gracefinanceapp.domain.referentiel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gracefinance.gracefinanceapp.domain.principal.Caisse;
import com.gracefinance.gracefinanceapp.domain.principal.EntiteFinanciere;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

/**
 * A Transfert.
 */
@Entity
@Table(name = "transfert")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Transfert implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "date_transfert", nullable = false)
    private LocalDate dateTransfert;

    @NotNull
    @Column(name = "montant", nullable = false, precision = 21, scale = 2)
    private BigDecimal montant;

    @Column(name = "motif")
    private String motif;

    @NotNull
    @Column(name = "type_transfert", nullable = false)
    private String typeTransfert;

    @NotNull
    @Column(name = "statut", nullable = false)
    private String statut;

    @Column(name = "valider_par")
    private String validerPar;

    @Column(name = "date_validation")
    private Instant dateValidation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "egliseLiees", "structureLiees" }, allowSetters = true)
    private EntiteFinanciere entiteFinanciereSource;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caisse_source_id")
    @JsonIgnoreProperties(value = { "entiteFinanciere" }, allowSetters = true)
    private Caisse caisseSource;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caisse_destination_id")
    @JsonIgnoreProperties(value = { "entiteFinanciere" }, allowSetters = true)
    private Caisse caisseDestination;

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

    public void setEntiteFinanciereSource(EntiteFinanciere e) {
        this.entiteFinanciereSource = e;
    }

    public Transfert entiteFinanciereSource(EntiteFinanciere e) {
        this.setEntiteFinanciereSource(e);
        return this;
    }

    public Caisse getCaisseSource() {
        return this.caisseSource;
    }

    public void setCaisseSource(Caisse caisse) {
        this.caisseSource = caisse;
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
    }

    public Transfert caisseDestination(Caisse caisse) {
        this.setCaisseDestination(caisse);
        return this;
    }

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
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "Transfert{" +
            "id=" +
            getId() +
            ", code='" +
            getCode() +
            "'" +
            ", dateTransfert='" +
            getDateTransfert() +
            "'" +
            ", montant=" +
            getMontant() +
            ", motif='" +
            getMotif() +
            "'" +
            ", typeTransfert='" +
            getTypeTransfert() +
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
