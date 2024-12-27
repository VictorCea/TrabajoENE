import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IEgresosDiarios, NewEgresosDiarios } from '../egresos-diarios.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEgresosDiarios for edit and NewEgresosDiariosFormGroupInput for create.
 */
type EgresosDiariosFormGroupInput = IEgresosDiarios | PartialWithRequiredKeyOf<NewEgresosDiarios>;

type EgresosDiariosFormDefaults = Pick<NewEgresosDiarios, 'id'>;

type EgresosDiariosFormGroupContent = {
  id: FormControl<IEgresosDiarios['id'] | NewEgresosDiarios['id']>;
  gastoDiario: FormControl<IEgresosDiarios['gastoDiario']>;
  valorGasto: FormControl<IEgresosDiarios['valorGasto']>;
};

export type EgresosDiariosFormGroup = FormGroup<EgresosDiariosFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EgresosDiariosFormService {
  createEgresosDiariosFormGroup(egresosDiarios: EgresosDiariosFormGroupInput = { id: null }): EgresosDiariosFormGroup {
    const egresosDiariosRawValue = {
      ...this.getFormDefaults(),
      ...egresosDiarios,
    };
    return new FormGroup<EgresosDiariosFormGroupContent>({
      id: new FormControl(
        { value: egresosDiariosRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      gastoDiario: new FormControl(egresosDiariosRawValue.gastoDiario),
      valorGasto: new FormControl(egresosDiariosRawValue.valorGasto),
    });
  }

  getEgresosDiarios(form: EgresosDiariosFormGroup): IEgresosDiarios | NewEgresosDiarios {
    return form.getRawValue() as IEgresosDiarios | NewEgresosDiarios;
  }

  resetForm(form: EgresosDiariosFormGroup, egresosDiarios: EgresosDiariosFormGroupInput): void {
    const egresosDiariosRawValue = { ...this.getFormDefaults(), ...egresosDiarios };
    form.reset(
      {
        ...egresosDiariosRawValue,
        id: { value: egresosDiariosRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EgresosDiariosFormDefaults {
    return {
      id: null,
    };
  }
}
