package com.aiep.ene.domain;

import static com.aiep.ene.domain.IngresosDiariosTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.aiep.ene.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IngresosDiariosTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IngresosDiarios.class);
        IngresosDiarios ingresosDiarios1 = getIngresosDiariosSample1();
        IngresosDiarios ingresosDiarios2 = new IngresosDiarios();
        assertThat(ingresosDiarios1).isNotEqualTo(ingresosDiarios2);

        ingresosDiarios2.setId(ingresosDiarios1.getId());
        assertThat(ingresosDiarios1).isEqualTo(ingresosDiarios2);

        ingresosDiarios2 = getIngresosDiariosSample2();
        assertThat(ingresosDiarios1).isNotEqualTo(ingresosDiarios2);
    }
}
