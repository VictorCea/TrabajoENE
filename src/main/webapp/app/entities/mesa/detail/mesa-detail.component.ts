import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IMesa } from '../mesa.model';

@Component({
  selector: 'jhi-mesa-detail',
  templateUrl: './mesa-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class MesaDetailComponent {
  mesa = input<IMesa | null>(null);

  previousState(): void {
    window.history.back();
  }
}
