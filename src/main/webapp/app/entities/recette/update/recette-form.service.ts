import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IRecette, NewRecette } from '../recette.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRecette for edit and NewRecetteFormGroupInput for create.
 */
type RecetteFormGroupInput = IRecette | PartialWithRequiredKeyOf<NewRecette>;

type RecetteFormDefaults = Pick<NewRecette, 'id' | 'anonyme'>;

type RecetteFormGroupContent = {
  id: FormControl<IRecette['id'] | NewRecette['id']>;
  code: FormControl<IRecette['code']>;
  dateRecette: FormControl<IRecette['dateRecette']>;
  montant: FormControl<IRecette['montant']>;
  typeRecette: FormControl<IRecette['typeRecette']>;
  anonyme: FormControl<IRecette['anonyme']>;
  membreNom: FormControl<IRecette['membreNom']>;
  motif: FormControl<IRecette['motif']>;
  referencePiece: FormControl<IRecette['referencePiece']>;
  statut: FormControl<IRecette['statut']>;
  entiteFinanciere: FormControl<IRecette['entiteFinanciere']>;
  caisse: FormControl<IRecette['caisse']>;
  categorie: FormControl<IRecette['categorie']>;
};

export type RecetteFormGroup = FormGroup<RecetteFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RecetteFormService {
  createRecetteFormGroup(recette: RecetteFormGroupInput = { id: null }): RecetteFormGroup {
    const recetteRawValue = {
      ...this.getFormDefaults(),
      ...recette,
    };
    return new FormGroup<RecetteFormGroupContent>({
      id: new FormControl(
        { value: recetteRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      code: new FormControl(recetteRawValue.code, {
        validators: [Validators.required],
      }),
      dateRecette: new FormControl(recetteRawValue.dateRecette, {
        validators: [Validators.required],
      }),
      montant: new FormControl(recetteRawValue.montant, {
        validators: [Validators.required],
      }),
      typeRecette: new FormControl(recetteRawValue.typeRecette, {
        validators: [Validators.required],
      }),
      anonyme: new FormControl(recetteRawValue.anonyme, {
        validators: [Validators.required],
      }),
      membreNom: new FormControl(recetteRawValue.membreNom),
      motif: new FormControl(recetteRawValue.motif),
      referencePiece: new FormControl(recetteRawValue.referencePiece),
      statut: new FormControl(recetteRawValue.statut, {
        validators: [Validators.required],
      }),
      entiteFinanciere: new FormControl(recetteRawValue.entiteFinanciere, {
        validators: [Validators.required],
      }),
      caisse: new FormControl(recetteRawValue.caisse, {
        validators: [Validators.required],
      }),
      categorie: new FormControl(recetteRawValue.categorie, {
        validators: [Validators.required],
      }),
    });
  }

  getRecette(form: RecetteFormGroup): IRecette | NewRecette {
    return form.getRawValue() as IRecette | NewRecette;
  }

  resetForm(form: RecetteFormGroup, recette: RecetteFormGroupInput): void {
    const recetteRawValue = { ...this.getFormDefaults(), ...recette };
    form.reset(
      {
        ...recetteRawValue,
        id: { value: recetteRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): RecetteFormDefaults {
    return {
      id: null,
      anonyme: false,
    };
  }
}
