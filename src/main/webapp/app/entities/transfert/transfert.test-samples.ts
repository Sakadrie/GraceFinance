import dayjs from 'dayjs/esm';

import { ITransfert, NewTransfert } from '../../shared/model/referentiel/transfert.model';

export const sampleWithRequiredData: ITransfert = {
  id: 7543,
  code: 'échanger cuicui',
  dateTransfert: dayjs('2026-02-26'),
  montant: 6343.28,
  typeTransfert: 'près de',
  statut: 'autant loin marier',
};

export const sampleWithPartialData: ITransfert = {
  id: 4998,
  code: 'cadre administration',
  dateTransfert: dayjs('2026-02-26'),
  montant: 16395.93,
  typeTransfert: 'là',
  statut: 'téléphoner concurrence',
};

export const sampleWithFullData: ITransfert = {
  id: 13521,
  code: 'mal gens gestionnaire',
  dateTransfert: dayjs('2026-02-26'),
  montant: 7774.35,
  motif: 'raser dring',
  typeTransfert: 'ainsi volontiers vide',
  statut: 'secours multiple au cas où',
  validerPar: 'dès que égoïste super',
  dateValidation: dayjs('2026-02-25T17:52'),
};

export const sampleWithNewData: NewTransfert = {
  code: 'insipide zzzz prestataire de services',
  dateTransfert: dayjs('2026-02-26'),
  montant: 23516.01,
  typeTransfert: 'dense fade',
  statut: 'accomplir',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
