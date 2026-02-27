import { IEntiteFinanciere, NewEntiteFinanciere } from './entite-financiere.model';

export const sampleWithRequiredData: IEntiteFinanciere = {
  id: 30243,
  nom: 'splendide commissionnaire',
  code: 'au-dedans de broum crac',
  type: 'croâ',
  actif: false,
};

export const sampleWithPartialData: IEntiteFinanciere = {
  id: 6778,
  nom: 'assez tant multiple',
  code: 'loin aussitôt que',
  type: 'partenaire fréquenter marron',
  description: "doucement à l'encontre de",
  actif: true,
};

export const sampleWithFullData: IEntiteFinanciere = {
  id: 25059,
  nom: 'vaste aux environs de pour',
  code: 'chialer',
  type: 'quoique assurément rompre',
  description: 'triathlète tsoin-tsoin présidence',
  actif: false,
};

export const sampleWithNewData: NewEntiteFinanciere = {
  nom: 'de manière à hors de',
  code: 'aux environs de accorder',
  type: 'ha avant que',
  actif: true,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
