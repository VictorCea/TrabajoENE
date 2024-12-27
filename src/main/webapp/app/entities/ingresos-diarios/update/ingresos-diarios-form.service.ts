import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IIngresosDiarios, NewIngresosDiarios } from '../ingresos-diarios.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IIngresosDiarios for edit and NewIngresosDiariosFormGroupInput for create.
 */
type IngresosDiariosFormGroupInput = IIngresosDiarios | PartialWithRequiredKeyOf<NewIngresosDiarios>;

type IngresosDiariosFormDefaults = Pick<NewIngresosDiarios, 'id'>;

type IngresosDiariosFormGroupContent = {
  id: FormControl<IIngresosDiarios['id'] | NewIngresosDiarios['id']>;
  ventaNumero: FormControl<IIngresosDiarios['ventaNumero']>;
  valorVenta: FormControl<IIngresosDiarios['valorVenta']>;
};

export type IngresosDiariosFormGroup = FormGroup<IngresosDiariosFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class IngresosDiariosFormService {
  createIngresosDiariosFormGroup(ingresosDiarios: IngresosDiariosFormGroupInput = { id: null }): IngresosDiariosFormGroup {
    const ingresosDiariosRawValue = {
      ...this.getFormDefaults(),
      ...ingresosDiarios,
    };
    return new FormGroup<IngresosDiariosFormGroupContent>({
      id: new FormControl(
        { value: ingresosDiariosRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      ventaNumero: new FormControl(ingresosDiariosRawValue.ventaNumero),
      valorVenta: new FormControl(ingresosDiariosRawValue.valorVenta),
    });
  }

  getIngresosDiarios(form: IngresosDiariosFormGroup): IIngresosDiarios | NewIngresosDiarios {
    return form.getRawValue() as IIngresosDiarios | NewIngresosDiarios;
  }

  resetForm(form: IngresosDiariosFormGroup, ingresosDiarios: IngresosDiariosFormGroupInput): void {
    const ingresosDiariosRawValue = { ...this.getFormDefaults(), ...ingresosDiarios };
    form.reset(
      {
        ...ingresosDiariosRawValue,
        id: { value: ingresosDiariosRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): IngresosDiariosFormDefaults {
    return {
      id: null,
    };
  }
}
