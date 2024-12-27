package com.aiep.ene.domain;

import static com.aiep.ene.domain.OrdenesCocinaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.aiep.ene.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdenesCocinaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdenesCocina.class);
        OrdenesCocina ordenesCocina1 = getOrdenesCocinaSample1();
        OrdenesCocina ordenesCocina2 = new OrdenesCocina();
        assertThat(ordenesCocina1).isNotEqualTo(ordenesCocina2);

        ordenesCocina2.setId(ordenesCocina1.getId());
        assertThat(ordenesCocina1).isEqualTo(ordenesCocina2);

        ordenesCocina2 = getOrdenesCocinaSample2();
        assertThat(ordenesCocina1).isNotEqualTo(ordenesCocina2);
    }
}
