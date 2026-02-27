import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ILigneEcriture } from '../ligne-ecriture.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../ligne-ecriture.test-samples';

import { LigneEcritureService } from './ligne-ecriture.service';

const requireRestSample: ILigneEcriture = {
  ...sampleWithRequiredData,
};

describe('LigneEcriture Service', () => {
  let service: LigneEcritureService;
  let httpMock: HttpTestingController;
  let expectedResult: ILigneEcriture | ILigneEcriture[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(LigneEcritureService);
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

    it('should create a LigneEcriture', () => {
      const ligneEcriture = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(ligneEcriture).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a LigneEcriture', () => {
      const ligneEcriture = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(ligneEcriture).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a LigneEcriture', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of LigneEcriture', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a LigneEcriture', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addLigneEcritureToCollectionIfMissing', () => {
      it('should add a LigneEcriture to an empty array', () => {
        const ligneEcriture: ILigneEcriture = sampleWithRequiredData;
        expectedResult = service.addLigneEcritureToCollectionIfMissing([], ligneEcriture);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ligneEcriture);
      });

      it('should not add a LigneEcriture to an array that contains it', () => {
        const ligneEcriture: ILigneEcriture = sampleWithRequiredData;
        const ligneEcritureCollection: ILigneEcriture[] = [
          {
            ...ligneEcriture,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addLigneEcritureToCollectionIfMissing(ligneEcritureCollection, ligneEcriture);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a LigneEcriture to an array that doesn't contain it", () => {
        const ligneEcriture: ILigneEcriture = sampleWithRequiredData;
        const ligneEcritureCollection: ILigneEcriture[] = [sampleWithPartialData];
        expectedResult = service.addLigneEcritureToCollectionIfMissing(ligneEcritureCollection, ligneEcriture);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ligneEcriture);
      });

      it('should add only unique LigneEcriture to an array', () => {
        const ligneEcritureArray: ILigneEcriture[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const ligneEcritureCollection: ILigneEcriture[] = [sampleWithRequiredData];
        expectedResult = service.addLigneEcritureToCollectionIfMissing(ligneEcritureCollection, ...ligneEcritureArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const ligneEcriture: ILigneEcriture = sampleWithRequiredData;
        const ligneEcriture2: ILigneEcriture = sampleWithPartialData;
        expectedResult = service.addLigneEcritureToCollectionIfMissing([], ligneEcriture, ligneEcriture2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ligneEcriture);
        expect(expectedResult).toContain(ligneEcriture2);
      });

      it('should accept null and undefined values', () => {
        const ligneEcriture: ILigneEcriture = sampleWithRequiredData;
        expectedResult = service.addLigneEcritureToCollectionIfMissing([], null, ligneEcriture, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ligneEcriture);
      });

      it('should return initial array if no LigneEcriture is added', () => {
        const ligneEcritureCollection: ILigneEcriture[] = [sampleWithRequiredData];
        expectedResult = service.addLigneEcritureToCollectionIfMissing(ligneEcritureCollection, undefined, null);
        expect(expectedResult).toEqual(ligneEcritureCollection);
      });
    });

    describe('compareLigneEcriture', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareLigneEcriture(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 32726 };
        const entity2 = null;

        const compareResult1 = service.compareLigneEcriture(entity1, entity2);
        const compareResult2 = service.compareLigneEcriture(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 32726 };
        const entity2 = { id: 28772 };

        const compareResult1 = service.compareLigneEcriture(entity1, entity2);
        const compareResult2 = service.compareLigneEcriture(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 32726 };
        const entity2 = { id: 32726 };

        const compareResult1 = service.compareLigneEcriture(entity1, entity2);
        const compareResult2 = service.compareLigneEcriture(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
