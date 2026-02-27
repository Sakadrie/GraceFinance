import { IEntiteFinanciere } from 'app/entities/entite-financiere/entite-financiere.model';

export interface ICategorie {
  id: number;
  nom?: string | null;
  code?: string | null;
  typeCategorie?: string | null;
  description?: string | null;
  actif?: boolean | null;
  entiteFinanciere?: Pick<IEntiteFinanciere, 'id'> | null;
}

export type NewCategorie = Omit<ICategorie, 'id'> & { id: null };
