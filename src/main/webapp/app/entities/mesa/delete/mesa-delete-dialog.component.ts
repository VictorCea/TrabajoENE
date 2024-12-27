import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IMesa } from '../mesa.model';
import { MesaService } from '../service/mesa.service';

@Component({
  templateUrl: './mesa-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class MesaDeleteDialogComponent {
  mesa?: IMesa;

  protected mesaService = inject(MesaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.mesaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
