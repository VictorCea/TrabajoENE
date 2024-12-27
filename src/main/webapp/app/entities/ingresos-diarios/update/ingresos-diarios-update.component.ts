import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IIngresosDiarios } from '../ingresos-diarios.model';
import { IngresosDiariosService } from '../service/ingresos-diarios.service';
import { IngresosDiariosFormGroup, IngresosDiariosFormService } from './ingresos-diarios-form.service';

@Component({
  selector: 'jhi-ingresos-diarios-update',
  templateUrl: './ingresos-diarios-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class IngresosDiariosUpdateComponent implements OnInit {
  isSaving = false;
  ingresosDiarios: IIngresosDiarios | null = null;

  protected ingresosDiariosService = inject(IngresosDiariosService);
  protected ingresosDiariosFormService = inject(IngresosDiariosFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: IngresosDiariosFormGroup = this.ingresosDiariosFormService.createIngresosDiariosFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ingresosDiarios }) => {
      this.ingresosDiarios = ingresosDiarios;
      if (ingresosDiarios) {
        this.updateForm(ingresosDiarios);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ingresosDiarios = this.ingresosDiariosFormService.getIngresosDiarios(this.editForm);
    if (ingresosDiarios.id !== null) {
      this.subscribeToSaveResponse(this.ingresosDiariosService.update(ingresosDiarios));
    } else {
      this.subscribeToSaveResponse(this.ingresosDiariosService.create(ingresosDiarios));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIngresosDiarios>>): void {
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

  protected updateForm(ingresosDiarios: IIngresosDiarios): void {
    this.ingresosDiarios = ingresosDiarios;
    this.ingresosDiariosFormService.resetForm(this.editForm, ingresosDiarios);
  }
}
