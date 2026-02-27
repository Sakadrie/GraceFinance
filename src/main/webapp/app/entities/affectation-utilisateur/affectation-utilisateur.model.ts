import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';
import { IEntiteFinanciere } from 'app/entities/entite-financiere/entite-financiere.model';
import { IProfil } from 'app/entities/profil/profil.model';

export interface IAffectationUtilisateur {
  id: number;
  actif?: boolean | null;
  dateAffectation?: dayjs.Dayjs | null;
  user?: Pick<IUser, 'id'> | null;
  entiteFinanciere?: Pick<IEntiteFinanciere, 'id'> | null;
  profils?: Pick<IProfil, 'id'>[] | null;
}

export type NewAffectationUtilisateur = Omit<IAffectationUtilisateur, 'id'> & { id: null };
