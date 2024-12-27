import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPedidosProveedores } from '../pedidos-proveedores.model';
import { PedidosProveedoresService } from '../service/pedidos-proveedores.service';
import { PedidosProveedoresFormGroup, PedidosProveedoresFormService } from './pedidos-proveedores-form.service';

@Component({
  selector: 'jhi-pedidos-proveedores-update',
  templateUrl: './pedidos-proveedores-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PedidosProveedoresUpdateComponent implements OnInit {
  isSaving = false;
  pedidosProveedores: IPedidosProveedores | null = null;

  protected pedidosProveedoresService = inject(PedidosProveedoresService);
  protected pedidosProveedoresFormService = inject(PedidosProveedoresFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PedidosProveedoresFormGroup = this.pedidosProveedoresFormService.createPedidosProveedoresFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pedidosProveedores }) => {
      this.pedidosProveedores = pedidosProveedores;
      if (pedidosProveedores) {
        this.updateForm(pedidosProveedores);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pedidosProveedores = this.pedidosProveedoresFormService.getPedidosProveedores(this.editForm);
    if (pedidosProveedores.id !== null) {
      this.subscribeToSaveResponse(this.pedidosProveedoresService.update(pedidosProveedores));
    } else {
      this.subscribeToSaveResponse(this.pedidosProveedoresService.create(pedidosProveedores));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPedidosProveedores>>): void {
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

  protected updateForm(pedidosProveedores: IPedidosProveedores): void {
    this.pedidosProveedores = pedidosProveedores;
    this.pedidosProveedoresFormService.resetForm(this.editForm, pedidosProveedores);
  }
}
