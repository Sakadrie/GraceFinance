import { ICompteComptable, NewCompteComptable } from '../../shared/model/principal/compte-comptable.model';

export const sampleWithRequiredData: ICompteComptable = {
  id: 15720,
  code: 'drôlement hebdomadaire tellement',
  libelle: 'équipe de recherche',
  classe: 4772,
};

export const sampleWithPartialData: ICompteComptable = {
  id: 28525,
  code: 'boum',
  libelle: 'divinement atchoum',
  classe: 13884,
};

export const sampleWithFullData: ICompteComptable = {
  id: 4740,
  code: 'sauvegarder malade',
  libelle: 'jamais dehors',
  classe: 25901,
};

export const sampleWithNewData: NewCompteComptable = {
  code: 'en pourpre volontiers',
  libelle: 'par actionnaire',
  classe: 10172,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
