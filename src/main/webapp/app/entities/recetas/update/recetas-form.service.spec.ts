import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../recetas.test-samples';

import { RecetasFormService } from './recetas-form.service';

describe('Recetas Form Service', () => {
  let service: RecetasFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RecetasFormService);
  });

  describe('Service methods', () => {
    describe('createRecetasFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createRecetasFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombrePlato: expect.any(Object),
            ingredientesPlato: expect.any(Object),
            tiempoPreparacion: expect.any(Object),
          }),
        );
      });

      it('passing IRecetas should create a new form with FormGroup', () => {
        const formGroup = service.createRecetasFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombrePlato: expect.any(Object),
            ingredientesPlato: expect.any(Object),
            tiempoPreparacion: expect.any(Object),
          }),
        );
      });
    });

    describe('getRecetas', () => {
      it('should return NewRecetas for default Recetas initial value', () => {
        const formGroup = service.createRecetasFormGroup(sampleWithNewData);

        const recetas = service.getRecetas(formGroup) as any;

        expect(recetas).toMatchObject(sampleWithNewData);
      });

      it('should return NewRecetas for empty Recetas initial value', () => {
        const formGroup = service.createRecetasFormGroup();

        const recetas = service.getRecetas(formGroup) as any;

        expect(recetas).toMatchObject({});
      });

      it('should return IRecetas', () => {
        const formGroup = service.createRecetasFormGroup(sampleWithRequiredData);

        const recetas = service.getRecetas(formGroup) as any;

        expect(recetas).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IRecetas should not enable id FormControl', () => {
        const formGroup = service.createRecetasFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewRecetas should disable id FormControl', () => {
        const formGroup = service.createRecetasFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
