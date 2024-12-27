import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IRecetas } from '../recetas.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../recetas.test-samples';

import { RecetasService } from './recetas.service';

const requireRestSample: IRecetas = {
  ...sampleWithRequiredData,
};

describe('Recetas Service', () => {
  let service: RecetasService;
  let httpMock: HttpTestingController;
  let expectedResult: IRecetas | IRecetas[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(RecetasService);
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

    it('should create a Recetas', () => {
      const recetas = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(recetas).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Recetas', () => {
      const recetas = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(recetas).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Recetas', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Recetas', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Recetas', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addRecetasToCollectionIfMissing', () => {
      it('should add a Recetas to an empty array', () => {
        const recetas: IRecetas = sampleWithRequiredData;
        expectedResult = service.addRecetasToCollectionIfMissing([], recetas);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(recetas);
      });

      it('should not add a Recetas to an array that contains it', () => {
        const recetas: IRecetas = sampleWithRequiredData;
        const recetasCollection: IRecetas[] = [
          {
            ...recetas,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addRecetasToCollectionIfMissing(recetasCollection, recetas);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Recetas to an array that doesn't contain it", () => {
        const recetas: IRecetas = sampleWithRequiredData;
        const recetasCollection: IRecetas[] = [sampleWithPartialData];
        expectedResult = service.addRecetasToCollectionIfMissing(recetasCollection, recetas);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(recetas);
      });

      it('should add only unique Recetas to an array', () => {
        const recetasArray: IRecetas[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const recetasCollection: IRecetas[] = [sampleWithRequiredData];
        expectedResult = service.addRecetasToCollectionIfMissing(recetasCollection, ...recetasArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const recetas: IRecetas = sampleWithRequiredData;
        const recetas2: IRecetas = sampleWithPartialData;
        expectedResult = service.addRecetasToCollectionIfMissing([], recetas, recetas2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(recetas);
        expect(expectedResult).toContain(recetas2);
      });

      it('should accept null and undefined values', () => {
        const recetas: IRecetas = sampleWithRequiredData;
        expectedResult = service.addRecetasToCollectionIfMissing([], null, recetas, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(recetas);
      });

      it('should return initial array if no Recetas is added', () => {
        const recetasCollection: IRecetas[] = [sampleWithRequiredData];
        expectedResult = service.addRecetasToCollectionIfMissing(recetasCollection, undefined, null);
        expect(expectedResult).toEqual(recetasCollection);
      });
    });

    describe('compareRecetas', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareRecetas(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 7707 };
        const entity2 = null;

        const compareResult1 = service.compareRecetas(entity1, entity2);
        const compareResult2 = service.compareRecetas(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 7707 };
        const entity2 = { id: 20001 };

        const compareResult1 = service.compareRecetas(entity1, entity2);
        const compareResult2 = service.compareRecetas(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 7707 };
        const entity2 = { id: 7707 };

        const compareResult1 = service.compareRecetas(entity1, entity2);
        const compareResult2 = service.compareRecetas(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
