import dayjs from 'dayjs/esm';

export interface IEcritureComptable {
  id: number;
  dateComptable?: dayjs.Dayjs | null;
  numeroPiece?: string | null;
  libelle?: string | null;
  referenceExterne?: string | null;
}

export type NewEcritureComptable = Omit<IEcritureComptable, 'id'> & { id: null };
