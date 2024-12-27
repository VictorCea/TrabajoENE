import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IMesa, NewMesa } from '../mesa.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMesa for edit and NewMesaFormGroupInput for create.
 */
type MesaFormGroupInput = IMesa | PartialWithRequiredKeyOf<NewMesa>;

type MesaFormDefaults = Pick<NewMesa, 'id'>;

type MesaFormGroupContent = {
  id: FormControl<IMesa['id'] | NewMesa['id']>;
  numero: FormControl<IMesa['numero']>;
};

export type MesaFormGroup = FormGroup<MesaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MesaFormService {
  createMesaFormGroup(mesa: MesaFormGroupInput = { id: null }): MesaFormGroup {
    const mesaRawValue = {
      ...this.getFormDefaults(),
      ...mesa,
    };
    return new FormGroup<MesaFormGroupContent>({
      id: new FormControl(
        { value: mesaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      numero: new FormControl(mesaRawValue.numero),
    });
  }

  getMesa(form: MesaFormGroup): IMesa | NewMesa {
    return form.getRawValue() as IMesa | NewMesa;
  }

  resetForm(form: MesaFormGroup, mesa: MesaFormGroupInput): void {
    const mesaRawValue = { ...this.getFormDefaults(), ...mesa };
    form.reset(
      {
        ...mesaRawValue,
        id: { value: mesaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): MesaFormDefaults {
    return {
      id: null,
    };
  }
}
