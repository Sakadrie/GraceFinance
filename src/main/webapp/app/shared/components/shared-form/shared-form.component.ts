import { NgIf, NgFor, NgClass } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { TranslateDirective, TranslatePipe } from '@ngx-translate/core';
import { environment } from 'environments/environment';

export interface SharedFormFileUploadInputParam {
  libelle: string;
  accept: string;
  extensions: string[];
  // sizeLimit: EnumMega;
}

export interface SharedFormFileUploadInput {
  param: SharedFormFileUploadInputParam;
  textButton?: string;
  textDragDrop?: string;
  fileUploadId?: number;
  isRequired?: boolean;
  isDansListe?: boolean;
  module?: string;
  sousChemin?: string;
  isWithIncrement?: boolean;
  isWithDocument?: boolean;
  fichierId?: number | null;
  lien?: string | null;
  // fichier?: IFichier | null;
}

export interface SharedFormActionButton {
  classes?: string;
  id: string;
  label: string;
  isWithIcon?: boolean;
  iconName?: string;
}

export interface SharedFormInputOption {
  label: string;
  value: any;
  code?: string;
  labelFr?: string;
  labelEn?: string;
  labelCn?: string;
}

export interface SharedFormInputConfig {
  hide?: boolean; // Pour masquer l'input
  id: string;
  index: number;
  type:
    | 'text'
    | 'textarea'
    | 'number'
    | 'floatNumber'
    | 'date'
    | 'datetime'
    | 'dateRange'
    | 'select'
    | 'select2'
    | 'select2-multiple'
    | 'checkbox'
    | 'switch'
    | 'radio'
    | 'wysiwyg'
    | 'fichier';
  label: string;
  placeholder?: string;
  validations?: {
    required?: boolean;
    minLength?: number;
    maxLength?: number;
    min?: number;
    max?: number;
    pattern?: string;
    patternExample?: string;
  };
  isDisabled?: boolean;
  mask?: string;
  thousandSeparator?: '.' | ',';
  decimalMarker?: '.' | ',';
  decimalLimit?: number;
  options?: SharedFormInputOption[];
  isOptionAddAvailable?: boolean;
  bindLabel?: string;
  bindValue?: string;
  groupByKey?: string;
  maxSelectedItems?: number;
  searchable?: boolean;
  searchWhileTyping?: boolean;
  searchFunction?: (term: string, item: SharedFormInputOption) => boolean;
  value?: any;
  // toolbar?: Toolbar;
  colClasses?: string;
  fileInputParam?: SharedFormFileUploadInput;
  isWithOptionsLabelTranslation?: boolean; // Si true, les labels des options sont traduits
}

export interface SharedFormConfig {
  inputs: SharedFormInputConfig[];
  actionButtons?: SharedFormActionButton[];
  title?: string;
  subTitle?: string;
  saveButtonText?: string;
  saveButtonIconName?: string;
  saveButtonClasses?: string;
  buttonsDivClasses?: string;
  isNotWithSaveButton?: boolean;
  isNotWithSaveButtonIcon?: boolean;
  isWithResetButton?: boolean;
  isNotWithResetButtonIcon?: boolean;
  isWithBackButton?: boolean;
  isNotWithBackButtonIcon?: boolean;
  isWithAlertChampObligatoire?: boolean;
  isWithRecaptcha?: boolean;
}

@Component({
  selector: 'jhi-shared-form',
  templateUrl: './shared-form.component.html',
  styleUrl: './shared-form.component.scss',
  standalone: true,
  imports: [
    // NgIf,
    // FormsModule,
    // NgFor,
    // TranslateDirective,
    // MatFormField,
    // MatLabel,
    // MatOption,
    // MatSelect,
    // TranslatePipe,
    // NgClass,
    // ReusableNgxEditorComponent,
    // NgxMaskDirective,
    // NgSelectModule,
    // DynamicLabelDirective,
    // FileUploadComponent,
    // RecaptchaFormsModule,
    // RecaptchaModule,
    // AlertChampObligatoireComponent,
  ],
  // providers: [
  //   provideNgxMask(),
  //   DynamicLabelDirective,
  //   {
  //     provide: RECAPTCHA_SETTINGS,
  //     useValue: {
  //       siteKey: environment.recaptcha.siteKey,
  //     } as RecaptchaSettings,
  //   },
  // ],
})
export class SharedFormComponent implements OnInit {
  @Input() config!: SharedFormConfig;
  @Input() showActionButtons = true;
  @Input() isShowHeader = false;
  @Output() saveOutput = new EventEmitter<Record<string, any>>();
  @Output() changeOutput = new EventEmitter<Record<string, any>>();
  @Output() resetOutput = new EventEmitter<void>();
  @Output() backOutput = new EventEmitter<void>();
  @Output() actionButtonOutput = new EventEmitter<string>();
  @Output() addOptionOutput = new EventEmitter<string>();

  formValues: Record<string, any> = {};
  formErrors: Record<string, string> = {};
  formErrorsTranslatedValues: Record<string, any> = {};

  captcha: string | undefined;

  ngOnInit(): void {
    this.initializeInputs();
  }

  /**
   * Initialise uniquement les nouveaux inputs ajoutés à config.inputs après ngOnInit
   */
  initializeInputs(onlyNew?: boolean): void {
    if (this.config.inputs.length) {
      this.config.inputs.forEach(input => {
        if (onlyNew) {
          if (!(input.id in this.formValues)) {
            this.formValues[input.id] =
              input.value ??
              (input.type === 'dateRange'
                ? {
                    from: undefined,
                    to: undefined,
                  }
                : input.type === 'select2-multiple'
                  ? []
                  : undefined);
            this.formErrors[input.id] = '';
          }
        } else {
          this.formValues[input.id] =
            input.value ??
            (input.type === 'dateRange'
              ? {
                  from: undefined,
                  to: undefined,
                }
              : input.type === 'select2-multiple'
                ? []
                : undefined);
          this.formErrors[input.id] = '';
        }
      });
    }
  }

  updateInputOptions(id: string, options: SharedFormInputOption[]): void {
    const input = this.config.inputs.find(i => i.id === id);
    if (input) {
      input.options = options;
    }
  }

  onInputChange(id: string, value: any): void {
    const isChanged = this.formValues[id] !== value;
    this.formValues[id] = value;
    this.validateInput(id);
    if (isChanged) {
      this.onChange();
    }
  }

  onInputSelect2Change(input: { id: string; bindValue: string }, value: any): void {
    if (value) {
      this.formValues[input.id] = value[input.bindValue];
    } else {
      this.formValues[input.id] = null;
    }
    this.validateInput(input.id);
    this.onChange();
  }

  onInputSelect2MultipleChange(input: { id: string; bindValue: string }, value: any): void {
    this.validateInput(input.id);
    this.onChange();
  }

  onDateRangeChange(id: string, field: 'from' | 'to', value: any): void {
    this.formValues[id] ??= { from: '', to: '' };
    this.formValues[id][field] = value;
    this.validateInput(id);
    this.onChange();
  }

  validateInput(id: string): void {
    const input = this.config.inputs.find(i => i.id === id);
    if (!input) {
      return;
    }
    let value = this.formValues[id];
    if (Array.isArray(value)) {
      value = value.filter(v => v !== undefined);
    }
    let error = '';
    let errorTranslatedValues = {};
    if (input.validations?.required) {
      if (input.type === 'dateRange') {
        if (!value?.from || !value?.to) {
          error = 'main.validations.required';
        }
      } else if (input.type === 'select2-multiple') {
        if (!value || !Array.isArray(value) || value.length === 0) {
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
    this.formErrors[id] = error;
    this.formErrorsTranslatedValues[id] = errorTranslatedValues;
  }

  onChange(): void {
    this.changeOutput.emit({ ...this.formValues });
  }

  onSave(): void {
    let hasError = false;
    this.config.inputs.forEach(input => {
      this.validateInput(input.id);
      if (this.formErrors[input.id]) {
        hasError = true;
      }
    });
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-condition
    if (hasError) {
      return;
    }
    this.saveOutput.emit({ ...this.formValues });
  }

  validateAndEmit(): boolean {
    // Aucun champ de formulaire configuré.
    if (!this.config.inputs.length) {
      return false;
    }

    let isValid = true;
    for (const input of this.config.inputs) {
      this.validateInput(input.id);
      const error = this.formErrors[input.id];

      if (error) {
        isValid = false;
      }
    }

    // Formulaire invalide, vérifiez les champs.
    if (!isValid) {
      return false;
    }

    this.saveOutput.emit({ ...this.formValues });
    return true;
  }

  onReset(): void {
    this.config.inputs.forEach(input => {
      this.formValues[input.id] = input.value ?? (input.type === 'dateRange' ? { from: '', to: '' } : undefined);
      this.formErrors[input.id] = '';
    });
    if (this.captcha) {
      this.captcha = undefined;
    }
    this.resetOutput.emit();
  }

  onBack(): void {
    this.backOutput.emit();
  }

  onActionButtonClick(actionId: string): void {
    this.actionButtonOutput.emit(actionId);
  }

  hasFormErrors(): boolean {
    return Object.values(this.formErrors).some(error => error !== '');
  }

  onAddOption(inputId: string): void {
    this.addOptionOutput.emit(inputId);
  }

  // Gestion de l'upload de fichier
  onFileUploaded(id: string, fichier: any): void {
    // On suppose que le backend retourne l'objet fichier avec un id
    this.formValues[id] = fichier.id ?? fichier;
    this.validateInput(id);
    this.onChange();
  }

  onFileDeleted(id: string, fichier: any): void {
    this.formValues[id] = null;
    this.validateInput(id);
    this.onChange();
  }

  generateFileUploadId(): number {
    return Math.floor(Math.random() * (999999 - 10000 + 1)) + 10000;
  }
}
