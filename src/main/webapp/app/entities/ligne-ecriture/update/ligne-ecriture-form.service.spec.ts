import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../ligne-ecriture.test-samples';

import { LigneEcritureFormService } from './ligne-ecriture-form.service';

describe('LigneEcriture Form Service', () => {
  let service: LigneEcritureFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LigneEcritureFormService);
  });

  describe('Service methods', () => {
    describe('createLigneEcritureFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createLigneEcritureFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            montant: expect.any(Object),
            sens: expect.any(Object),
            libelle: expect.any(Object),
            ecriture: expect.any(Object),
            compte: expect.any(Object),
          }),
        );
      });

      it('passing ILigneEcriture should create a new form with FormGroup', () => {
        const formGroup = service.createLigneEcritureFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            montant: expect.any(Object),
            sens: expect.any(Object),
            libelle: expect.any(Object),
            ecriture: expect.any(Object),
            compte: expect.any(Object),
          }),
        );
      });
    });

    describe('getLigneEcriture', () => {
      it('should return NewLigneEcriture for default LigneEcriture initial value', () => {
        const formGroup = service.createLigneEcritureFormGroup(sampleWithNewData);

        const ligneEcriture = service.getLigneEcriture(formGroup) as any;

        expect(ligneEcriture).toMatchObject(sampleWithNewData);
      });

      it('should return NewLigneEcriture for empty LigneEcriture initial value', () => {
        const formGroup = service.createLigneEcritureFormGroup();

        const ligneEcriture = service.getLigneEcriture(formGroup) as any;

        expect(ligneEcriture).toMatchObject({});
      });

      it('should return ILigneEcriture', () => {
        const formGroup = service.createLigneEcritureFormGroup(sampleWithRequiredData);

        const ligneEcriture = service.getLigneEcriture(formGroup) as any;

        expect(ligneEcriture).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ILigneEcriture should not enable id FormControl', () => {
        const formGroup = service.createLigneEcritureFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewLigneEcriture should disable id FormControl', () => {
        const formGroup = service.createLigneEcritureFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
