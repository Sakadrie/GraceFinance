import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDepense, NewDepense } from '../../../shared/model/principal/depense.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDepense for edit and NewDepenseFormGroupInput for create.
 */
type DepenseFormGroupInput = IDepense | PartialWithRequiredKeyOf<NewDepense>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IDepense | NewDepense> = Omit<T, 'dateValidation'> & {
  dateValidation?: string | null;
};

type DepenseFormRawValue = FormValueOf<IDepense>;

type NewDepenseFormRawValue = FormValueOf<NewDepense>;

type DepenseFormDefaults = Pick<NewDepense, 'id' | 'dateValidation'>;

type DepenseFormGroupContent = {
  id: FormControl<DepenseFormRawValue['id'] | NewDepense['id']>;
  code: FormControl<DepenseFormRawValue['code']>;
  dateDepense: FormControl<DepenseFormRawValue['dateDepense']>;
  montant: FormControl<DepenseFormRawValue['montant']>;
  motif: FormControl<DepenseFormRawValue['motif']>;
  referencePiece: FormControl<DepenseFormRawValue['referencePiece']>;
  statut: FormControl<DepenseFormRawValue['statut']>;
  validerPar: FormControl<DepenseFormRawValue['validerPar']>;
  dateValidation: FormControl<DepenseFormRawValue['dateValidation']>;
  entiteFinanciere: FormControl<DepenseFormRawValue['entiteFinanciere']>;
  caisse: FormControl<DepenseFormRawValue['caisse']>;
  categorie: FormControl<DepenseFormRawValue['categorie']>;
};

export type DepenseFormGroup = FormGroup<DepenseFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DepenseFormService {
  createDepenseFormGroup(depense: DepenseFormGroupInput = { id: null }): DepenseFormGroup {
    const depenseRawValue = this.convertDepenseToDepenseRawValue({
      ...this.getFormDefaults(),
      ...depense,
    });
    return new FormGroup<DepenseFormGroupContent>({
      id: new FormControl(
        { value: depenseRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      code: new FormControl(depenseRawValue.code, {
        validators: [Validators.required],
      }),
      dateDepense: new FormControl(depenseRawValue.dateDepense, {
        validators: [Validators.required],
      }),
      montant: new FormControl(depenseRawValue.montant, {
        validators: [Validators.required],
      }),
      motif: new FormControl(depenseRawValue.motif, {
        validators: [Validators.required],
      }),
      referencePiece: new FormControl(depenseRawValue.referencePiece),
      statut: new FormControl(depenseRawValue.statut, {
        validators: [Validators.required],
      }),
      validerPar: new FormControl(depenseRawValue.validerPar),
      dateValidation: new FormControl(depenseRawValue.dateValidation),
      entiteFinanciere: new FormControl(depenseRawValue.entiteFinanciere, {
        validators: [Validators.required],
      }),
      caisse: new FormControl(depenseRawValue.caisse, {
        validators: [Validators.required],
      }),
      categorie: new FormControl(depenseRawValue.categorie, {
        validators: [Validators.required],
      }),
    });
  }

  getDepense(form: DepenseFormGroup): IDepense | NewDepense {
    return this.convertDepenseRawValueToDepense(form.getRawValue() as DepenseFormRawValue | NewDepenseFormRawValue);
  }

  resetForm(form: DepenseFormGroup, depense: DepenseFormGroupInput): void {
    const depenseRawValue = this.convertDepenseToDepenseRawValue({ ...this.getFormDefaults(), ...depense });
    form.reset(
      {
        ...depenseRawValue,
        id: { value: depenseRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): DepenseFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dateValidation: currentTime,
    };
  }

  private convertDepenseRawValueToDepense(rawDepense: DepenseFormRawValue | NewDepenseFormRawValue): IDepense | NewDepense {
    return {
      ...rawDepense,
      dateValidation: dayjs(rawDepense.dateValidation, DATE_TIME_FORMAT),
    };
  }

  private convertDepenseToDepenseRawValue(
    depense: IDepense | (Partial<NewDepense> & DepenseFormDefaults),
  ): DepenseFormRawValue | PartialWithRequiredKeyOf<NewDepenseFormRawValue> {
    return {
      ...depense,
      dateValidation: depense.dateValidation ? depense.dateValidation.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
