import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IEgresosDiarios } from '../egresos-diarios.model';

@Component({
  selector: 'jhi-egresos-diarios-detail',
  templateUrl: './egresos-diarios-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class EgresosDiariosDetailComponent {
  egresosDiarios = input<IEgresosDiarios | null>(null);

  previousState(): void {
    window.history.back();
  }
}
