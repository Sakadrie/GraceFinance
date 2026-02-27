import { ICaisse, NewCaisse } from './caisse.model';

export const sampleWithRequiredData: ICaisse = {
  id: 11058,
  nom: 'toc-toc',
  code: 'équipe de recherche au dépens de hôte',
  type: 'avant que',
  devise: 'soupçonner même si debout',
  solde: 22053.85,
  actif: false,
};

export const sampleWithPartialData: ICaisse = {
  id: 23081,
  nom: 'ficher',
  code: 'patientèle à défaut de cependant',
  type: 'alors que tant que',
  devise: 'accomplir',
  solde: 13208.19,
  actif: true,
};

export const sampleWithFullData: ICaisse = {
  id: 8892,
  nom: 'dense blablabla',
  code: 'novice',
  type: 'pendant que assez',
  devise: 'ah',
  solde: 29710.54,
  actif: true,
};

export const sampleWithNewData: NewCaisse = {
  nom: 'guide',
  code: 'tâcher dans la mesure où auparavant',
  type: 'moyennant',
  devise: 'pschitt extatique',
  solde: 32224.24,
  actif: false,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
