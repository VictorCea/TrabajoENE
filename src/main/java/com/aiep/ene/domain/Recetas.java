package com.aiep.ene.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Recetas.
 */
@Entity
@Table(name = "recetas")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Recetas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre_plato")
    private String nombrePlato;

    @Column(name = "ingredientes_plato")
    private String ingredientesPlato;

    @Column(name = "tiempo_preparacion")
    private String tiempoPreparacion;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Recetas id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombrePlato() {
        return this.nombrePlato;
    }

    public Recetas nombrePlato(String nombrePlato) {
        this.setNombrePlato(nombrePlato);
        return this;
    }

    public void setNombrePlato(String nombrePlato) {
        this.nombrePlato = nombrePlato;
    }

    public String getIngredientesPlato() {
        return this.ingredientesPlato;
    }

    public Recetas ingredientesPlato(String ingredientesPlato) {
        this.setIngredientesPlato(ingredientesPlato);
        return this;
    }

    public void setIngredientesPlato(String ingredientesPlato) {
        this.ingredientesPlato = ingredientesPlato;
    }

    public String getTiempoPreparacion() {
        return this.tiempoPreparacion;
    }

    public Recetas tiempoPreparacion(String tiempoPreparacion) {
        this.setTiempoPreparacion(tiempoPreparacion);
        return this;
    }

    public void setTiempoPreparacion(String tiempoPreparacion) {
        this.tiempoPreparacion = tiempoPreparacion;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Recetas)) {
            return false;
        }
        return getId() != null && getId().equals(((Recetas) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Recetas{" +
            "id=" + getId() +
            ", nombrePlato='" + getNombrePlato() + "'" +
            ", ingredientesPlato='" + getIngredientesPlato() + "'" +
            ", tiempoPreparacion='" + getTiempoPreparacion() + "'" +
            "}";
    }
}
