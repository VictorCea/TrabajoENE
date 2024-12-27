import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IRecetas, NewRecetas } from '../recetas.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRecetas for edit and NewRecetasFormGroupInput for create.
 */
type RecetasFormGroupInput = IRecetas | PartialWithRequiredKeyOf<NewRecetas>;

type RecetasFormDefaults = Pick<NewRecetas, 'id'>;

type RecetasFormGroupContent = {
  id: FormControl<IRecetas['id'] | NewRecetas['id']>;
  nombrePlato: FormControl<IRecetas['nombrePlato']>;
  ingredientesPlato: FormControl<IRecetas['ingredientesPlato']>;
  tiempoPreparacion: FormControl<IRecetas['tiempoPreparacion']>;
};

export type RecetasFormGroup = FormGroup<RecetasFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RecetasFormService {
  createRecetasFormGroup(recetas: RecetasFormGroupInput = { id: null }): RecetasFormGroup {
    const recetasRawValue = {
      ...this.getFormDefaults(),
      ...recetas,
    };
    return new FormGroup<RecetasFormGroupContent>({
      id: new FormControl(
        { value: recetasRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nombrePlato: new FormControl(recetasRawValue.nombrePlato),
      ingredientesPlato: new FormControl(recetasRawValue.ingredientesPlato),
      tiempoPreparacion: new FormControl(recetasRawValue.tiempoPreparacion),
    });
  }

  getRecetas(form: RecetasFormGroup): IRecetas | NewRecetas {
    return form.getRawValue() as IRecetas | NewRecetas;
  }

  resetForm(form: RecetasFormGroup, recetas: RecetasFormGroupInput): void {
    const recetasRawValue = { ...this.getFormDefaults(), ...recetas };
    form.reset(
      {
        ...recetasRawValue,
        id: { value: recetasRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): RecetasFormDefaults {
    return {
      id: null,
    };
  }
}
