package com.aiep.ene.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A IngresosDiarios.
 */
@Entity
@Table(name = "ingresos_diarios")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IngresosDiarios implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "venta_numero")
    private Integer ventaNumero;

    @Column(name = "valor_venta")
    private Integer valorVenta;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public IngresosDiarios id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVentaNumero() {
        return this.ventaNumero;
    }

    public IngresosDiarios ventaNumero(Integer ventaNumero) {
        this.setVentaNumero(ventaNumero);
        return this;
    }

    public void setVentaNumero(Integer ventaNumero) {
        this.ventaNumero = ventaNumero;
    }

    public Integer getValorVenta() {
        return this.valorVenta;
    }

    public IngresosDiarios valorVenta(Integer valorVenta) {
        this.setValorVenta(valorVenta);
        return this;
    }

    public void setValorVenta(Integer valorVenta) {
        this.valorVenta = valorVenta;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IngresosDiarios)) {
            return false;
        }
        return getId() != null && getId().equals(((IngresosDiarios) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IngresosDiarios{" +
            "id=" + getId() +
            ", ventaNumero=" + getVentaNumero() +
            ", valorVenta=" + getValorVenta() +
            "}";
    }
}
