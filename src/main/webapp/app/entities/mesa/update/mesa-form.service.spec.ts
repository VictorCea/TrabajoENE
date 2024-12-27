import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../mesa.test-samples';

import { MesaFormService } from './mesa-form.service';

describe('Mesa Form Service', () => {
  let service: MesaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MesaFormService);
  });

  describe('Service methods', () => {
    describe('createMesaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMesaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            numero: expect.any(Object),
          }),
        );
      });

      it('passing IMesa should create a new form with FormGroup', () => {
        const formGroup = service.createMesaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            numero: expect.any(Object),
          }),
        );
      });
    });

    describe('getMesa', () => {
      it('should return NewMesa for default Mesa initial value', () => {
        const formGroup = service.createMesaFormGroup(sampleWithNewData);

        const mesa = service.getMesa(formGroup) as any;

        expect(mesa).toMatchObject(sampleWithNewData);
      });

      it('should return NewMesa for empty Mesa initial value', () => {
        const formGroup = service.createMesaFormGroup();

        const mesa = service.getMesa(formGroup) as any;

        expect(mesa).toMatchObject({});
      });

      it('should return IMesa', () => {
        const formGroup = service.createMesaFormGroup(sampleWithRequiredData);

        const mesa = service.getMesa(formGroup) as any;

        expect(mesa).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMesa should not enable id FormControl', () => {
        const formGroup = service.createMesaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMesa should disable id FormControl', () => {
        const formGroup = service.createMesaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
