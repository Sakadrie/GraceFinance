export interface TableElement {
  [key: string]: string | number;
}

export interface ExportedColumnData {
  key: string;
  label: string;
  type: 'string' | 'number' | 'date' | 'datetime' | 'boolean';
}
