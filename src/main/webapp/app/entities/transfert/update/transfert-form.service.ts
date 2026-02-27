import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITransfert, NewTransfert } from '../transfert.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITransfert for edit and NewTransfertFormGroupInput for create.
 */
type TransfertFormGroupInput = ITransfert | PartialWithRequiredKeyOf<NewTransfert>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ITransfert | NewTransfert> = Omit<T, 'dateValidation'> & {
  dateValidation?: string | null;
};

type TransfertFormRawValue = FormValueOf<ITransfert>;

type NewTransfertFormRawValue = FormValueOf<NewTransfert>;

type TransfertFormDefaults = Pick<NewTransfert, 'id' | 'dateValidation'>;

type TransfertFormGroupContent = {
  id: FormControl<TransfertFormRawValue['id'] | NewTransfert['id']>;
  code: FormControl<TransfertFormRawValue['code']>;
  dateTransfert: FormControl<TransfertFormRawValue['dateTransfert']>;
  montant: FormControl<TransfertFormRawValue['montant']>;
  motif: FormControl<TransfertFormRawValue['motif']>;
  typeTransfert: FormControl<TransfertFormRawValue['typeTransfert']>;
  statut: FormControl<TransfertFormRawValue['statut']>;
  validerPar: FormControl<TransfertFormRawValue['validerPar']>;
  dateValidation: FormControl<TransfertFormRawValue['dateValidation']>;
  entiteFinanciereSource: FormControl<TransfertFormRawValue['entiteFinanciereSource']>;
  caisseSource: FormControl<TransfertFormRawValue['caisseSource']>;
  caisseDestination: FormControl<TransfertFormRawValue['caisseDestination']>;
};

export type TransfertFormGroup = FormGroup<TransfertFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TransfertFormService {
  createTransfertFormGroup(transfert: TransfertFormGroupInput = { id: null }): TransfertFormGroup {
    const transfertRawValue = this.convertTransfertToTransfertRawValue({
      ...this.getFormDefaults(),
      ...transfert,
    });
    return new FormGroup<TransfertFormGroupContent>({
      id: new FormControl(
        { value: transfertRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      code: new FormControl(transfertRawValue.code, {
        validators: [Validators.required],
      }),
      dateTransfert: new FormControl(transfertRawValue.dateTransfert, {
        validators: [Validators.required],
      }),
      montant: new FormControl(transfertRawValue.montant, {
        validators: [Validators.required],
      }),
      motif: new FormControl(transfertRawValue.motif),
      typeTransfert: new FormControl(transfertRawValue.typeTransfert, {
        validators: [Validators.required],
      }),
      statut: new FormControl(transfertRawValue.statut, {
        validators: [Validators.required],
      }),
      validerPar: new FormControl(transfertRawValue.validerPar),
      dateValidation: new FormControl(transfertRawValue.dateValidation),
      entiteFinanciereSource: new FormControl(transfertRawValue.entiteFinanciereSource, {
        validators: [Validators.required],
      }),
      caisseSource: new FormControl(transfertRawValue.caisseSource, {
        validators: [Validators.required],
      }),
      caisseDestination: new FormControl(transfertRawValue.caisseDestination, {
        validators: [Validators.required],
      }),
    });
  }

  getTransfert(form: TransfertFormGroup): ITransfert | NewTransfert {
    return this.convertTransfertRawValueToTransfert(form.getRawValue() as TransfertFormRawValue | NewTransfertFormRawValue);
  }

  resetForm(form: TransfertFormGroup, transfert: TransfertFormGroupInput): void {
    const transfertRawValue = this.convertTransfertToTransfertRawValue({ ...this.getFormDefaults(), ...transfert });
    form.reset(
      {
        ...transfertRawValue,
        id: { value: transfertRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): TransfertFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dateValidation: currentTime,
    };
  }

  private convertTransfertRawValueToTransfert(rawTransfert: TransfertFormRawValue | NewTransfertFormRawValue): ITransfert | NewTransfert {
    return {
      ...rawTransfert,
      dateValidation: dayjs(rawTransfert.dateValidation, DATE_TIME_FORMAT),
    };
  }

  private convertTransfertToTransfertRawValue(
    transfert: ITransfert | (Partial<NewTransfert> & TransfertFormDefaults),
  ): TransfertFormRawValue | PartialWithRequiredKeyOf<NewTransfertFormRawValue> {
    return {
      ...transfert,
      dateValidation: transfert.dateValidation ? transfert.dateValidation.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
