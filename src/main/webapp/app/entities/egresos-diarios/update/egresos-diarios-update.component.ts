import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEgresosDiarios } from '../egresos-diarios.model';
import { EgresosDiariosService } from '../service/egresos-diarios.service';
import { EgresosDiariosFormGroup, EgresosDiariosFormService } from './egresos-diarios-form.service';

@Component({
  selector: 'jhi-egresos-diarios-update',
  templateUrl: './egresos-diarios-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class EgresosDiariosUpdateComponent implements OnInit {
  isSaving = false;
  egresosDiarios: IEgresosDiarios | null = null;

  protected egresosDiariosService = inject(EgresosDiariosService);
  protected egresosDiariosFormService = inject(EgresosDiariosFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: EgresosDiariosFormGroup = this.egresosDiariosFormService.createEgresosDiariosFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ egresosDiarios }) => {
      this.egresosDiarios = egresosDiarios;
      if (egresosDiarios) {
        this.updateForm(egresosDiarios);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const egresosDiarios = this.egresosDiariosFormService.getEgresosDiarios(this.editForm);
    if (egresosDiarios.id !== null) {
      this.subscribeToSaveResponse(this.egresosDiariosService.update(egresosDiarios));
    } else {
      this.subscribeToSaveResponse(this.egresosDiariosService.create(egresosDiarios));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEgresosDiarios>>): void {
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

  protected updateForm(egresosDiarios: IEgresosDiarios): void {
    this.egresosDiarios = egresosDiarios;
    this.egresosDiariosFormService.resetForm(this.editForm, egresosDiarios);
  }
}
