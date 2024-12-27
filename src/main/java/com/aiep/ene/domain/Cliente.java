package com.aiep.ene.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Cliente.
 */
@Entity
@Table(name = "cliente")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "mesa")
    private Integer mesa;

    @Column(name = "orden")
    private String orden;

    @Column(name = "cantidad_clientes")
    private Integer cantidadClientes;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Cliente id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMesa() {
        return this.mesa;
    }

    public Cliente mesa(Integer mesa) {
        this.setMesa(mesa);
        return this;
    }

    public void setMesa(Integer mesa) {
        this.mesa = mesa;
    }

    public String getOrden() {
        return this.orden;
    }

    public Cliente orden(String orden) {
        this.setOrden(orden);
        return this;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public Integer getCantidadClientes() {
        return this.cantidadClientes;
    }

    public Cliente cantidadClientes(Integer cantidadClientes) {
        this.setCantidadClientes(cantidadClientes);
        return this;
    }

    public void setCantidadClientes(Integer cantidadClientes) {
        this.cantidadClientes = cantidadClientes;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cliente)) {
            return false;
        }
        return getId() != null && getId().equals(((Cliente) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cliente{" +
            "id=" + getId() +
            ", mesa=" + getMesa() +
            ", orden='" + getOrden() + "'" +
            ", cantidadClientes=" + getCantidadClientes() +
            "}";
    }
}
