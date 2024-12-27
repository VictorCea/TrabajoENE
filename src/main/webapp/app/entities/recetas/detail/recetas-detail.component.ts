import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IRecetas } from '../recetas.model';

@Component({
  selector: 'jhi-recetas-detail',
  templateUrl: './recetas-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class RecetasDetailComponent {
  recetas = input<IRecetas | null>(null);

  previousState(): void {
    window.history.back();
  }
}
