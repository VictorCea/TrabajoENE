import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IIngresosDiarios } from '../ingresos-diarios.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../ingresos-diarios.test-samples';

import { IngresosDiariosService } from './ingresos-diarios.service';

const requireRestSample: IIngresosDiarios = {
  ...sampleWithRequiredData,
};

describe('IngresosDiarios Service', () => {
  let service: IngresosDiariosService;
  let httpMock: HttpTestingController;
  let expectedResult: IIngresosDiarios | IIngresosDiarios[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(IngresosDiariosService);
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

    it('should create a IngresosDiarios', () => {
      const ingresosDiarios = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(ingresosDiarios).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a IngresosDiarios', () => {
      const ingresosDiarios = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(ingresosDiarios).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a IngresosDiarios', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of IngresosDiarios', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a IngresosDiarios', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addIngresosDiariosToCollectionIfMissing', () => {
      it('should add a IngresosDiarios to an empty array', () => {
        const ingresosDiarios: IIngresosDiarios = sampleWithRequiredData;
        expectedResult = service.addIngresosDiariosToCollectionIfMissing([], ingresosDiarios);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ingresosDiarios);
      });

      it('should not add a IngresosDiarios to an array that contains it', () => {
        const ingresosDiarios: IIngresosDiarios = sampleWithRequiredData;
        const ingresosDiariosCollection: IIngresosDiarios[] = [
          {
            ...ingresosDiarios,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addIngresosDiariosToCollectionIfMissing(ingresosDiariosCollection, ingresosDiarios);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a IngresosDiarios to an array that doesn't contain it", () => {
        const ingresosDiarios: IIngresosDiarios = sampleWithRequiredData;
        const ingresosDiariosCollection: IIngresosDiarios[] = [sampleWithPartialData];
        expectedResult = service.addIngresosDiariosToCollectionIfMissing(ingresosDiariosCollection, ingresosDiarios);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ingresosDiarios);
      });

      it('should add only unique IngresosDiarios to an array', () => {
        const ingresosDiariosArray: IIngresosDiarios[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const ingresosDiariosCollection: IIngresosDiarios[] = [sampleWithRequiredData];
        expectedResult = service.addIngresosDiariosToCollectionIfMissing(ingresosDiariosCollection, ...ingresosDiariosArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const ingresosDiarios: IIngresosDiarios = sampleWithRequiredData;
        const ingresosDiarios2: IIngresosDiarios = sampleWithPartialData;
        expectedResult = service.addIngresosDiariosToCollectionIfMissing([], ingresosDiarios, ingresosDiarios2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ingresosDiarios);
        expect(expectedResult).toContain(ingresosDiarios2);
      });

      it('should accept null and undefined values', () => {
        const ingresosDiarios: IIngresosDiarios = sampleWithRequiredData;
        expectedResult = service.addIngresosDiariosToCollectionIfMissing([], null, ingresosDiarios, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ingresosDiarios);
      });

      it('should return initial array if no IngresosDiarios is added', () => {
        const ingresosDiariosCollection: IIngresosDiarios[] = [sampleWithRequiredData];
        expectedResult = service.addIngresosDiariosToCollectionIfMissing(ingresosDiariosCollection, undefined, null);
        expect(expectedResult).toEqual(ingresosDiariosCollection);
      });
    });

    describe('compareIngresosDiarios', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareIngresosDiarios(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 3106 };
        const entity2 = null;

        const compareResult1 = service.compareIngresosDiarios(entity1, entity2);
        const compareResult2 = service.compareIngresosDiarios(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 3106 };
        const entity2 = { id: 19403 };

        const compareResult1 = service.compareIngresosDiarios(entity1, entity2);
        const compareResult2 = service.compareIngresosDiarios(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 3106 };
        const entity2 = { id: 3106 };

        const compareResult1 = service.compareIngresosDiarios(entity1, entity2);
        const compareResult2 = service.compareIngresosDiarios(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
