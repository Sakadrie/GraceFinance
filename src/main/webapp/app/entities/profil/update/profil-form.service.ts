import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IProfil, NewProfil } from '../profil.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProfil for edit and NewProfilFormGroupInput for create.
 */
type ProfilFormGroupInput = IProfil | PartialWithRequiredKeyOf<NewProfil>;

type ProfilFormDefaults = Pick<NewProfil, 'id' | 'droits' | 'affectations'>;

type ProfilFormGroupContent = {
  id: FormControl<IProfil['id'] | NewProfil['id']>;
  nom: FormControl<IProfil['nom']>;
  code: FormControl<IProfil['code']>;
  description: FormControl<IProfil['description']>;
  droits: FormControl<IProfil['droits']>;
  affectations: FormControl<IProfil['affectations']>;
};

export type ProfilFormGroup = FormGroup<ProfilFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProfilFormService {
  createProfilFormGroup(profil: ProfilFormGroupInput = { id: null }): ProfilFormGroup {
    const profilRawValue = {
      ...this.getFormDefaults(),
      ...profil,
    };
    return new FormGroup<ProfilFormGroupContent>({
      id: new FormControl(
        { value: profilRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nom: new FormControl(profilRawValue.nom, {
        validators: [Validators.required],
      }),
      code: new FormControl(profilRawValue.code, {
        validators: [Validators.required],
      }),
      description: new FormControl(profilRawValue.description),
      droits: new FormControl(profilRawValue.droits ?? []),
      affectations: new FormControl(profilRawValue.affectations ?? []),
    });
  }

  getProfil(form: ProfilFormGroup): IProfil | NewProfil {
    return form.getRawValue() as IProfil | NewProfil;
  }

  resetForm(form: ProfilFormGroup, profil: ProfilFormGroupInput): void {
    const profilRawValue = { ...this.getFormDefaults(), ...profil };
    form.reset(
      {
        ...profilRawValue,
        id: { value: profilRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ProfilFormDefaults {
    return {
      id: null,
      droits: [],
      affectations: [],
    };
  }
}
