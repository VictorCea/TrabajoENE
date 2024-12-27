import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IPedidosProveedores } from '../pedidos-proveedores.model';
import { PedidosProveedoresService } from '../service/pedidos-proveedores.service';

@Component({
  templateUrl: './pedidos-proveedores-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class PedidosProveedoresDeleteDialogComponent {
  pedidosProveedores?: IPedidosProveedores;

  protected pedidosProveedoresService = inject(PedidosProveedoresService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pedidosProveedoresService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
