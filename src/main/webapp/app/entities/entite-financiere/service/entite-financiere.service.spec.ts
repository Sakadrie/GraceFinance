import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IEntiteFinanciere } from '../../../shared/model/principal/entite-financiere.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../entite-financiere.test-samples';

import { EntiteFinanciereService } from './entite-financiere.service';

const requireRestSample: IEntiteFinanciere = {
  ...sampleWithRequiredData,
};

describe('EntiteFinanciere Service', () => {
  let service: EntiteFinanciereService;
  let httpMock: HttpTestingController;
  let expectedResult: IEntiteFinanciere | IEntiteFinanciere[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(EntiteFinanciereService);
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

    it('should create a EntiteFinanciere', () => {
      const entiteFinanciere = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(entiteFinanciere).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EntiteFinanciere', () => {
      const entiteFinanciere = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(entiteFinanciere).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a EntiteFinanciere', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of EntiteFinanciere', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a EntiteFinanciere', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEntiteFinanciereToCollectionIfMissing', () => {
      it('should add a EntiteFinanciere to an empty array', () => {
        const entiteFinanciere: IEntiteFinanciere = sampleWithRequiredData;
        expectedResult = service.addEntiteFinanciereToCollectionIfMissing([], entiteFinanciere);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(entiteFinanciere);
      });

      it('should not add a EntiteFinanciere to an array that contains it', () => {
        const entiteFinanciere: IEntiteFinanciere = sampleWithRequiredData;
        const entiteFinanciereCollection: IEntiteFinanciere[] = [
          {
            ...entiteFinanciere,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEntiteFinanciereToCollectionIfMissing(entiteFinanciereCollection, entiteFinanciere);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EntiteFinanciere to an array that doesn't contain it", () => {
        const entiteFinanciere: IEntiteFinanciere = sampleWithRequiredData;
        const entiteFinanciereCollection: IEntiteFinanciere[] = [sampleWithPartialData];
        expectedResult = service.addEntiteFinanciereToCollectionIfMissing(entiteFinanciereCollection, entiteFinanciere);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(entiteFinanciere);
      });

      it('should add only unique EntiteFinanciere to an array', () => {
        const entiteFinanciereArray: IEntiteFinanciere[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const entiteFinanciereCollection: IEntiteFinanciere[] = [sampleWithRequiredData];
        expectedResult = service.addEntiteFinanciereToCollectionIfMissing(entiteFinanciereCollection, ...entiteFinanciereArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const entiteFinanciere: IEntiteFinanciere = sampleWithRequiredData;
        const entiteFinanciere2: IEntiteFinanciere = sampleWithPartialData;
        expectedResult = service.addEntiteFinanciereToCollectionIfMissing([], entiteFinanciere, entiteFinanciere2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(entiteFinanciere);
        expect(expectedResult).toContain(entiteFinanciere2);
      });

      it('should accept null and undefined values', () => {
        const entiteFinanciere: IEntiteFinanciere = sampleWithRequiredData;
        expectedResult = service.addEntiteFinanciereToCollectionIfMissing([], null, entiteFinanciere, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(entiteFinanciere);
      });

      it('should return initial array if no EntiteFinanciere is added', () => {
        const entiteFinanciereCollection: IEntiteFinanciere[] = [sampleWithRequiredData];
        expectedResult = service.addEntiteFinanciereToCollectionIfMissing(entiteFinanciereCollection, undefined, null);
        expect(expectedResult).toEqual(entiteFinanciereCollection);
      });
    });

    describe('compareEntiteFinanciere', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEntiteFinanciere(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 8941 };
        const entity2 = null;

        const compareResult1 = service.compareEntiteFinanciere(entity1, entity2);
        const compareResult2 = service.compareEntiteFinanciere(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 8941 };
        const entity2 = { id: 4924 };

        const compareResult1 = service.compareEntiteFinanciere(entity1, entity2);
        const compareResult2 = service.compareEntiteFinanciere(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 8941 };
        const entity2 = { id: 8941 };

        const compareResult1 = service.compareEntiteFinanciere(entity1, entity2);
        const compareResult2 = service.compareEntiteFinanciere(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
