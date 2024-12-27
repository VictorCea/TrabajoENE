package com.aiep.ene.domain;

import static com.aiep.ene.domain.RecetasTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.aiep.ene.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RecetasTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Recetas.class);
        Recetas recetas1 = getRecetasSample1();
        Recetas recetas2 = new Recetas();
        assertThat(recetas1).isNotEqualTo(recetas2);

        recetas2.setId(recetas1.getId());
        assertThat(recetas1).isEqualTo(recetas2);

        recetas2 = getRecetasSample2();
        assertThat(recetas1).isNotEqualTo(recetas2);
    }
}
