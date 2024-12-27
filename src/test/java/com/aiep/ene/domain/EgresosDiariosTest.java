package com.aiep.ene.domain;

import static com.aiep.ene.domain.EgresosDiariosTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.aiep.ene.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EgresosDiariosTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EgresosDiarios.class);
        EgresosDiarios egresosDiarios1 = getEgresosDiariosSample1();
        EgresosDiarios egresosDiarios2 = new EgresosDiarios();
        assertThat(egresosDiarios1).isNotEqualTo(egresosDiarios2);

        egresosDiarios2.setId(egresosDiarios1.getId());
        assertThat(egresosDiarios1).isEqualTo(egresosDiarios2);

        egresosDiarios2 = getEgresosDiariosSample2();
        assertThat(egresosDiarios1).isNotEqualTo(egresosDiarios2);
    }
}
