import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IOrdenesCocina } from '../ordenes-cocina.model';

@Component({
  selector: 'jhi-ordenes-cocina-detail',
  templateUrl: './ordenes-cocina-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class OrdenesCocinaDetailComponent {
  ordenesCocina = input<IOrdenesCocina | null>(null);

  previousState(): void {
    window.history.back();
  }
}
