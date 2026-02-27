import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../droit.test-samples';

import { DroitFormService } from './droit-form.service';

describe('Droit Form Service', () => {
  let service: DroitFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DroitFormService);
  });

  describe('Service methods', () => {
    describe('createDroitFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDroitFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            code: expect.any(Object),
            description: expect.any(Object),
            profils: expect.any(Object),
          }),
        );
      });

      it('passing IDroit should create a new form with FormGroup', () => {
        const formGroup = service.createDroitFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            code: expect.any(Object),
            description: expect.any(Object),
            profils: expect.any(Object),
          }),
        );
      });
    });

    describe('getDroit', () => {
      it('should return NewDroit for default Droit initial value', () => {
        const formGroup = service.createDroitFormGroup(sampleWithNewData);

        const droit = service.getDroit(formGroup) as any;

        expect(droit).toMatchObject(sampleWithNewData);
      });

      it('should return NewDroit for empty Droit initial value', () => {
        const formGroup = service.createDroitFormGroup();

        const droit = service.getDroit(formGroup) as any;

        expect(droit).toMatchObject({});
      });

      it('should return IDroit', () => {
        const formGroup = service.createDroitFormGroup(sampleWithRequiredData);

        const droit = service.getDroit(formGroup) as any;

        expect(droit).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDroit should not enable id FormControl', () => {
        const formGroup = service.createDroitFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDroit should disable id FormControl', () => {
        const formGroup = service.createDroitFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
