import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IProveedor, NewProveedor } from '../proveedor.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProveedor for edit and NewProveedorFormGroupInput for create.
 */
type ProveedorFormGroupInput = IProveedor | PartialWithRequiredKeyOf<NewProveedor>;

type ProveedorFormDefaults = Pick<NewProveedor, 'id'>;

type ProveedorFormGroupContent = {
  id: FormControl<IProveedor['id'] | NewProveedor['id']>;
  nombre: FormControl<IProveedor['nombre']>;
  direccion: FormControl<IProveedor['direccion']>;
  email: FormControl<IProveedor['email']>;
  fono: FormControl<IProveedor['fono']>;
};

export type ProveedorFormGroup = FormGroup<ProveedorFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProveedorFormService {
  createProveedorFormGroup(proveedor: ProveedorFormGroupInput = { id: null }): ProveedorFormGroup {
    const proveedorRawValue = {
      ...this.getFormDefaults(),
      ...proveedor,
    };
    return new FormGroup<ProveedorFormGroupContent>({
      id: new FormControl(
        { value: proveedorRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nombre: new FormControl(proveedorRawValue.nombre),
      direccion: new FormControl(proveedorRawValue.direccion),
      email: new FormControl(proveedorRawValue.email),
      fono: new FormControl(proveedorRawValue.fono),
    });
  }

  getProveedor(form: ProveedorFormGroup): IProveedor | NewProveedor {
    return form.getRawValue() as IProveedor | NewProveedor;
  }

  resetForm(form: ProveedorFormGroup, proveedor: ProveedorFormGroupInput): void {
    const proveedorRawValue = { ...this.getFormDefaults(), ...proveedor };
    form.reset(
      {
        ...proveedorRawValue,
        id: { value: proveedorRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ProveedorFormDefaults {
    return {
      id: null,
    };
  }
}
