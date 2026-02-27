import dayjs from 'dayjs/esm';

import { IDepense, NewDepense } from './depense.model';

export const sampleWithRequiredData: IDepense = {
  id: 29021,
  code: 'diététiste',
  dateDepense: dayjs('2026-02-26'),
  montant: 14186.32,
  motif: 'débile assurément quand',
  statut: 'étant donné que longtemps tellement',
};

export const sampleWithPartialData: IDepense = {
  id: 4760,
  code: 'chut vaste tant',
  dateDepense: dayjs('2026-02-25'),
  montant: 7169.24,
  motif: 'commissionnaire réserver badaboum',
  referencePiece: 'jusqu’à ce que lorsque gâcher',
  statut: 'acheter a',
  validerPar: 'ha ha',
};

export const sampleWithFullData: IDepense = {
  id: 2254,
  code: 'situer',
  dateDepense: dayjs('2026-02-25'),
  montant: 8304.42,
  motif: 'corps enseignant présidence',
  referencePiece: 'de façon à',
  statut: 'sans',
  validerPar: 'tellement quand',
  dateValidation: dayjs('2026-02-25T17:02'),
};

export const sampleWithNewData: NewDepense = {
  code: 'hors repentir',
  dateDepense: dayjs('2026-02-26'),
  montant: 31355.17,
  motif: 'à peine grimper',
  statut: 'attribuer de la part de',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
