import { IEcritureComptable } from 'app/shared/model/principal/ecriture-comptable.model';
import { ICompteComptable } from 'app/shared/model/principal/compte-comptable.model';

export interface ILigneEcriture {
  id: number;
  montant?: number | null;
  sens?: string | null;
  libelle?: string | null;
  ecriture?: Pick<IEcritureComptable, 'id'> | null;
  compte?: Pick<ICompteComptable, 'id'> | null;
}

export type NewLigneEcriture = Omit<ILigneEcriture, 'id'> & { id: null };
