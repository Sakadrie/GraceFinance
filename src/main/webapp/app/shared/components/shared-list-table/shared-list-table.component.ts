import { NgClass, NgFor, NgIf, NgSwitch, NgSwitchCase, NgSwitchDefault, NgTemplateOutlet } from '@angular/common';
import { Component, Input, Output, EventEmitter } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbDropdownModule, NgbPagination, NgbTooltip } from '@ng-bootstrap/ng-bootstrap';
import { TranslatePipe } from '@ngx-translate/core';
// import { JsonRepairService } from 'app/core/util/json-repair.service';
import HasAnyAuthorityDirective from 'app/shared/auth/has-any-authority.directive';
import { FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { TranslateDirective } from 'app/shared/language';
import { ItemCountComponent } from 'app/shared/pagination';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';
import { ClickToCopyDirective } from 'app/shared/directive/click-to-copy.directive';
import { CurrencyPipe } from 'app/shared/pipe/currency.pipe';
import { PercentPipe } from 'app/shared/pipe/percent.pipe';
import { NumberDividerPipe } from 'app/shared/pipe/number-divider.pipe';
import FormatBytesPipe from 'app/shared/pipe/format-bytes.pipe';
import { JoinPropertyHtmlPipe } from 'app/shared/pipe/join-property-html.pipe';
import PrettyJsonPipe from 'app/shared/pipe/pretty-json.pipe';

export interface SelectableRow extends Record<string, any> {
  isSelected?: boolean;
}

export interface SharedListTableColumn {
  rowPredicate?: (row: Record<string, any> | undefined) => boolean; // Fonction pour filtrer/afficher la colonne selon la ligne
  key: string;
  label: string;
  sortable?: boolean;
  sortKey?: string; // Si différent de key
  order: number;
  cellStyle?: any;
  cellClass?: string;
  cellTemplate?: any;
  isNotInTableRow?: boolean; // Indique si la colonne doit apparaître dans la ligne du tableau
  isInDetail?: boolean; // Indique si la colonne doit apparaître dans le dépliant
  type?:
    | 'text'
    | 'number'
    | 'floatNumber'
    | 'date'
    | 'datetime'
    | 'boolean'
    | 'currency'
    | 'percent'
    | 'bytes'
    | 'download'
    | 'table'
    | 'joinProperty'
    | 'json'
    | 'wysiwyg'; // Type de données pour le formatage, 'table' permet d'afficher un tableau imbriqué
  imbeddedTableColumns?: SharedListTableColumn[]; // Colonnes à afficher si type = 'table'
  subObject?: string;
  subSubObject?: string;
  isWithColumnDataTranslate?: boolean; // Si true, la valeur de la cellule est traduite
  isWithClickToCopy?: boolean; // Si true, un clic sur la cellule copie sa valeur dans le presse-papier
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
  joinPropertyMaxItemsDepliant?: number; // Nombre maximum d'items à afficher si type = 'joinProperty' (défaut: tous) dans le dépliant
  joinPropertyOverflowIndicatorDepliant?: string; // Indicateur à afficher si dépassement du nombre max d'items si type = 'joinProperty' (défaut: '…') dans le dépliant
  joinPropertyOverflowClassesDepliant?: string | string[]; // Classes CSS à appliquer à l'indicateur de dépassement si type = 'joinProperty' dans le dépliant
  joinPropertyShowMoreCountDepliant?: boolean; // Si true, affiche le nombre d'items cachés dans l'indicateur de dépassement si type = 'joinProperty' dans le dépliant
}

export interface SharedListTableAction {
  id: string;
  label: string;
  isLabel?: boolean; // Si true affiche le label dans le bouton
  icon?: string;
  color?: string;
  type?: 'button' | 'dropdown';
  authorities?: string[];
  actionPredicate?: (row: Record<string, any> | undefined) => boolean; // Fonction pour filtrer/afficher l'action selon la ligne
}

export interface SharedListTableInput {
  isNotWithInitialSearch?: boolean; // Si true, aucune donnée n'est affichée tant qu'une recherche n'a pas été effectuée
  isWithCheckboxes?: boolean; // Si true, une colonne de checkbox est ajoutée pour la sélection des lignes
  data: SelectableRow[];
  columns: SharedListTableColumn[];
  styleActions?: 'button' | 'dropdown';
  displayDetailsDepliant?: boolean; // Si false, le dépliant n'est pas utilisé
  actions?: SharedListTableAction[];
  actionsColClasses?: string; // Classes CSS à appliquer à la colonne des actions
  striped?: boolean;
  hover?: boolean;
  responsive?: boolean;
  additionalDivClass?: string;
  colorClass?: string;
  tableBorderClass?: string;
  tableHeadColorClass?: string;
  additionalTableClass?: string;
  rowDetailTemplate?: any;
  thousandSeparator?: '.' | ','; // Séparateur des milliers pour le pipe numberDivider (défaut: '.')
  decimalSeparator?: '.' | ','; // Séparateur décimal pour le pipe numberDivider (défaut: ',')
  decimalDigits?: number; // Nombre de chiffres après la virgule pour le pipe numberDivider (défaut: 2)
  // Pagination côté parent/serveur
  hidePagination?: boolean; // Si true, la pagination n'est pas affichée
  totalItems: number;
  itemsPerPage: number;
  page: number;
  pageSizes?: number[];
}

@Component({
  selector: 'jhi-shared-list-table',
  templateUrl: './shared-list-table.component.html',
  imports: [
    NgClass,
    NgIf,
    NgFor,
    NgTemplateOutlet,
    FormsModule,
    NgbPagination,
    ItemCountComponent,
    NgbDropdownModule,
    TranslateDirective,
    NgSwitch,
    NgSwitchCase,
    NgSwitchDefault,
    FormatMediumDatePipe,
    FormatMediumDatetimePipe,
    CurrencyPipe,
    PercentPipe,
    NumberDividerPipe,
    HasAnyAuthorityDirective,
    TranslatePipe,
    FormatBytesPipe,
    NgbTooltip,
    ClickToCopyDirective,
    JoinPropertyHtmlPipe,
    PrettyJsonPipe,
  ],
  styleUrls: ['./shared-list-table.component.scss'],
})
export class SharedListTableComponent {
  @Input() config!: SharedListTableInput;

  @Output() action = new EventEmitter<{ actionId: string; row: any }>();
  @Output() sort = new EventEmitter<{ columnKey: string; sortEventId?: string }>();
  @Output() pageChange = new EventEmitter<{ page: number; pageSize: number }>();

  expandedRows = new Set<number>();

  constructor(
    // private jsonRepairService: JsonRepairService,
    private sanitizer: DomSanitizer,
  ) {}

  getSanitizedHtml(html: string): SafeHtml {
    return this.sanitizer.bypassSecurityTrustHtml(html);
  }

  /**
   * Handler pour le (change) du checkbox en-tête (Angular ne supporte pas le cast dans le template)
   */
  onToggleAllRows(event: Event): void {
    const checked = (event.target && (event.target as HTMLInputElement).checked) ?? false;
    this.toggleAllRows(checked);
  }

  /**
   * Retourne true si toutes les lignes paginées sont sélectionnées
   */
  get allRowsSelected(): boolean {
    return this.pagedData.length > 0 && this.pagedData.every(row => !!row.isSelected);
  }

  /**
   * Retourne toutes les lignes sélectionnées (toutes pages)
   */
  get allSelectedRows(): SelectableRow[] {
    return this.pagedData.filter(row => !!row.isSelected);
  }

  /**
   * Retourne true si au moins une ligne est sélectionnée mais pas toutes
   */
  get someRowsSelected(): boolean {
    return this.pagedData.some(row => !!row.isSelected) && !this.allRowsSelected;
  }

  /**
   * Sélectionne/désélectionne toutes les lignes paginées
   */
  toggleAllRows(checked: boolean): void {
    this.pagedData.forEach(row => (row.isSelected = checked));
  }

  get sortedColumns(): SharedListTableColumn[] {
    return this.config.columns
      .slice()
      .filter(col => col.type !== 'table')
      .sort((a, b) => a.order - b.order);
  }

  get sortedFilterColumns(): SharedListTableColumn[] {
    return this.config.columns
      .slice()
      .filter(col => !col.isNotInTableRow)
      .filter(col => col.type !== 'table')
      .sort((a, b) => a.order - b.order);
  }

  get tableColumns(): SharedListTableColumn[] {
    return this.config.columns
      .slice()
      .filter(col => col.isInDetail)
      .filter(col => col.type === 'table')
      .sort((a, b) => a.order - b.order);
  }

  /**
   * Indique s'il y a des colonnes à afficher dans le dépliant par défaut
   */
  get hasDetailColumns(): boolean {
    // Le dépliant n'est disponible que si displayDetailsDepliant n'est pas false
    if (this.config.displayDetailsDepliant === false) {
      return false;
    }
    return this.sortedColumns.some((col: SharedListTableColumn) => !!col.isInDetail);
  }

  get hasTableColumns(): boolean {
    // Le dépliant n'est disponible que si displayDetailsDepliant n'est pas false
    if (this.config.displayDetailsDepliant === false) {
      return false;
    }
    return this.tableColumns.length > 0;
  }

  get page(): number {
    return this.config.page;
  }

  get pageSize(): number {
    return this.config.itemsPerPage;
  }

  get totalItems(): number {
    return this.config.totalItems;
  }

  get pageSizes(): number[] {
    return this.config.pageSizes ?? [5, 10, 20, 50];
  }

  get pagedData(): SelectableRow[] {
    // Les données sont déjà paginées côté parent/serveur
    return Array.isArray(this.config.data) ? this.config.data : [];
  }

  /**
   * Retourne un tableau de paires de colonnes à afficher dans le détail (2 par ligne)
   */
  getDetailColumnPairs(columns: any[]): any[][] {
    const detailCols = columns.filter(col => !!col.isInDetail);
    const pairs: any[][] = [];
    for (let i = 0; i < detailCols.length; i += 2) {
      pairs.push([detailCols[i], detailCols[i + 1]]);
    }
    return pairs;
  }

  onAction(actionId: string, row: any): void {
    this.action.emit({ actionId, row });
  }

  onSort(col: SharedListTableColumn): void {
    if (col.sortable) {
      this.sort.emit({ columnKey: col.sortKey ?? col.key });
    }
  }

  onPageChange(newPage: number | undefined): void {
    if (typeof newPage === 'number' && newPage > 0) {
      this.pageChange.emit({ page: newPage, pageSize: this.pageSize });
    }
  }

  onPageSizeChange(newSize: number | string | undefined): void {
    const size = typeof newSize === 'string' ? parseInt(newSize, 10) : newSize;
    if (typeof size === 'number' && size > 0) {
      this.pageChange.emit({ page: 1, pageSize: size });
    }
  }

  toggleRowDetail(idx: number): void {
    if (this.expandedRows.has(idx)) {
      this.expandedRows.delete(idx);
    } else {
      this.expandedRows.add(idx);
    }
  }

  /**
   * Convertit une chaîne contenant un tableau d'objets JSON en un tableau de Record<string, any>
   * @param jsonString La chaîne à parser
   * @returns Un tableau de Record<string, any>
   */
  // public parseJsonArrayString(jsonString: string): Record<string, any>[] {
  //   try {
  //     const parsed = this.jsonRepairService.parseJsonArrayFix(jsonString);
  //     if (Array.isArray(parsed)) {
  //       return parsed as Record<string, any>[];
  //     }
  //     return [];
  //   } catch {
  //     return [];
  //   }
  // }

  public checkIfIsArrayOfObjects(value: any): boolean {
    return Array.isArray(value) && value.every(item => typeof item === 'object' && item !== null);
  }
}
