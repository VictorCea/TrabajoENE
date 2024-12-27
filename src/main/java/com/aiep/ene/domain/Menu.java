package com.aiep.ene.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Menu.
 */
@Entity
@Table(name = "menu")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre_plato")
    private String nombrePlato;

    @Column(name = "valor_plato")
    private Integer valorPlato;

    @Column(name = "descripcion_plato")
    private String descripcionPlato;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Menu id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombrePlato() {
        return this.nombrePlato;
    }

    public Menu nombrePlato(String nombrePlato) {
        this.setNombrePlato(nombrePlato);
        return this;
    }

    public void setNombrePlato(String nombrePlato) {
        this.nombrePlato = nombrePlato;
    }

    public Integer getValorPlato() {
        return this.valorPlato;
    }

    public Menu valorPlato(Integer valorPlato) {
        this.setValorPlato(valorPlato);
        return this;
    }

    public void setValorPlato(Integer valorPlato) {
        this.valorPlato = valorPlato;
    }

    public String getDescripcionPlato() {
        return this.descripcionPlato;
    }

    public Menu descripcionPlato(String descripcionPlato) {
        this.setDescripcionPlato(descripcionPlato);
        return this;
    }

    public void setDescripcionPlato(String descripcionPlato) {
        this.descripcionPlato = descripcionPlato;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Menu)) {
            return false;
        }
        return getId() != null && getId().equals(((Menu) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Menu{" +
            "id=" + getId() +
            ", nombrePlato='" + getNombrePlato() + "'" +
            ", valorPlato=" + getValorPlato() +
            ", descripcionPlato='" + getDescripcionPlato() + "'" +
            "}";
    }
}
