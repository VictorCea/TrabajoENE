package com.aiep.ene.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A EgresosDiarios.
 */
@Entity
@Table(name = "egresos_diarios")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EgresosDiarios implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "gasto_diario")
    private String gastoDiario;

    @Column(name = "valor_gasto")
    private Integer valorGasto;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EgresosDiarios id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGastoDiario() {
        return this.gastoDiario;
    }

    public EgresosDiarios gastoDiario(String gastoDiario) {
        this.setGastoDiario(gastoDiario);
        return this;
    }

    public void setGastoDiario(String gastoDiario) {
        this.gastoDiario = gastoDiario;
    }

    public Integer getValorGasto() {
        return this.valorGasto;
    }

    public EgresosDiarios valorGasto(Integer valorGasto) {
        this.setValorGasto(valorGasto);
        return this;
    }

    public void setValorGasto(Integer valorGasto) {
        this.valorGasto = valorGasto;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EgresosDiarios)) {
            return false;
        }
        return getId() != null && getId().equals(((EgresosDiarios) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EgresosDiarios{" +
            "id=" + getId() +
            ", gastoDiario='" + getGastoDiario() + "'" +
            ", valorGasto=" + getValorGasto() +
            "}";
    }
}
