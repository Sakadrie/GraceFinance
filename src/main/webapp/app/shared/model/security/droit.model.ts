import { IProfil } from 'app/shared/model/security/profil.model';

export interface IDroit {
  id: number;
  nom?: string | null;
  code?: string | null;
  description?: string | null;
  profils?: Pick<IProfil, 'id'>[] | null;
}

export type NewDroit = Omit<IDroit, 'id'> & { id: null };
