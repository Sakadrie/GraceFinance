import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IDroit } from '../droit.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../droit.test-samples';

import { DroitService } from './droit.service';

const requireRestSample: IDroit = {
  ...sampleWithRequiredData,
};

describe('Droit Service', () => {
  let service: DroitService;
  let httpMock: HttpTestingController;
  let expectedResult: IDroit | IDroit[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(DroitService);
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

    it('should create a Droit', () => {
      const droit = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(droit).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Droit', () => {
      const droit = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(droit).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Droit', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Droit', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Droit', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDroitToCollectionIfMissing', () => {
      it('should add a Droit to an empty array', () => {
        const droit: IDroit = sampleWithRequiredData;
        expectedResult = service.addDroitToCollectionIfMissing([], droit);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(droit);
      });

      it('should not add a Droit to an array that contains it', () => {
        const droit: IDroit = sampleWithRequiredData;
        const droitCollection: IDroit[] = [
          {
            ...droit,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDroitToCollectionIfMissing(droitCollection, droit);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Droit to an array that doesn't contain it", () => {
        const droit: IDroit = sampleWithRequiredData;
        const droitCollection: IDroit[] = [sampleWithPartialData];
        expectedResult = service.addDroitToCollectionIfMissing(droitCollection, droit);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(droit);
      });

      it('should add only unique Droit to an array', () => {
        const droitArray: IDroit[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const droitCollection: IDroit[] = [sampleWithRequiredData];
        expectedResult = service.addDroitToCollectionIfMissing(droitCollection, ...droitArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const droit: IDroit = sampleWithRequiredData;
        const droit2: IDroit = sampleWithPartialData;
        expectedResult = service.addDroitToCollectionIfMissing([], droit, droit2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(droit);
        expect(expectedResult).toContain(droit2);
      });

      it('should accept null and undefined values', () => {
        const droit: IDroit = sampleWithRequiredData;
        expectedResult = service.addDroitToCollectionIfMissing([], null, droit, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(droit);
      });

      it('should return initial array if no Droit is added', () => {
        const droitCollection: IDroit[] = [sampleWithRequiredData];
        expectedResult = service.addDroitToCollectionIfMissing(droitCollection, undefined, null);
        expect(expectedResult).toEqual(droitCollection);
      });
    });

    describe('compareDroit', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDroit(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 23804 };
        const entity2 = null;

        const compareResult1 = service.compareDroit(entity1, entity2);
        const compareResult2 = service.compareDroit(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 23804 };
        const entity2 = { id: 30538 };

        const compareResult1 = service.compareDroit(entity1, entity2);
        const compareResult2 = service.compareDroit(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 23804 };
        const entity2 = { id: 23804 };

        const compareResult1 = service.compareDroit(entity1, entity2);
        const compareResult2 = service.compareDroit(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
