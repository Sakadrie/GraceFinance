import { IDroit } from 'app/entities/droit/droit.model';
import { IAffectationUtilisateur } from 'app/entities/affectation-utilisateur/affectation-utilisateur.model';

export interface IProfil {
  id: number;
  nom?: string | null;
  code?: string | null;
  description?: string | null;
  droits?: Pick<IDroit, 'id'>[] | null;
  affectations?: Pick<IAffectationUtilisateur, 'id'>[] | null;
}

export type NewProfil = Omit<IProfil, 'id'> & { id: null };
