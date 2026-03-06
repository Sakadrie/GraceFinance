import dayjs from 'dayjs/esm';

import { IRecette, NewRecette } from '../../shared/model/principal/recette.model';

export const sampleWithRequiredData: IRecette = {
  id: 4656,
  code: 'de varier dans la mesure où',
  dateRecette: dayjs('2026-02-25'),
  montant: 14799.49,
  typeRecette: 'précisément',
  anonyme: true,
  statut: 'en outre de',
};

export const sampleWithPartialData: IRecette = {
  id: 14334,
  code: 'en outre de',
  dateRecette: dayjs('2026-02-25'),
  montant: 10458.09,
  typeRecette: 'par',
  anonyme: false,
  membreNom: 'délégation',
  statut: 'bien que ha ha au dépens de',
};

export const sampleWithFullData: IRecette = {
  id: 1257,
  code: 'de manière à ce que hypocrite rudement',
  dateRecette: dayjs('2026-02-25'),
  montant: 6461.9,
  typeRecette: 'porte-parole',
  anonyme: true,
  membreNom: 'échapper via',
  motif: 'aimable',
  referencePiece: 'chef de cuisine chercher',
  statut: 'snob que',
};

export const sampleWithNewData: NewRecette = {
  code: 'large gigantesque',
  dateRecette: dayjs('2026-02-25'),
  montant: 26346.35,
  typeRecette: 'renverser si bien que deçà',
  anonyme: true,
  statut: 'efficace sentir sombre',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
