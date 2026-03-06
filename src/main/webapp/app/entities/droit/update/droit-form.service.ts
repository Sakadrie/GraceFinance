import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IDroit, NewDroit } from '../../../shared/model/security/droit.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDroit for edit and NewDroitFormGroupInput for create.
 */
type DroitFormGroupInput = IDroit | PartialWithRequiredKeyOf<NewDroit>;

type DroitFormDefaults = Pick<NewDroit, 'id' | 'profils'>;

type DroitFormGroupContent = {
  id: FormControl<IDroit['id'] | NewDroit['id']>;
  nom: FormControl<IDroit['nom']>;
  code: FormControl<IDroit['code']>;
  description: FormControl<IDroit['description']>;
  profils: FormControl<IDroit['profils']>;
};

export type DroitFormGroup = FormGroup<DroitFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DroitFormService {
  createDroitFormGroup(droit: DroitFormGroupInput = { id: null }): DroitFormGroup {
    const droitRawValue = {
      ...this.getFormDefaults(),
      ...droit,
    };
    return new FormGroup<DroitFormGroupContent>({
      id: new FormControl(
        { value: droitRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nom: new FormControl(droitRawValue.nom, {
        validators: [Validators.required],
      }),
      code: new FormControl(droitRawValue.code, {
        validators: [Validators.required],
      }),
      description: new FormControl(droitRawValue.description),
      profils: new FormControl(droitRawValue.profils ?? []),
    });
  }

  getDroit(form: DroitFormGroup): IDroit | NewDroit {
    return form.getRawValue() as IDroit | NewDroit;
  }

  resetForm(form: DroitFormGroup, droit: DroitFormGroupInput): void {
    const droitRawValue = { ...this.getFormDefaults(), ...droit };
    form.reset(
      {
        ...droitRawValue,
        id: { value: droitRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): DroitFormDefaults {
    return {
      id: null,
      profils: [],
    };
  }
}
