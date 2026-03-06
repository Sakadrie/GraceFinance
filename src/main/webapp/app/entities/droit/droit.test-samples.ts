import { IDroit, NewDroit } from '../../shared/model/security/droit.model';

export const sampleWithRequiredData: IDroit = {
  id: 24080,
  nom: 'collègue prestataire de services ha ha',
  code: 'dring même si sombre',
};

export const sampleWithPartialData: IDroit = {
  id: 30797,
  nom: 'procurer',
  code: 'entre-temps oups si',
  description: 'groin groin',
};

export const sampleWithFullData: IDroit = {
  id: 15047,
  nom: "adversaire d'avec",
  code: 'mouiller bon cadre',
  description: 'par rapport à snif',
};

export const sampleWithNewData: NewDroit = {
  nom: 'grrr amuser',
  code: 'tâter',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
