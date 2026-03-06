import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IAffectationUtilisateur, NewAffectationUtilisateur } from '../../../shared/model/security/affectation-utilisateur.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAffectationUtilisateur for edit and NewAffectationUtilisateurFormGroupInput for create.
 */
type AffectationUtilisateurFormGroupInput = IAffectationUtilisateur | PartialWithRequiredKeyOf<NewAffectationUtilisateur>;

type AffectationUtilisateurFormDefaults = Pick<NewAffectationUtilisateur, 'id' | 'actif' | 'profils'>;

type AffectationUtilisateurFormGroupContent = {
  id: FormControl<IAffectationUtilisateur['id'] | NewAffectationUtilisateur['id']>;
  actif: FormControl<IAffectationUtilisateur['actif']>;
  dateAffectation: FormControl<IAffectationUtilisateur['dateAffectation']>;
  user: FormControl<IAffectationUtilisateur['user']>;
  entiteFinanciere: FormControl<IAffectationUtilisateur['entiteFinanciere']>;
  profils: FormControl<IAffectationUtilisateur['profils']>;
};

export type AffectationUtilisateurFormGroup = FormGroup<AffectationUtilisateurFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AffectationUtilisateurFormService {
  createAffectationUtilisateurFormGroup(
    affectationUtilisateur: AffectationUtilisateurFormGroupInput = { id: null },
  ): AffectationUtilisateurFormGroup {
    const affectationUtilisateurRawValue = {
      ...this.getFormDefaults(),
      ...affectationUtilisateur,
    };
    return new FormGroup<AffectationUtilisateurFormGroupContent>({
      id: new FormControl(
        { value: affectationUtilisateurRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      actif: new FormControl(affectationUtilisateurRawValue.actif, {
        validators: [Validators.required],
      }),
      dateAffectation: new FormControl(affectationUtilisateurRawValue.dateAffectation, {
        validators: [Validators.required],
      }),
      user: new FormControl(affectationUtilisateurRawValue.user, {
        validators: [Validators.required],
      }),
      entiteFinanciere: new FormControl(affectationUtilisateurRawValue.entiteFinanciere, {
        validators: [Validators.required],
      }),
      profils: new FormControl(affectationUtilisateurRawValue.profils ?? []),
    });
  }

  getAffectationUtilisateur(form: AffectationUtilisateurFormGroup): IAffectationUtilisateur | NewAffectationUtilisateur {
    return form.getRawValue() as IAffectationUtilisateur | NewAffectationUtilisateur;
  }

  resetForm(form: AffectationUtilisateurFormGroup, affectationUtilisateur: AffectationUtilisateurFormGroupInput): void {
    const affectationUtilisateurRawValue = { ...this.getFormDefaults(), ...affectationUtilisateur };
    form.reset(
      {
        ...affectationUtilisateurRawValue,
        id: { value: affectationUtilisateurRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AffectationUtilisateurFormDefaults {
    return {
      id: null,
      actif: false,
      profils: [],
    };
  }
}
