export interface IEntiteFinanciere {
  id: number;
  nom?: string | null;
  code?: string | null;
  type?: string | null;
  description?: string | null;
  actif?: boolean | null;
  egliseLiees?: Pick<IEntiteFinanciere, 'id'>[] | null;
  structureLiees?: Pick<IEntiteFinanciere, 'id'>[] | null;
}

export type NewEntiteFinanciere = Omit<IEntiteFinanciere, 'id'> & { id: null };
