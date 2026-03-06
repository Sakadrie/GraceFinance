import { IEntiteFinanciere } from 'app/shared/model/principal/entite-financiere.model';

export interface ICaisse {
  id: number;
  nom?: string | null;
  code?: string | null;
  type?: string | null;
  devise?: string | null;
  solde?: number | null;
  actif?: boolean | null;
  entiteFinanciere?: Pick<IEntiteFinanciere, 'id'> | null;
}

export type NewCaisse = Omit<ICaisse, 'id'> & { id: null };
