import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IEcritureComptable, NewEcritureComptable } from '../ecriture-comptable.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEcritureComptable for edit and NewEcritureComptableFormGroupInput for create.
 */
type EcritureComptableFormGroupInput = IEcritureComptable | PartialWithRequiredKeyOf<NewEcritureComptable>;

type EcritureComptableFormDefaults = Pick<NewEcritureComptable, 'id'>;

type EcritureComptableFormGroupContent = {
  id: FormControl<IEcritureComptable['id'] | NewEcritureComptable['id']>;
  dateComptable: FormControl<IEcritureComptable['dateComptable']>;
  numeroPiece: FormControl<IEcritureComptable['numeroPiece']>;
  libelle: FormControl<IEcritureComptable['libelle']>;
  referenceExterne: FormControl<IEcritureComptable['referenceExterne']>;
};

export type EcritureComptableFormGroup = FormGroup<EcritureComptableFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EcritureComptableFormService {
  createEcritureComptableFormGroup(ecritureComptable: EcritureComptableFormGroupInput = { id: null }): EcritureComptableFormGroup {
    const ecritureComptableRawValue = {
      ...this.getFormDefaults(),
      ...ecritureComptable,
    };
    return new FormGroup<EcritureComptableFormGroupContent>({
      id: new FormControl(
        { value: ecritureComptableRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      dateComptable: new FormControl(ecritureComptableRawValue.dateComptable, {
        validators: [Validators.required],
      }),
      numeroPiece: new FormControl(ecritureComptableRawValue.numeroPiece, {
        validators: [Validators.required],
      }),
      libelle: new FormControl(ecritureComptableRawValue.libelle),
      referenceExterne: new FormControl(ecritureComptableRawValue.referenceExterne),
    });
  }

  getEcritureComptable(form: EcritureComptableFormGroup): IEcritureComptable | NewEcritureComptable {
    return form.getRawValue() as IEcritureComptable | NewEcritureComptable;
  }

  resetForm(form: EcritureComptableFormGroup, ecritureComptable: EcritureComptableFormGroupInput): void {
    const ecritureComptableRawValue = { ...this.getFormDefaults(), ...ecritureComptable };
    form.reset(
      {
        ...ecritureComptableRawValue,
        id: { value: ecritureComptableRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EcritureComptableFormDefaults {
    return {
      id: null,
    };
  }
}
