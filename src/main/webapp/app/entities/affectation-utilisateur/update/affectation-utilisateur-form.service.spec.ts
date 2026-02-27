import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../affectation-utilisateur.test-samples';

import { AffectationUtilisateurFormService } from './affectation-utilisateur-form.service';

describe('AffectationUtilisateur Form Service', () => {
  let service: AffectationUtilisateurFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AffectationUtilisateurFormService);
  });

  describe('Service methods', () => {
    describe('createAffectationUtilisateurFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAffectationUtilisateurFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            actif: expect.any(Object),
            dateAffectation: expect.any(Object),
            user: expect.any(Object),
            entiteFinanciere: expect.any(Object),
            profils: expect.any(Object),
          }),
        );
      });

      it('passing IAffectationUtilisateur should create a new form with FormGroup', () => {
        const formGroup = service.createAffectationUtilisateurFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            actif: expect.any(Object),
            dateAffectation: expect.any(Object),
            user: expect.any(Object),
            entiteFinanciere: expect.any(Object),
            profils: expect.any(Object),
          }),
        );
      });
    });

    describe('getAffectationUtilisateur', () => {
      it('should return NewAffectationUtilisateur for default AffectationUtilisateur initial value', () => {
        const formGroup = service.createAffectationUtilisateurFormGroup(sampleWithNewData);

        const affectationUtilisateur = service.getAffectationUtilisateur(formGroup) as any;

        expect(affectationUtilisateur).toMatchObject(sampleWithNewData);
      });

      it('should return NewAffectationUtilisateur for empty AffectationUtilisateur initial value', () => {
        const formGroup = service.createAffectationUtilisateurFormGroup();

        const affectationUtilisateur = service.getAffectationUtilisateur(formGroup) as any;

        expect(affectationUtilisateur).toMatchObject({});
      });

      it('should return IAffectationUtilisateur', () => {
        const formGroup = service.createAffectationUtilisateurFormGroup(sampleWithRequiredData);

        const affectationUtilisateur = service.getAffectationUtilisateur(formGroup) as any;

        expect(affectationUtilisateur).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAffectationUtilisateur should not enable id FormControl', () => {
        const formGroup = service.createAffectationUtilisateurFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAffectationUtilisateur should disable id FormControl', () => {
        const formGroup = service.createAffectationUtilisateurFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
