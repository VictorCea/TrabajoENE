import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IOrdenesCocina, NewOrdenesCocina } from '../ordenes-cocina.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IOrdenesCocina for edit and NewOrdenesCocinaFormGroupInput for create.
 */
type OrdenesCocinaFormGroupInput = IOrdenesCocina | PartialWithRequiredKeyOf<NewOrdenesCocina>;

type OrdenesCocinaFormDefaults = Pick<NewOrdenesCocina, 'id'>;

type OrdenesCocinaFormGroupContent = {
  id: FormControl<IOrdenesCocina['id'] | NewOrdenesCocina['id']>;
  nombrePlato: FormControl<IOrdenesCocina['nombrePlato']>;
  cantidadPlato: FormControl<IOrdenesCocina['cantidadPlato']>;
};

export type OrdenesCocinaFormGroup = FormGroup<OrdenesCocinaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class OrdenesCocinaFormService {
  createOrdenesCocinaFormGroup(ordenesCocina: OrdenesCocinaFormGroupInput = { id: null }): OrdenesCocinaFormGroup {
    const ordenesCocinaRawValue = {
      ...this.getFormDefaults(),
      ...ordenesCocina,
    };
    return new FormGroup<OrdenesCocinaFormGroupContent>({
      id: new FormControl(
        { value: ordenesCocinaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nombrePlato: new FormControl(ordenesCocinaRawValue.nombrePlato),
      cantidadPlato: new FormControl(ordenesCocinaRawValue.cantidadPlato),
    });
  }

  getOrdenesCocina(form: OrdenesCocinaFormGroup): IOrdenesCocina | NewOrdenesCocina {
    return form.getRawValue() as IOrdenesCocina | NewOrdenesCocina;
  }

  resetForm(form: OrdenesCocinaFormGroup, ordenesCocina: OrdenesCocinaFormGroupInput): void {
    const ordenesCocinaRawValue = { ...this.getFormDefaults(), ...ordenesCocina };
    form.reset(
      {
        ...ordenesCocinaRawValue,
        id: { value: ordenesCocinaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): OrdenesCocinaFormDefaults {
    return {
      id: null,
    };
  }
}
