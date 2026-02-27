import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../entite-financiere.test-samples';

import { EntiteFinanciereFormService } from './entite-financiere-form.service';

describe('EntiteFinanciere Form Service', () => {
  let service: EntiteFinanciereFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EntiteFinanciereFormService);
  });

  describe('Service methods', () => {
    describe('createEntiteFinanciereFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEntiteFinanciereFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            code: expect.any(Object),
            type: expect.any(Object),
            description: expect.any(Object),
            actif: expect.any(Object),
            egliseLiees: expect.any(Object),
            structureLiees: expect.any(Object),
          }),
        );
      });

      it('passing IEntiteFinanciere should create a new form with FormGroup', () => {
        const formGroup = service.createEntiteFinanciereFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            code: expect.any(Object),
            type: expect.any(Object),
            description: expect.any(Object),
            actif: expect.any(Object),
            egliseLiees: expect.any(Object),
            structureLiees: expect.any(Object),
          }),
        );
      });
    });

    describe('getEntiteFinanciere', () => {
      it('should return NewEntiteFinanciere for default EntiteFinanciere initial value', () => {
        const formGroup = service.createEntiteFinanciereFormGroup(sampleWithNewData);

        const entiteFinanciere = service.getEntiteFinanciere(formGroup) as any;

        expect(entiteFinanciere).toMatchObject(sampleWithNewData);
      });

      it('should return NewEntiteFinanciere for empty EntiteFinanciere initial value', () => {
        const formGroup = service.createEntiteFinanciereFormGroup();

        const entiteFinanciere = service.getEntiteFinanciere(formGroup) as any;

        expect(entiteFinanciere).toMatchObject({});
      });

      it('should return IEntiteFinanciere', () => {
        const formGroup = service.createEntiteFinanciereFormGroup(sampleWithRequiredData);

        const entiteFinanciere = service.getEntiteFinanciere(formGroup) as any;

        expect(entiteFinanciere).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEntiteFinanciere should not enable id FormControl', () => {
        const formGroup = service.createEntiteFinanciereFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEntiteFinanciere should disable id FormControl', () => {
        const formGroup = service.createEntiteFinanciereFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
