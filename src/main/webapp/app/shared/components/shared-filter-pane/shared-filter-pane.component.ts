import { NgIf, NgFor, NgClass } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { TranslateDirective, TranslatePipe } from '@ngx-translate/core';

export interface SharedFilterInputOption {
  label: string;
  value: any;
  code?: string;
  labelFr?: string;
  labelEn?: string;
  labelCn?: string;
}

export interface SharedFilterInputConfig {
  hide?: boolean; // Pour masquer l'input
  id: string;
  index: number;
  type: 'text' | 'number' | 'floatNumber' | 'date' | 'datetime' | 'dateRange' | 'select' | 'select2' | 'checkbox' | 'switch' | 'radio';
  label: string;
  placeholder?: string;
  filterProperty?: string;
  filterExpression?:
    | 'equals'
    | 'contains'
    | 'specified'
    | 'in'
    | 'notIn'
    | 'greaterThan'
    | 'lessThan'
    | 'greaterThanOrEqual'
    | 'lessThanOrEqual'
    | 'notEquals';
  options?: SharedFilterInputOption[]; // Pour les selects
  value?: any;
  validations?: {
    required?: boolean;
    minLength?: number;
    maxLength?: number;
    min?: number;
    max?: number;
    pattern?: string;
    patternExample?: string;
  };
  mask?: string;
  thousandSeparator?: '.' | ',';
  decimalMarker?: '.' | ',';
  decimalLimit?: number;
  bindLabel?: string;
  bindValue?: string;
  groupByKey?: string;
  searchable?: boolean;
  searchWhileTyping?: boolean;
  searchFunction?: (term: string, item: SharedFilterInputOption) => boolean;
  colClasses?: string;
  isWithOptionsLabelTranslation?: boolean; // Si true, les labels des options sont traduits
}

export interface SharedFilterPaneConfig {
  inputs: SharedFilterInputConfig[];
  title?: string;
  localStorageKey?: string;
  isNotWithInitialSearch?: boolean; // Si true, aucune donnée n'est affichée tant qu'une recherche n'a pas été effectuée
  isWithDownloadAllButton?: boolean; // Si true, affiche un bouton pour télécharger toutes les données
  isHideResetButton?: boolean; // Si true, n'affiche pas le bouton de réinitialisation
  isHideFilterButton?: boolean; // Si true, n'affiche pas le bouton de filtre
  buttonsLocation?: 'top' | 'bottom' | 'both'; // Emplacement des boutons (top : en haut, bottom : en bas, both : en haut et en bas)
}

@Component({
  selector: 'jhi-shared-filter-pane',
  templateUrl: './shared-filter-pane.component.html',
  styleUrl: './shared-filter-pane.component.scss',
  standalone: true,
  imports: [
    // NgIf,
    // FormsModule,
    // NgFor,
    // OrderByIndexPipe,
    // TranslateDirective,
    // MatFormField,
    // MatLabel,
    // MatOption,
    // MatSelect,
    // TranslatePipe,
    // NgClass,
    // NgxMaskDirective,
    // NgSelectModule,
    // DynamicLabelDirective,
  ],
  // providers: [provideNgxMask(), DynamicLabelDirective],
})
export class SharedFilterPaneComponent implements OnInit {
  @Input() show = true;
  @Input() withValidationStyleAndInfo = false;
  @Input() config!: SharedFilterPaneConfig;
  @Output() filterOutput = new EventEmitter<Record<string, any>>();
  @Output() resetOutput = new EventEmitter<void>();
  @Output() downloadAllOutput = new EventEmitter<Record<string, any>>();

  filterValues: Record<string, any> = {};
  filterErrors: Record<string, string> = {};
  filterErrorsTranslatedValues: Record<string, any> = {};

  constructor() // private globalService: GlobalService
  {}

  ngOnInit(): void {
    // this.initializeFilterFromLocalStorage();
  }

  /**
   * Initialise uniquement les nouveaux inputs ajoutés à config.inputs après ngOnInit
   */
  initializeInputs(onlyNew?: boolean): void {
    if (this.config.inputs.length) {
      this.config.inputs.forEach(input => {
        if (onlyNew) {
          if (!(input.id in this.filterValues)) {
            this.filterValues[input.id] = input.value ?? (input.type === 'dateRange' ? { from: undefined, to: undefined } : undefined);
            this.filterErrors[input.id] = '';
          }
        } else {
          this.filterValues[input.id] = input.value ?? (input.type === 'dateRange' ? { from: undefined, to: undefined } : undefined);
          this.filterErrors[input.id] = '';
        }
      });
    }
  }

  updateInputOptions(id: string, options: SharedFilterInputOption[]): void {
    const input = this.config.inputs.find(i => i.id === id);
    if (input) {
      input.options = options;
    }
  }

  onInputChange(id: string, value: any): void {
    this.filterValues[id] = value;
  }

  onInputSelect2Change(input: { id: string; bindValue: string }, value: any): void {
    if (value) {
      this.filterValues[input.id] = value[input.bindValue];
    } else {
      this.filterValues[input.id] = null;
    }
    this.validateInput(input.id);
  }

  onDateRangeChange(id: string, field: 'from' | 'to', value: any): void {
    this.filterValues[id] ??= { from: '', to: '' };
    this.filterValues[id][field] = value;
  }

  validateInput(id: string): void {
    const input = this.config.inputs.find(i => i.id === id);
    if (!input) {
      return;
    }
    const value = this.filterValues[id];
    let error = '';
    let errorTranslatedValues = {};
    if (input.validations?.required) {
      if (input.type === 'dateRange') {
        if (!value?.from || !value?.to) {
          error = 'main.validations.required';
        }
      } else if (value === '' || value === null || value === undefined) {
        error = 'main.validations.required';
      }
    }
    if (
      !error &&
      input.validations?.minLength &&
      typeof value === 'string' &&
      value.length > 0 &&
      value.length < input.validations.minLength
    ) {
      error = `main.validations.minLength`;
      errorTranslatedValues = { minLength: input.validations.minLength };
    }
    if (!error && input.validations?.maxLength && typeof value === 'string' && value.length > input.validations.maxLength) {
      error = `main.validations.maxLength`;
      errorTranslatedValues = { maxLength: input.validations.maxLength };
    }
    if (!error && input.validations?.min !== undefined && typeof value === 'number' && value && value < input.validations.min) {
      error = `main.validations.min`;
      errorTranslatedValues = { min: input.validations.min };
    }
    if (!error && input.validations?.max !== undefined && typeof value === 'number' && value > input.validations.max) {
      error = `main.validations.max`;
      errorTranslatedValues = { max: input.validations.max };
    }
    if (!error && input.validations?.pattern && typeof value === 'string') {
      const regex = new RegExp(input.validations.pattern);
      if (!regex.test(value)) {
        if (input.validations.patternExample) {
          error = 'main.validations.patternWithExample';
          errorTranslatedValues = { patternExample: input.validations.patternExample };
        } else {
          error = 'main.validations.pattern';
        }
      }
    }
    this.filterErrors[id] = error;
    this.filterErrorsTranslatedValues[id] = errorTranslatedValues;
  }

  onFilter(): void {
    let hasError = false;
    this.config.inputs.forEach(input => {
      this.validateInput(input.id);
      if (this.filterErrors[input.id]) {
        hasError = true;
      }
    });
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-condition
    if (hasError) {
      return;
    }
    if (this.config.localStorageKey) {
      // this.globalService.storeFiltersInLocalStorage(this.config.localStorageKey, this.filterValues);
    }
    this.filterOutput.emit({ ...this.filterValues });
  }

  onReset(): void {
    this.config.inputs.forEach(input => {
      input.value = undefined;
      this.filterValues[input.id] = input.type === 'dateRange' ? { from: undefined, to: undefined } : undefined;
      this.filterErrors[input.id] = '';
    });
    if (this.config.localStorageKey) {
      // this.globalService.storeFiltersInLocalStorage(this.config.localStorageKey, undefined);
    }
    this.resetOutput.emit();
  }

  onDownloadAll(): void {
    let hasError = false;
    this.config.inputs.forEach(input => {
      this.validateInput(input.id);
      if (this.filterErrors[input.id]) {
        hasError = true;
      }
    });
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-condition
    if (hasError) {
      return;
    }
    if (this.config.localStorageKey) {
      // this.globalService.storeFiltersInLocalStorage(this.config.localStorageKey, this.filterValues);
    }
    this.downloadAllOutput.emit({ ...this.filterValues });
  }

  // initializeFilterFromLocalStorage(): void {
  //   if (this.config.localStorageKey) {
  //     const storedFilters = this.globalService.getFiltersFromLocalStorage(this.config.localStorageKey);
  //     if (storedFilters) {
  //       this.config = {
  //         ...this.config,
  //         inputs: this.config.inputs.map(input => ({
  //           ...input,
  //           value: storedFilters[input.id],
  //         })),
  //       };
  //     }
  //   }
  //   this.initializeInputs();
  //   if (!this.config.isNotWithInitialSearch) {
  //     this.onFilter();
  //   }
  // }
}
