import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ILigneEcriture, NewLigneEcriture } from '../ligne-ecriture.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILigneEcriture for edit and NewLigneEcritureFormGroupInput for create.
 */
type LigneEcritureFormGroupInput = ILigneEcriture | PartialWithRequiredKeyOf<NewLigneEcriture>;

type LigneEcritureFormDefaults = Pick<NewLigneEcriture, 'id'>;

type LigneEcritureFormGroupContent = {
  id: FormControl<ILigneEcriture['id'] | NewLigneEcriture['id']>;
  montant: FormControl<ILigneEcriture['montant']>;
  sens: FormControl<ILigneEcriture['sens']>;
  libelle: FormControl<ILigneEcriture['libelle']>;
  ecriture: FormControl<ILigneEcriture['ecriture']>;
  compte: FormControl<ILigneEcriture['compte']>;
};

export type LigneEcritureFormGroup = FormGroup<LigneEcritureFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LigneEcritureFormService {
  createLigneEcritureFormGroup(ligneEcriture: LigneEcritureFormGroupInput = { id: null }): LigneEcritureFormGroup {
    const ligneEcritureRawValue = {
      ...this.getFormDefaults(),
      ...ligneEcriture,
    };
    return new FormGroup<LigneEcritureFormGroupContent>({
      id: new FormControl(
        { value: ligneEcritureRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      montant: new FormControl(ligneEcritureRawValue.montant, {
        validators: [Validators.required],
      }),
      sens: new FormControl(ligneEcritureRawValue.sens, {
        validators: [Validators.required],
      }),
      libelle: new FormControl(ligneEcritureRawValue.libelle),
      ecriture: new FormControl(ligneEcritureRawValue.ecriture, {
        validators: [Validators.required],
      }),
      compte: new FormControl(ligneEcritureRawValue.compte, {
        validators: [Validators.required],
      }),
    });
  }

  getLigneEcriture(form: LigneEcritureFormGroup): ILigneEcriture | NewLigneEcriture {
    return form.getRawValue() as ILigneEcriture | NewLigneEcriture;
  }

  resetForm(form: LigneEcritureFormGroup, ligneEcriture: LigneEcritureFormGroupInput): void {
    const ligneEcritureRawValue = { ...this.getFormDefaults(), ...ligneEcriture };
    form.reset(
      {
        ...ligneEcritureRawValue,
        id: { value: ligneEcritureRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): LigneEcritureFormDefaults {
    return {
      id: null,
    };
  }
}
