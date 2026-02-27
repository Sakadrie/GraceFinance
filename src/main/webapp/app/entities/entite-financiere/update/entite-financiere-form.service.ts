import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IEntiteFinanciere, NewEntiteFinanciere } from '../entite-financiere.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEntiteFinanciere for edit and NewEntiteFinanciereFormGroupInput for create.
 */
type EntiteFinanciereFormGroupInput = IEntiteFinanciere | PartialWithRequiredKeyOf<NewEntiteFinanciere>;

type EntiteFinanciereFormDefaults = Pick<NewEntiteFinanciere, 'id' | 'actif' | 'egliseLiees' | 'structureLiees'>;

type EntiteFinanciereFormGroupContent = {
  id: FormControl<IEntiteFinanciere['id'] | NewEntiteFinanciere['id']>;
  nom: FormControl<IEntiteFinanciere['nom']>;
  code: FormControl<IEntiteFinanciere['code']>;
  type: FormControl<IEntiteFinanciere['type']>;
  description: FormControl<IEntiteFinanciere['description']>;
  actif: FormControl<IEntiteFinanciere['actif']>;
  egliseLiees: FormControl<IEntiteFinanciere['egliseLiees']>;
  structureLiees: FormControl<IEntiteFinanciere['structureLiees']>;
};

export type EntiteFinanciereFormGroup = FormGroup<EntiteFinanciereFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EntiteFinanciereFormService {
  createEntiteFinanciereFormGroup(entiteFinanciere: EntiteFinanciereFormGroupInput = { id: null }): EntiteFinanciereFormGroup {
    const entiteFinanciereRawValue = {
      ...this.getFormDefaults(),
      ...entiteFinanciere,
    };
    return new FormGroup<EntiteFinanciereFormGroupContent>({
      id: new FormControl(
        { value: entiteFinanciereRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nom: new FormControl(entiteFinanciereRawValue.nom, {
        validators: [Validators.required],
      }),
      code: new FormControl(entiteFinanciereRawValue.code, {
        validators: [Validators.required],
      }),
      type: new FormControl(entiteFinanciereRawValue.type, {
        validators: [Validators.required],
      }),
      description: new FormControl(entiteFinanciereRawValue.description),
      actif: new FormControl(entiteFinanciereRawValue.actif, {
        validators: [Validators.required],
      }),
      egliseLiees: new FormControl(entiteFinanciereRawValue.egliseLiees ?? []),
      structureLiees: new FormControl(entiteFinanciereRawValue.structureLiees ?? []),
    });
  }

  getEntiteFinanciere(form: EntiteFinanciereFormGroup): IEntiteFinanciere | NewEntiteFinanciere {
    return form.getRawValue() as IEntiteFinanciere | NewEntiteFinanciere;
  }

  resetForm(form: EntiteFinanciereFormGroup, entiteFinanciere: EntiteFinanciereFormGroupInput): void {
    const entiteFinanciereRawValue = { ...this.getFormDefaults(), ...entiteFinanciere };
    form.reset(
      {
        ...entiteFinanciereRawValue,
        id: { value: entiteFinanciereRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EntiteFinanciereFormDefaults {
    return {
      id: null,
      actif: false,
      egliseLiees: [],
      structureLiees: [],
    };
  }
}
