import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../ordenes-cocina.test-samples';

import { OrdenesCocinaFormService } from './ordenes-cocina-form.service';

describe('OrdenesCocina Form Service', () => {
  let service: OrdenesCocinaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OrdenesCocinaFormService);
  });

  describe('Service methods', () => {
    describe('createOrdenesCocinaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createOrdenesCocinaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombrePlato: expect.any(Object),
            cantidadPlato: expect.any(Object),
          }),
        );
      });

      it('passing IOrdenesCocina should create a new form with FormGroup', () => {
        const formGroup = service.createOrdenesCocinaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombrePlato: expect.any(Object),
            cantidadPlato: expect.any(Object),
          }),
        );
      });
    });

    describe('getOrdenesCocina', () => {
      it('should return NewOrdenesCocina for default OrdenesCocina initial value', () => {
        const formGroup = service.createOrdenesCocinaFormGroup(sampleWithNewData);

        const ordenesCocina = service.getOrdenesCocina(formGroup) as any;

        expect(ordenesCocina).toMatchObject(sampleWithNewData);
      });

      it('should return NewOrdenesCocina for empty OrdenesCocina initial value', () => {
        const formGroup = service.createOrdenesCocinaFormGroup();

        const ordenesCocina = service.getOrdenesCocina(formGroup) as any;

        expect(ordenesCocina).toMatchObject({});
      });

      it('should return IOrdenesCocina', () => {
        const formGroup = service.createOrdenesCocinaFormGroup(sampleWithRequiredData);

        const ordenesCocina = service.getOrdenesCocina(formGroup) as any;

        expect(ordenesCocina).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IOrdenesCocina should not enable id FormControl', () => {
        const formGroup = service.createOrdenesCocinaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewOrdenesCocina should disable id FormControl', () => {
        const formGroup = service.createOrdenesCocinaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
