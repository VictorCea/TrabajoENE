import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IOrdenesCocina } from '../ordenes-cocina.model';
import { OrdenesCocinaService } from '../service/ordenes-cocina.service';

@Component({
  templateUrl: './ordenes-cocina-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class OrdenesCocinaDeleteDialogComponent {
  ordenesCocina?: IOrdenesCocina;

  protected ordenesCocinaService = inject(OrdenesCocinaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ordenesCocinaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
