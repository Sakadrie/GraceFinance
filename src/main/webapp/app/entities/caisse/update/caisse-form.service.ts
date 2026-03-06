import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ICaisse, NewCaisse } from '../../../shared/model/principal/caisse.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICaisse for edit and NewCaisseFormGroupInput for create.
 */
type CaisseFormGroupInput = ICaisse | PartialWithRequiredKeyOf<NewCaisse>;

type CaisseFormDefaults = Pick<NewCaisse, 'id' | 'actif'>;

type CaisseFormGroupContent = {
  id: FormControl<ICaisse['id'] | NewCaisse['id']>;
  nom: FormControl<ICaisse['nom']>;
  code: FormControl<ICaisse['code']>;
  type: FormControl<ICaisse['type']>;
  devise: FormControl<ICaisse['devise']>;
  solde: FormControl<ICaisse['solde']>;
  actif: FormControl<ICaisse['actif']>;
  entiteFinanciere: FormControl<ICaisse['entiteFinanciere']>;
};

export type CaisseFormGroup = FormGroup<CaisseFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CaisseFormService {
  createCaisseFormGroup(caisse: CaisseFormGroupInput = { id: null }): CaisseFormGroup {
    const caisseRawValue = {
      ...this.getFormDefaults(),
      ...caisse,
    };
    return new FormGroup<CaisseFormGroupContent>({
      id: new FormControl(
        { value: caisseRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nom: new FormControl(caisseRawValue.nom, {
        validators: [Validators.required],
      }),
      code: new FormControl(caisseRawValue.code, {
        validators: [Validators.required],
      }),
      type: new FormControl(caisseRawValue.type, {
        validators: [Validators.required],
      }),
      devise: new FormControl(caisseRawValue.devise, {
        validators: [Validators.required],
      }),
      solde: new FormControl(caisseRawValue.solde, {
        validators: [Validators.required],
      }),
      actif: new FormControl(caisseRawValue.actif, {
        validators: [Validators.required],
      }),
      entiteFinanciere: new FormControl(caisseRawValue.entiteFinanciere, {
        validators: [Validators.required],
      }),
    });
  }

  getCaisse(form: CaisseFormGroup): ICaisse | NewCaisse {
    return form.getRawValue() as ICaisse | NewCaisse;
  }

  resetForm(form: CaisseFormGroup, caisse: CaisseFormGroupInput): void {
    const caisseRawValue = { ...this.getFormDefaults(), ...caisse };
    form.reset(
      {
        ...caisseRawValue,
        id: { value: caisseRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CaisseFormDefaults {
    return {
      id: null,
      actif: false,
    };
  }
}
