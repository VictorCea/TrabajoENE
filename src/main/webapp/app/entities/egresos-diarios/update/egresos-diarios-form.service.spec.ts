import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../egresos-diarios.test-samples';

import { EgresosDiariosFormService } from './egresos-diarios-form.service';

describe('EgresosDiarios Form Service', () => {
  let service: EgresosDiariosFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EgresosDiariosFormService);
  });

  describe('Service methods', () => {
    describe('createEgresosDiariosFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEgresosDiariosFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            gastoDiario: expect.any(Object),
            valorGasto: expect.any(Object),
          }),
        );
      });

      it('passing IEgresosDiarios should create a new form with FormGroup', () => {
        const formGroup = service.createEgresosDiariosFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            gastoDiario: expect.any(Object),
            valorGasto: expect.any(Object),
          }),
        );
      });
    });

    describe('getEgresosDiarios', () => {
      it('should return NewEgresosDiarios for default EgresosDiarios initial value', () => {
        const formGroup = service.createEgresosDiariosFormGroup(sampleWithNewData);

        const egresosDiarios = service.getEgresosDiarios(formGroup) as any;

        expect(egresosDiarios).toMatchObject(sampleWithNewData);
      });

      it('should return NewEgresosDiarios for empty EgresosDiarios initial value', () => {
        const formGroup = service.createEgresosDiariosFormGroup();

        const egresosDiarios = service.getEgresosDiarios(formGroup) as any;

        expect(egresosDiarios).toMatchObject({});
      });

      it('should return IEgresosDiarios', () => {
        const formGroup = service.createEgresosDiariosFormGroup(sampleWithRequiredData);

        const egresosDiarios = service.getEgresosDiarios(formGroup) as any;

        expect(egresosDiarios).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEgresosDiarios should not enable id FormControl', () => {
        const formGroup = service.createEgresosDiariosFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEgresosDiarios should disable id FormControl', () => {
        const formGroup = service.createEgresosDiariosFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
