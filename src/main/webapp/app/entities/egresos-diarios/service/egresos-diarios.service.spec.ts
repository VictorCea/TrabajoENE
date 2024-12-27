import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IEgresosDiarios } from '../egresos-diarios.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../egresos-diarios.test-samples';

import { EgresosDiariosService } from './egresos-diarios.service';

const requireRestSample: IEgresosDiarios = {
  ...sampleWithRequiredData,
};

describe('EgresosDiarios Service', () => {
  let service: EgresosDiariosService;
  let httpMock: HttpTestingController;
  let expectedResult: IEgresosDiarios | IEgresosDiarios[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(EgresosDiariosService);
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

    it('should create a EgresosDiarios', () => {
      const egresosDiarios = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(egresosDiarios).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EgresosDiarios', () => {
      const egresosDiarios = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(egresosDiarios).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a EgresosDiarios', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of EgresosDiarios', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a EgresosDiarios', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEgresosDiariosToCollectionIfMissing', () => {
      it('should add a EgresosDiarios to an empty array', () => {
        const egresosDiarios: IEgresosDiarios = sampleWithRequiredData;
        expectedResult = service.addEgresosDiariosToCollectionIfMissing([], egresosDiarios);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(egresosDiarios);
      });

      it('should not add a EgresosDiarios to an array that contains it', () => {
        const egresosDiarios: IEgresosDiarios = sampleWithRequiredData;
        const egresosDiariosCollection: IEgresosDiarios[] = [
          {
            ...egresosDiarios,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEgresosDiariosToCollectionIfMissing(egresosDiariosCollection, egresosDiarios);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EgresosDiarios to an array that doesn't contain it", () => {
        const egresosDiarios: IEgresosDiarios = sampleWithRequiredData;
        const egresosDiariosCollection: IEgresosDiarios[] = [sampleWithPartialData];
        expectedResult = service.addEgresosDiariosToCollectionIfMissing(egresosDiariosCollection, egresosDiarios);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(egresosDiarios);
      });

      it('should add only unique EgresosDiarios to an array', () => {
        const egresosDiariosArray: IEgresosDiarios[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const egresosDiariosCollection: IEgresosDiarios[] = [sampleWithRequiredData];
        expectedResult = service.addEgresosDiariosToCollectionIfMissing(egresosDiariosCollection, ...egresosDiariosArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const egresosDiarios: IEgresosDiarios = sampleWithRequiredData;
        const egresosDiarios2: IEgresosDiarios = sampleWithPartialData;
        expectedResult = service.addEgresosDiariosToCollectionIfMissing([], egresosDiarios, egresosDiarios2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(egresosDiarios);
        expect(expectedResult).toContain(egresosDiarios2);
      });

      it('should accept null and undefined values', () => {
        const egresosDiarios: IEgresosDiarios = sampleWithRequiredData;
        expectedResult = service.addEgresosDiariosToCollectionIfMissing([], null, egresosDiarios, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(egresosDiarios);
      });

      it('should return initial array if no EgresosDiarios is added', () => {
        const egresosDiariosCollection: IEgresosDiarios[] = [sampleWithRequiredData];
        expectedResult = service.addEgresosDiariosToCollectionIfMissing(egresosDiariosCollection, undefined, null);
        expect(expectedResult).toEqual(egresosDiariosCollection);
      });
    });

    describe('compareEgresosDiarios', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEgresosDiarios(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 1591 };
        const entity2 = null;

        const compareResult1 = service.compareEgresosDiarios(entity1, entity2);
        const compareResult2 = service.compareEgresosDiarios(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 1591 };
        const entity2 = { id: 22661 };

        const compareResult1 = service.compareEgresosDiarios(entity1, entity2);
        const compareResult2 = service.compareEgresosDiarios(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 1591 };
        const entity2 = { id: 1591 };

        const compareResult1 = service.compareEgresosDiarios(entity1, entity2);
        const compareResult2 = service.compareEgresosDiarios(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
