import dayjs from 'dayjs/esm';
import { IEntiteFinanciere } from 'app/shared/model/principal/entite-financiere.model';
import { ICaisse } from 'app/shared/model/principal/caisse.model';
import { ICategorie } from 'app/shared/model/referentiel/categorie.model';

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
