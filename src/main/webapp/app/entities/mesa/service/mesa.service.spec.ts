import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IMesa } from '../mesa.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../mesa.test-samples';

import { MesaService } from './mesa.service';

const requireRestSample: IMesa = {
  ...sampleWithRequiredData,
};

describe('Mesa Service', () => {
  let service: MesaService;
  let httpMock: HttpTestingController;
  let expectedResult: IMesa | IMesa[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(MesaService);
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

    it('should create a Mesa', () => {
      const mesa = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(mesa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Mesa', () => {
      const mesa = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(mesa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Mesa', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Mesa', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Mesa', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addMesaToCollectionIfMissing', () => {
      it('should add a Mesa to an empty array', () => {
        const mesa: IMesa = sampleWithRequiredData;
        expectedResult = service.addMesaToCollectionIfMissing([], mesa);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(mesa);
      });

      it('should not add a Mesa to an array that contains it', () => {
        const mesa: IMesa = sampleWithRequiredData;
        const mesaCollection: IMesa[] = [
          {
            ...mesa,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMesaToCollectionIfMissing(mesaCollection, mesa);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Mesa to an array that doesn't contain it", () => {
        const mesa: IMesa = sampleWithRequiredData;
        const mesaCollection: IMesa[] = [sampleWithPartialData];
        expectedResult = service.addMesaToCollectionIfMissing(mesaCollection, mesa);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(mesa);
      });

      it('should add only unique Mesa to an array', () => {
        const mesaArray: IMesa[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const mesaCollection: IMesa[] = [sampleWithRequiredData];
        expectedResult = service.addMesaToCollectionIfMissing(mesaCollection, ...mesaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const mesa: IMesa = sampleWithRequiredData;
        const mesa2: IMesa = sampleWithPartialData;
        expectedResult = service.addMesaToCollectionIfMissing([], mesa, mesa2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(mesa);
        expect(expectedResult).toContain(mesa2);
      });

      it('should accept null and undefined values', () => {
        const mesa: IMesa = sampleWithRequiredData;
        expectedResult = service.addMesaToCollectionIfMissing([], null, mesa, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(mesa);
      });

      it('should return initial array if no Mesa is added', () => {
        const mesaCollection: IMesa[] = [sampleWithRequiredData];
        expectedResult = service.addMesaToCollectionIfMissing(mesaCollection, undefined, null);
        expect(expectedResult).toEqual(mesaCollection);
      });
    });

    describe('compareMesa', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMesa(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 9830 };
        const entity2 = null;

        const compareResult1 = service.compareMesa(entity1, entity2);
        const compareResult2 = service.compareMesa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 9830 };
        const entity2 = { id: 10514 };

        const compareResult1 = service.compareMesa(entity1, entity2);
        const compareResult2 = service.compareMesa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 9830 };
        const entity2 = { id: 9830 };

        const compareResult1 = service.compareMesa(entity1, entity2);
        const compareResult2 = service.compareMesa(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
