import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IPedidosProveedores, NewPedidosProveedores } from '../pedidos-proveedores.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPedidosProveedores for edit and NewPedidosProveedoresFormGroupInput for create.
 */
type PedidosProveedoresFormGroupInput = IPedidosProveedores | PartialWithRequiredKeyOf<NewPedidosProveedores>;

type PedidosProveedoresFormDefaults = Pick<NewPedidosProveedores, 'id'>;

type PedidosProveedoresFormGroupContent = {
  id: FormControl<IPedidosProveedores['id'] | NewPedidosProveedores['id']>;
  producto: FormControl<IPedidosProveedores['producto']>;
  cantidad: FormControl<IPedidosProveedores['cantidad']>;
  valor: FormControl<IPedidosProveedores['valor']>;
};

export type PedidosProveedoresFormGroup = FormGroup<PedidosProveedoresFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PedidosProveedoresFormService {
  createPedidosProveedoresFormGroup(pedidosProveedores: PedidosProveedoresFormGroupInput = { id: null }): PedidosProveedoresFormGroup {
    const pedidosProveedoresRawValue = {
      ...this.getFormDefaults(),
      ...pedidosProveedores,
    };
    return new FormGroup<PedidosProveedoresFormGroupContent>({
      id: new FormControl(
        { value: pedidosProveedoresRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      producto: new FormControl(pedidosProveedoresRawValue.producto),
      cantidad: new FormControl(pedidosProveedoresRawValue.cantidad),
      valor: new FormControl(pedidosProveedoresRawValue.valor),
    });
  }

  getPedidosProveedores(form: PedidosProveedoresFormGroup): IPedidosProveedores | NewPedidosProveedores {
    return form.getRawValue() as IPedidosProveedores | NewPedidosProveedores;
  }

  resetForm(form: PedidosProveedoresFormGroup, pedidosProveedores: PedidosProveedoresFormGroupInput): void {
    const pedidosProveedoresRawValue = { ...this.getFormDefaults(), ...pedidosProveedores };
    form.reset(
      {
        ...pedidosProveedoresRawValue,
        id: { value: pedidosProveedoresRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PedidosProveedoresFormDefaults {
    return {
      id: null,
    };
  }
}
