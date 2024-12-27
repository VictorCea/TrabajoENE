import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IPedidosProveedores } from '../pedidos-proveedores.model';

@Component({
  selector: 'jhi-pedidos-proveedores-detail',
  templateUrl: './pedidos-proveedores-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class PedidosProveedoresDetailComponent {
  pedidosProveedores = input<IPedidosProveedores | null>(null);

  previousState(): void {
    window.history.back();
  }
}
