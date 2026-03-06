export interface ICompteComptable {
  id: number;
  code?: string | null;
  libelle?: string | null;
  classe?: number | null;
}

export type NewCompteComptable = Omit<ICompteComptable, 'id'> & { id: null };
