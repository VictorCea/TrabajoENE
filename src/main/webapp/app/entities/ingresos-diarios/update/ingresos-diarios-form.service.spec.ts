import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../ingresos-diarios.test-samples';

import { IngresosDiariosFormService } from './ingresos-diarios-form.service';

describe('IngresosDiarios Form Service', () => {
  let service: IngresosDiariosFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IngresosDiariosFormService);
  });

  describe('Service methods', () => {
    describe('createIngresosDiariosFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createIngresosDiariosFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            ventaNumero: expect.any(Object),
            valorVenta: expect.any(Object),
          }),
        );
      });

      it('passing IIngresosDiarios should create a new form with FormGroup', () => {
        const formGroup = service.createIngresosDiariosFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            ventaNumero: expect.any(Object),
            valorVenta: expect.any(Object),
          }),
        );
      });
    });

    describe('getIngresosDiarios', () => {
      it('should return NewIngresosDiarios for default IngresosDiarios initial value', () => {
        const formGroup = service.createIngresosDiariosFormGroup(sampleWithNewData);

        const ingresosDiarios = service.getIngresosDiarios(formGroup) as any;

        expect(ingresosDiarios).toMatchObject(sampleWithNewData);
      });

      it('should return NewIngresosDiarios for empty IngresosDiarios initial value', () => {
        const formGroup = service.createIngresosDiariosFormGroup();

        const ingresosDiarios = service.getIngresosDiarios(formGroup) as any;

        expect(ingresosDiarios).toMatchObject({});
      });

      it('should return IIngresosDiarios', () => {
        const formGroup = service.createIngresosDiariosFormGroup(sampleWithRequiredData);

        const ingresosDiarios = service.getIngresosDiarios(formGroup) as any;

        expect(ingresosDiarios).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IIngresosDiarios should not enable id FormControl', () => {
        const formGroup = service.createIngresosDiariosFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewIngresosDiarios should disable id FormControl', () => {
        const formGroup = service.createIngresosDiariosFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
