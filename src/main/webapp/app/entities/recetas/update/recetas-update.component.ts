import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IRecetas } from '../recetas.model';
import { RecetasService } from '../service/recetas.service';
import { RecetasFormGroup, RecetasFormService } from './recetas-form.service';

@Component({
  selector: 'jhi-recetas-update',
  templateUrl: './recetas-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class RecetasUpdateComponent implements OnInit {
  isSaving = false;
  recetas: IRecetas | null = null;

  protected recetasService = inject(RecetasService);
  protected recetasFormService = inject(RecetasFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: RecetasFormGroup = this.recetasFormService.createRecetasFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ recetas }) => {
      this.recetas = recetas;
      if (recetas) {
        this.updateForm(recetas);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const recetas = this.recetasFormService.getRecetas(this.editForm);
    if (recetas.id !== null) {
      this.subscribeToSaveResponse(this.recetasService.update(recetas));
    } else {
      this.subscribeToSaveResponse(this.recetasService.create(recetas));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRecetas>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(recetas: IRecetas): void {
    this.recetas = recetas;
    this.recetasFormService.resetForm(this.editForm, recetas);
  }
}
