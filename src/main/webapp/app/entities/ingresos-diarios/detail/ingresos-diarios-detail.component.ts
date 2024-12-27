import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IIngresosDiarios } from '../ingresos-diarios.model';

@Component({
  selector: 'jhi-ingresos-diarios-detail',
  templateUrl: './ingresos-diarios-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class IngresosDiariosDetailComponent {
  ingresosDiarios = input<IIngresosDiarios | null>(null);

  previousState(): void {
    window.history.back();
  }
}
