import { NgClass, NgIf } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { NgbTooltip } from '@ng-bootstrap/ng-bootstrap';
import { TranslatePipe } from '@ngx-translate/core';
import HasAnyAuthorityDirective from 'app/shared/auth/has-any-authority.directive';
import TranslateDirective from 'app/shared/language/translate.directive';

@Component({
  selector: 'jhi-admin-page-header',
  imports: [NgClass, NgIf, TranslateDirective, NgbTooltip, TranslatePipe, HasAnyAuthorityDirective],
  templateUrl: './admin-page-header.component.html',
  styleUrl: './admin-page-header.component.scss',
})
export class AdminPageHeaderComponent {
  @Input() pageHeaderInput!: AdminPageHeaderInput;

  @Output() addButtonClicked = new EventEmitter<void>();
  @Output() addBescExportButtonClicked = new EventEmitter<void>();
  @Output() addBescImportButtonClicked = new EventEmitter<void>();
  @Output() exportDataButtonClicked = new EventEmitter<void>();
  @Output() refreshDataButtonClicked = new EventEmitter<void>();
  @Output() filterSettingsButtonClicked = new EventEmitter<void>();
  @Output() backButtonClicked = new EventEmitter<void>();
  @Output() saveButtonClicked = new EventEmitter<void>();
  @Output() resetButtonClicked = new EventEmitter<void>();
  @Output() topOffcanvasButtonClicked = new EventEmitter<void>();
  @Output() bottomOffcanvasButtonClicked = new EventEmitter<void>();
  @Output() rightOffcanvasButtonClicked = new EventEmitter<void>();
  @Output() leftOffcanvasButtonClicked = new EventEmitter<void>();
  @Output() showSystemDataButtonClicked = new EventEmitter<void>();
  @Output() deleteButtonClicked = new EventEmitter<void>();
}

export interface AdminPageHeaderInput {
  title: string;
  titleTranslatedValues?: any;
  items: BreadCrumbItem[];
  isWithAddButton?: boolean;
  isWithAddBescExportButton?: boolean;
  isWithAddBescImportButton?: boolean;
  addButtonAuthorities?: string[];
  addBescExportButtonAuthorities?: string[];
  addBescImportButtonAuthorities?: string[];
  isWithExportDataButton?: boolean;
  exportDataButtonAuthorities?: string[];
  isWithRefreshDataButton?: boolean;
  isWithFilterSettingsButton?: boolean;
  isWithBackButton?: boolean;
  isWithSaveButton?: boolean;
  isWithResetButton?: boolean;
  isWithTopOffcanvasButton?: boolean;
  isWithBottomOffcanvasButton?: boolean;
  isWithRightOffcanvasButton?: boolean;
  isWithLeftOffcanvasButton?: boolean;
  isWithShowSystemDataButton?: boolean;
  showSystemDataButtonAuthorities?: string[];
  isWithDeleteButton?: boolean;
  deleteButtonAuthorities?: string[];
  isWithFilterSettingsButtonLabel?: boolean;
  filterSettingsButtonLabel?: string;
  isWithAddButtonLabel?: boolean;
  addButtonLabel?: string;
  isWithAddBescImportButtonLabel?: boolean;
  addBescImportButtonLabel?: string;
  isWithAddBescExportButtonLabel?: boolean;
  addBescExportButtonLabel?: string;
  isWithRefreshDataButtonLabel?: boolean;
  refreshDataButtonLabel?: string;
  isWithExportDataButtonLabel?: boolean;
  exportDataButtonLabel?: string;
  isWithSaveButtonLabel?: boolean;
  saveButtonLabel?: string;
  isWithResetButtonLabel?: boolean;
  resetButtonLabel?: string;
  isWithBackButtonLabel?: boolean;
  backButtonLabel?: string;
  isWithTopOffcanvasButtonLabel?: boolean;
  topOffcanvasButtonLabel?: string;
  isWithBottomOffcanvasButtonLabel?: boolean;
  bottomOffcanvasButtonLabel?: string;
  isWithRightOffcanvasButtonLabel?: boolean;
  rightOffcanvasButtonLabel?: string;
  isWithLeftOffcanvasButtonLabel?: boolean;
  leftOffcanvasButtonLabel?: string;
  isWithShowSystemDataButtonLabel?: boolean;
  showSystemDataButtonLabel?: string;
  isWithDeleteButtonLabel?: boolean;
  deleteButtonLabel?: string;
}

export interface BreadCrumbItem {
  label: string;
  url?: string | null;
  isActive?: boolean;
}
