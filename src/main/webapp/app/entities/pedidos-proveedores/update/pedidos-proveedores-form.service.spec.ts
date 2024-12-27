import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../pedidos-proveedores.test-samples';

import { PedidosProveedoresFormService } from './pedidos-proveedores-form.service';

describe('PedidosProveedores Form Service', () => {
  let service: PedidosProveedoresFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PedidosProveedoresFormService);
  });

  describe('Service methods', () => {
    describe('createPedidosProveedoresFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPedidosProveedoresFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            producto: expect.any(Object),
            cantidad: expect.any(Object),
            valor: expect.any(Object),
          }),
        );
      });

      it('passing IPedidosProveedores should create a new form with FormGroup', () => {
        const formGroup = service.createPedidosProveedoresFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            producto: expect.any(Object),
            cantidad: expect.any(Object),
            valor: expect.any(Object),
          }),
        );
      });
    });

    describe('getPedidosProveedores', () => {
      it('should return NewPedidosProveedores for default PedidosProveedores initial value', () => {
        const formGroup = service.createPedidosProveedoresFormGroup(sampleWithNewData);

        const pedidosProveedores = service.getPedidosProveedores(formGroup) as any;

        expect(pedidosProveedores).toMatchObject(sampleWithNewData);
      });

      it('should return NewPedidosProveedores for empty PedidosProveedores initial value', () => {
        const formGroup = service.createPedidosProveedoresFormGroup();

        const pedidosProveedores = service.getPedidosProveedores(formGroup) as any;

        expect(pedidosProveedores).toMatchObject({});
      });

      it('should return IPedidosProveedores', () => {
        const formGroup = service.createPedidosProveedoresFormGroup(sampleWithRequiredData);

        const pedidosProveedores = service.getPedidosProveedores(formGroup) as any;

        expect(pedidosProveedores).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPedidosProveedores should not enable id FormControl', () => {
        const formGroup = service.createPedidosProveedoresFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPedidosProveedores should disable id FormControl', () => {
        const formGroup = service.createPedidosProveedoresFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
