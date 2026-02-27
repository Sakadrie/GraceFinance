import { ILigneEcriture, NewLigneEcriture } from './ligne-ecriture.model';

export const sampleWithRequiredData: ILigneEcriture = {
  id: 17702,
  montant: 31471.85,
  sens: 'buter commissionnaire porte-parole',
};

export const sampleWithPartialData: ILigneEcriture = {
  id: 26491,
  montant: 21669.39,
  sens: 'hé',
};

export const sampleWithFullData: ILigneEcriture = {
  id: 8193,
  montant: 20384.53,
  sens: 'au-dessus de via si bien que',
  libelle: 'corps enseignant',
};

export const sampleWithNewData: NewLigneEcriture = {
  montant: 20992.03,
  sens: 'commander loin de loufoque',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
