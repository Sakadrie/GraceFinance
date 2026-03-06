import dayjs from 'dayjs/esm';
import { IEntiteFinanciere } from 'app/shared/model/principal/entite-financiere.model';
import { ICaisse } from 'app/shared/model/principal/caisse.model';
import { ICategorie } from 'app/shared/model/referentiel/categorie.model';

export interface IRecette {
  id: number;
  code?: string | null;
  dateRecette?: dayjs.Dayjs | null;
  montant?: number | null;
  typeRecette?: string | null;
  anonyme?: boolean | null;
  membreNom?: string | null;
  motif?: string | null;
  referencePiece?: string | null;
  statut?: string | null;
  entiteFinanciere?: Pick<IEntiteFinanciere, 'id'> | null;
  caisse?: Pick<ICaisse, 'id'> | null;
  categorie?: Pick<ICategorie, 'id'> | null;
}

export type NewRecette = Omit<IRecette, 'id'> & { id: null };
