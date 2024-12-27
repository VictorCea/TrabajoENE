package com.aiep.ene.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A OrdenesCocina.
 */
@Entity
@Table(name = "ordenes_cocina")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrdenesCocina implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre_plato")
    private String nombrePlato;

    @Column(name = "cantidad_plato")
    private Integer cantidadPlato;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OrdenesCocina id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombrePlato() {
        return this.nombrePlato;
    }

    public OrdenesCocina nombrePlato(String nombrePlato) {
        this.setNombrePlato(nombrePlato);
        return this;
    }

    public void setNombrePlato(String nombrePlato) {
        this.nombrePlato = nombrePlato;
    }

    public Integer getCantidadPlato() {
        return this.cantidadPlato;
    }

    public OrdenesCocina cantidadPlato(Integer cantidadPlato) {
        this.setCantidadPlato(cantidadPlato);
        return this;
    }

    public void setCantidadPlato(Integer cantidadPlato) {
        this.cantidadPlato = cantidadPlato;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrdenesCocina)) {
            return false;
        }
        return getId() != null && getId().equals(((OrdenesCocina) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdenesCocina{" +
            "id=" + getId() +
            ", nombrePlato='" + getNombrePlato() + "'" +
            ", cantidadPlato=" + getCantidadPlato() +
            "}";
    }
}
