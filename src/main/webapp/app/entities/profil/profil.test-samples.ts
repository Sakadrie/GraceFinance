import { IProfil, NewProfil } from './profil.model';

export const sampleWithRequiredData: IProfil = {
  id: 21388,
  nom: 'psitt',
  code: 'population du Québec atchoum',
};

export const sampleWithPartialData: IProfil = {
  id: 4798,
  nom: 'zzzz',
  code: 'conseil municipal population du Québec',
};

export const sampleWithFullData: IProfil = {
  id: 31533,
  nom: 'psitt',
  code: 'après que',
  description: 'grâce à admettre imaginer',
};

export const sampleWithNewData: NewProfil = {
  nom: 'coin-coin coac coac',
  code: 'ronron',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
