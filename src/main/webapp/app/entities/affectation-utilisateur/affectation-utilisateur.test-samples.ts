import dayjs from 'dayjs/esm';

import { IAffectationUtilisateur, NewAffectationUtilisateur } from './affectation-utilisateur.model';

export const sampleWithRequiredData: IAffectationUtilisateur = {
  id: 4789,
  actif: true,
  dateAffectation: dayjs('2026-02-26'),
};

export const sampleWithPartialData: IAffectationUtilisateur = {
  id: 30465,
  actif: true,
  dateAffectation: dayjs('2026-02-25'),
};

export const sampleWithFullData: IAffectationUtilisateur = {
  id: 23219,
  actif: true,
  dateAffectation: dayjs('2026-02-26'),
};

export const sampleWithNewData: NewAffectationUtilisateur = {
  actif: false,
  dateAffectation: dayjs('2026-02-26'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
