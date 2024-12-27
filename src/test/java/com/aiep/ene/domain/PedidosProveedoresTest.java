package com.aiep.ene.domain;

import static com.aiep.ene.domain.PedidosProveedoresTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.aiep.ene.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PedidosProveedoresTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PedidosProveedores.class);
        PedidosProveedores pedidosProveedores1 = getPedidosProveedoresSample1();
        PedidosProveedores pedidosProveedores2 = new PedidosProveedores();
        assertThat(pedidosProveedores1).isNotEqualTo(pedidosProveedores2);

        pedidosProveedores2.setId(pedidosProveedores1.getId());
        assertThat(pedidosProveedores1).isEqualTo(pedidosProveedores2);

        pedidosProveedores2 = getPedidosProveedoresSample2();
        assertThat(pedidosProveedores1).isNotEqualTo(pedidosProveedores2);
    }
}
