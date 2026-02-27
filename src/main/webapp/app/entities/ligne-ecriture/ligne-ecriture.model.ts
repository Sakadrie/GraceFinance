import { IEcritureComptable } from 'app/entities/ecriture-comptable/ecriture-comptable.model';
import { ICompteComptable } from 'app/entities/compte-comptable/compte-comptable.model';

export interface ILigneEcriture {
  id: number;
  montant?: number | null;
  sens?: string | null;
  libelle?: string | null;
  ecriture?: Pick<IEcritureComptable, 'id'> | null;
  compte?: Pick<ICompteComptable, 'id'> | null;
}

export type NewLigneEcriture = Omit<ILigneEcriture, 'id'> & { id: null };
