import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IPedidosProveedores } from '../pedidos-proveedores.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../pedidos-proveedores.test-samples';

import { PedidosProveedoresService } from './pedidos-proveedores.service';

const requireRestSample: IPedidosProveedores = {
  ...sampleWithRequiredData,
};

describe('PedidosProveedores Service', () => {
  let service: PedidosProveedoresService;
  let httpMock: HttpTestingController;
  let expectedResult: IPedidosProveedores | IPedidosProveedores[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(PedidosProveedoresService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a PedidosProveedores', () => {
      const pedidosProveedores = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(pedidosProveedores).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PedidosProveedores', () => {
      const pedidosProveedores = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(pedidosProveedores).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PedidosProveedores', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PedidosProveedores', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PedidosProveedores', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPedidosProveedoresToCollectionIfMissing', () => {
      it('should add a PedidosProveedores to an empty array', () => {
        const pedidosProveedores: IPedidosProveedores = sampleWithRequiredData;
        expectedResult = service.addPedidosProveedoresToCollectionIfMissing([], pedidosProveedores);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pedidosProveedores);
      });

      it('should not add a PedidosProveedores to an array that contains it', () => {
        const pedidosProveedores: IPedidosProveedores = sampleWithRequiredData;
        const pedidosProveedoresCollection: IPedidosProveedores[] = [
          {
            ...pedidosProveedores,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPedidosProveedoresToCollectionIfMissing(pedidosProveedoresCollection, pedidosProveedores);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PedidosProveedores to an array that doesn't contain it", () => {
        const pedidosProveedores: IPedidosProveedores = sampleWithRequiredData;
        const pedidosProveedoresCollection: IPedidosProveedores[] = [sampleWithPartialData];
        expectedResult = service.addPedidosProveedoresToCollectionIfMissing(pedidosProveedoresCollection, pedidosProveedores);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pedidosProveedores);
      });

      it('should add only unique PedidosProveedores to an array', () => {
        const pedidosProveedoresArray: IPedidosProveedores[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const pedidosProveedoresCollection: IPedidosProveedores[] = [sampleWithRequiredData];
        expectedResult = service.addPedidosProveedoresToCollectionIfMissing(pedidosProveedoresCollection, ...pedidosProveedoresArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const pedidosProveedores: IPedidosProveedores = sampleWithRequiredData;
        const pedidosProveedores2: IPedidosProveedores = sampleWithPartialData;
        expectedResult = service.addPedidosProveedoresToCollectionIfMissing([], pedidosProveedores, pedidosProveedores2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pedidosProveedores);
        expect(expectedResult).toContain(pedidosProveedores2);
      });

      it('should accept null and undefined values', () => {
        const pedidosProveedores: IPedidosProveedores = sampleWithRequiredData;
        expectedResult = service.addPedidosProveedoresToCollectionIfMissing([], null, pedidosProveedores, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pedidosProveedores);
      });

      it('should return initial array if no PedidosProveedores is added', () => {
        const pedidosProveedoresCollection: IPedidosProveedores[] = [sampleWithRequiredData];
        expectedResult = service.addPedidosProveedoresToCollectionIfMissing(pedidosProveedoresCollection, undefined, null);
        expect(expectedResult).toEqual(pedidosProveedoresCollection);
      });
    });

    describe('comparePedidosProveedores', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePedidosProveedores(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 7220 };
        const entity2 = null;

        const compareResult1 = service.comparePedidosProveedores(entity1, entity2);
        const compareResult2 = service.comparePedidosProveedores(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 7220 };
        const entity2 = { id: 31162 };

        const compareResult1 = service.comparePedidosProveedores(entity1, entity2);
        const compareResult2 = service.comparePedidosProveedores(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 7220 };
        const entity2 = { id: 7220 };

        const compareResult1 = service.comparePedidosProveedores(entity1, entity2);
        const compareResult2 = service.comparePedidosProveedores(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
