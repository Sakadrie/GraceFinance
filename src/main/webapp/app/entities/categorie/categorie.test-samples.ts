import { ICategorie, NewCategorie } from '../../shared/model/referentiel/categorie.model';

export const sampleWithRequiredData: ICategorie = {
  id: 24162,
  nom: 'groin groin à moins de',
  code: 'croâ membre à vie avex',
  typeCategorie: 'concurrence',
  actif: true,
};

export const sampleWithPartialData: ICategorie = {
  id: 2648,
  nom: "à l'exception de",
  code: 'afin de',
  typeCategorie: 'extatique rapide',
  actif: false,
};

export const sampleWithFullData: ICategorie = {
  id: 19936,
  nom: 'parvenir',
  code: 'délégation de peur que arrêter',
  typeCategorie: 'super pacifique',
  description: 'parlementaire',
  actif: false,
};

export const sampleWithNewData: NewCategorie = {
  nom: 'maigre jeune enfant',
  code: 'afin que ah',
  typeCategorie: 'sale areu areu',
  actif: true,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
