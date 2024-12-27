package com.aiep.ene.domain;

import static com.aiep.ene.domain.MesaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.aiep.ene.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MesaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mesa.class);
        Mesa mesa1 = getMesaSample1();
        Mesa mesa2 = new Mesa();
        assertThat(mesa1).isNotEqualTo(mesa2);

        mesa2.setId(mesa1.getId());
        assertThat(mesa1).isEqualTo(mesa2);

        mesa2 = getMesaSample2();
        assertThat(mesa1).isNotEqualTo(mesa2);
    }
}
