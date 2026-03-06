import dayjs from 'dayjs/esm';
import { IEntiteFinanciere } from 'app/shared/model/principal/entite-financiere.model';
import { ICaisse } from 'app/shared/model/principal/caisse.model';

export interface ITransfert {
  id: number;
  code?: string | null;
  dateTransfert?: dayjs.Dayjs | null;
  montant?: number | null;
  motif?: string | null;
  typeTransfert?: string | null;
  statut?: string | null;
  validerPar?: string | null;
  dateValidation?: dayjs.Dayjs | null;
  entiteFinanciereSource?: Pick<IEntiteFinanciere, 'id'> | null;
  caisseSource?: Pick<ICaisse, 'id'> | null;
  caisseDestination?: Pick<ICaisse, 'id'> | null;
}

export type NewTransfert = Omit<ITransfert, 'id'> & { id: null };
