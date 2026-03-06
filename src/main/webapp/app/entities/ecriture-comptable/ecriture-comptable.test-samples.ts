import dayjs from 'dayjs/esm';

import { IEcritureComptable, NewEcritureComptable } from '../../shared/model/principal/ecriture-comptable.model';

export const sampleWithRequiredData: IEcritureComptable = {
  id: 22443,
  dateComptable: dayjs('2026-02-25'),
  numeroPiece: 'simplifier racheter extra',
};

export const sampleWithPartialData: IEcritureComptable = {
  id: 14024,
  dateComptable: dayjs('2026-02-25'),
  numeroPiece: 'soit porter main-d’œuvre',
  libelle: 'désormais conseiller porte-parole',
  referenceExterne: 'immense',
};

export const sampleWithFullData: IEcritureComptable = {
  id: 12040,
  dateComptable: dayjs('2026-02-26'),
  numeroPiece: 'considérable après que',
  libelle: 'concurrence vétuste membre de l’équipe',
  referenceExterne: 'bè plic',
};

export const sampleWithNewData: NewEcritureComptable = {
  dateComptable: dayjs('2026-02-25'),
  numeroPiece: 'obéir zzzz',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
