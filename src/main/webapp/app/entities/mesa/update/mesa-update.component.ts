import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IMesa } from '../mesa.model';
import { MesaService } from '../service/mesa.service';
import { MesaFormGroup, MesaFormService } from './mesa-form.service';

@Component({
  selector: 'jhi-mesa-update',
  templateUrl: './mesa-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class MesaUpdateComponent implements OnInit {
  isSaving = false;
  mesa: IMesa | null = null;

  protected mesaService = inject(MesaService);
  protected mesaFormService = inject(MesaFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: MesaFormGroup = this.mesaFormService.createMesaFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mesa }) => {
      this.mesa = mesa;
      if (mesa) {
        this.updateForm(mesa);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const mesa = this.mesaFormService.getMesa(this.editForm);
    if (mesa.id !== null) {
      this.subscribeToSaveResponse(this.mesaService.update(mesa));
    } else {
      this.subscribeToSaveResponse(this.mesaService.create(mesa));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMesa>>): void {
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

  protected updateForm(mesa: IMesa): void {
    this.mesa = mesa;
    this.mesaFormService.resetForm(this.editForm, mesa);
  }
}
