import dayjs from 'dayjs/esm';
import { IEntiteFinanciere } from 'app/entities/entite-financiere/entite-financiere.model';
import { ICaisse } from 'app/entities/caisse/caisse.model';
import { ICategorie } from 'app/entities/categorie/categorie.model';

export interface IDepense {
  id: number;
  code?: string | null;
  dateDepense?: dayjs.Dayjs | null;
  montant?: number | null;
  motif?: string | null;
  referencePiece?: string | null;
  statut?: string | null;
  validerPar?: string | null;
  dateValidation?: dayjs.Dayjs | null;
  entiteFinanciere?: Pick<IEntiteFinanciere, 'id'> | null;
  caisse?: Pick<ICaisse, 'id'> | null;
  categorie?: Pick<ICategorie, 'id'> | null;
}

export type NewDepense = Omit<IDepense, 'id'> & { id: null };
