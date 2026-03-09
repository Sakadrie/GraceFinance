package com.gracefinance.gracefinanceapp.domain.principal;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A CompteComptable.
 */
@Entity
@Table(name = "compte_comptable")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CompteComptable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @NotNull
    @Column(name = "classe", nullable = false)
    private Integer classe;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return this.id;
    }

    public CompteComptable id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public CompteComptable code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public CompteComptable libelle(String libelle) {
        this.setLibelle(libelle);
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Integer getClasse() {
        return this.classe;
    }

    public CompteComptable classe(Integer classe) {
        this.setClasse(classe);
        return this;
    }

    public void setClasse(Integer classe) {
        this.classe = classe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompteComptable)) {
            return false;
        }
        return getId() != null && getId().equals(((CompteComptable) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "CompteComptable{" +
            "id=" +
            getId() +
            ", code='" +
            getCode() +
            "'" +
            ", libelle='" +
            getLibelle() +
            "'" +
            ", classe=" +
            getClasse() +
            "}"
        );
    }
}
