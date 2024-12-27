import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IIngresosDiarios } from '../ingresos-diarios.model';
import { IngresosDiariosService } from '../service/ingresos-diarios.service';

@Component({
  templateUrl: './ingresos-diarios-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class IngresosDiariosDeleteDialogComponent {
  ingresosDiarios?: IIngresosDiarios;

  protected ingresosDiariosService = inject(IngresosDiariosService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ingresosDiariosService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
