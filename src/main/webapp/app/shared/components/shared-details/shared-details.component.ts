import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';

import { DomSanitizer, SafeHtml } from '@angular/platform-browser';

export interface SharedDetailsColumn {
  order: number;
  key: string;
  label: string;
  type:
    | 'text'
    | 'joinProperty'
    | 'date'
    | 'datetime'
    | 'number'
    | 'floatNumber'
    | 'boolean'
    | 'currency'
    | 'percent'
    | 'bytes'
    | 'download'
    | 'json'
    | 'wysiwyg';
  isNotShow?: boolean; // Indique si la ligne doit être affichée ou non
  isWithColumnDataTranslate?: boolean; // Indique si la donnée est à traduire (utile pour les données de constantes)
  isWithClickToCopy?: boolean; // Indique si la donnée est cliquable pour être copiée dans le presse-papier
  joinPropertyName?: string; // Propriété à concaténer si type = 'joinProperty' ; obligatoire si type = 'joinProperty'
  joinPropertySeparator?: string; // Séparateur à utiliser si type = 'joinProperty' (défaut: ', ')
  joinPropertyListProperty?: string; // Propriété de l'objet source contenant la liste si type = 'joinProperty' (défaut: 'list')
  joinPropertySortOrder?: 'asc' | 'desc'; // Ordre de tri des valeurs concaténées si type = 'joinProperty' (défaut: pas de tri)
  joinPropertySortPath?: string; // Chemin de la propriété à utiliser pour le tri si type = 'joinProperty' (défaut: même que joinPropertyName)
  joinPropertySpanClasses?: string | string[]; // Classes CSS à appliquer aux <span> des éléments concaténés si type = 'joinProperty'
  joinPropertySeparatorClasses?: string | string[]; // Classes CSS à appliquer aux <span> du séparateur si type = 'joinProperty'
  joinPropertyMaxItems?: number; // Nombre maximum d'items à afficher si type = 'joinProperty' (défaut: tous)
  joinPropertyOverflowIndicator?: string; // Indicateur à afficher si dépassement du nombre max d'items si type = 'joinProperty' (défaut: '…')
  joinPropertyOverflowClasses?: string | string[]; // Classes CSS à appliquer à l'indicateur de dépassement si type = 'joinProperty'
  joinPropertyShowMoreCount?: boolean; // Si true, affiche le nombre d'items cachés dans l'indicateur de dépassement si type = 'joinProperty'
}

@Component({
  selector: 'jhi-shared-details',
  standalone: true,
  templateUrl: './shared-details.component.html',
  styleUrls: ['./shared-details.component.scss'],
  // imports: [CommonModule, TranslatePipe, NgbTooltip, ClickToCopyDirective, JoinPropertyHtmlPipe, PrettyJsonPipe],
})
export class SharedDetailsComponent implements OnInit {
  @Input() config: {
    data: any;
    columns: SharedDetailsColumn[];
    itemsPerRowXs: number;
    itemsPerRowSm: number;
    itemsPerRowMd: number;
    itemsPerRowLg: number;
    thousandSeparator?: '.' | ',';
    decimalSeparator?: '.' | ',';
    decimalDigits?: number;
  } = {
    data: null,
    columns: [],
    itemsPerRowXs: 1,
    itemsPerRowSm: 2,
    itemsPerRowMd: 3,
    itemsPerRowLg: 4,
    thousandSeparator: '.',
    decimalSeparator: ',',
    decimalDigits: 2,
  };
  @Output() back = new EventEmitter<void>();

  listeRowsXs: SharedDetailsColumn[][] = [];
  listeRowsSm: SharedDetailsColumn[][] = [];
  listeRowsMd: SharedDetailsColumn[][] = [];
  listeRowsLg: SharedDetailsColumn[][] = [];

  constructor(private sanitizer: DomSanitizer) {}

  ngOnInit(): void {
    this.listeRowsXs = this.rowsXs();
    this.listeRowsSm = this.rowsSm();
    this.listeRowsMd = this.rowsMd();
    this.listeRowsLg = this.rowsLg();
  }

  getSanitizedHtml(html: string): SafeHtml {
    return this.sanitizer.bypassSecurityTrustHtml(html);
  }

  rowsXs(): SharedDetailsColumn[][] {
    const rows: SharedDetailsColumn[][] = [];
    for (let i = 0; i < this.config.columns.filter(col => !col.isNotShow).length; i += this.config.itemsPerRowXs) {
      rows.push(this.config.columns.filter(col => !col.isNotShow).slice(i, i + this.config.itemsPerRowXs));
    }
    return rows;
  }

  rowsSm(): SharedDetailsColumn[][] {
    const rows: SharedDetailsColumn[][] = [];
    for (let i = 0; i < this.config.columns.filter(col => !col.isNotShow).length; i += this.config.itemsPerRowSm) {
      rows.push(this.config.columns.filter(col => !col.isNotShow).slice(i, i + this.config.itemsPerRowSm));
    }
    return rows;
  }

  rowsMd(): SharedDetailsColumn[][] {
    const rows: SharedDetailsColumn[][] = [];
    for (let i = 0; i < this.config.columns.filter(col => !col.isNotShow).length; i += this.config.itemsPerRowMd) {
      rows.push(this.config.columns.filter(col => !col.isNotShow).slice(i, i + this.config.itemsPerRowMd));
    }
    return rows;
  }

  rowsLg(): SharedDetailsColumn[][] {
    const rows: SharedDetailsColumn[][] = [];
    for (let i = 0; i < this.config.columns.filter(col => !col.isNotShow).length; i += this.config.itemsPerRowLg) {
      rows.push(this.config.columns.filter(col => !col.isNotShow).slice(i, i + this.config.itemsPerRowLg));
    }
    return rows;
  }

  formatValue(col: SharedDetailsColumn): any {
    const value = this.config.data?.[col.key];
    switch (col.type) {
      case 'date':
        return value ? FormatMediumDatePipe.prototype.transform.call(null, value) : '';
      case 'datetime':
        return value ? FormatMediumDatetimePipe.prototype.transform.call(null, value) : '';
      case 'boolean':
        return value ? 'Oui' : 'Non';
      case 'number':
      // return value
      //   ? NumberDividerPipe.prototype.transform.call(null, value, this.config.thousandSeparator, this.config.decimalSeparator, 0)
      //   : '';
      case 'floatNumber':
      // return value
      //   ? NumberDividerPipe.prototype.transform.call(
      //       null,
      //       value,
      //       this.config.thousandSeparator,
      //       this.config.decimalSeparator,
      //       this.config.decimalDigits,
      //     )
      //   : '';
      case 'currency':
      // return value ? CurrencyPipe.prototype.transform.call(null, value) : '';
      case 'percent':
      // return value ? PercentPipe.prototype.transform.call(null, value) : '';
      case 'bytes':
      // return value ? FormatBytesPipe.prototype.transform.call(null, value) : '';
      default:
        return value;
    }
  }
}
