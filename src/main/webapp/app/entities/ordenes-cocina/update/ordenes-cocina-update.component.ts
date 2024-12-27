import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IOrdenesCocina } from '../ordenes-cocina.model';
import { OrdenesCocinaService } from '../service/ordenes-cocina.service';
import { OrdenesCocinaFormGroup, OrdenesCocinaFormService } from './ordenes-cocina-form.service';

@Component({
  selector: 'jhi-ordenes-cocina-update',
  templateUrl: './ordenes-cocina-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class OrdenesCocinaUpdateComponent implements OnInit {
  isSaving = false;
  ordenesCocina: IOrdenesCocina | null = null;

  protected ordenesCocinaService = inject(OrdenesCocinaService);
  protected ordenesCocinaFormService = inject(OrdenesCocinaFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: OrdenesCocinaFormGroup = this.ordenesCocinaFormService.createOrdenesCocinaFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ordenesCocina }) => {
      this.ordenesCocina = ordenesCocina;
      if (ordenesCocina) {
        this.updateForm(ordenesCocina);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ordenesCocina = this.ordenesCocinaFormService.getOrdenesCocina(this.editForm);
    if (ordenesCocina.id !== null) {
      this.subscribeToSaveResponse(this.ordenesCocinaService.update(ordenesCocina));
    } else {
      this.subscribeToSaveResponse(this.ordenesCocinaService.create(ordenesCocina));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrdenesCocina>>): void {
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

  protected updateForm(ordenesCocina: IOrdenesCocina): void {
    this.ordenesCocina = ordenesCocina;
    this.ordenesCocinaFormService.resetForm(this.editForm, ordenesCocina);
  }
}
