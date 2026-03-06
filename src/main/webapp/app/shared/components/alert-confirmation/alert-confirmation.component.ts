/* eslint-disable eqeqeq */
import { Component, Inject } from '@angular/core';
import {
  EnumIconAlert,
  EnumInputType,
  SWAL_MENTION_IRREVERSIBLE,
  SWAL_TEXT_BTN_CANCEL,
  SWAL_TEXT_BTN_CONFIRME,
} from '../../constants/liste.constants';
import { MAT_DIALOG_DATA, MatDialogClose, MatDialogContent, MatDialogRef } from '@angular/material/dialog';
import { FormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { MatButton, MatButtonModule } from '@angular/material/button';
import { NgForOf, NgIf } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatRadioModule } from '@angular/material/radio';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatOptionModule } from '@angular/material/core';
import { TranslateDirective } from 'app/shared/language';
import { TranslatePipe } from '@ngx-translate/core';

export interface DialogData {
  title: string;
  content: string;
  contentMsgValue: string;
  icon: string;
  showCancelButton: boolean;
  isAddMentionIrreversible: boolean;
  reverseButtons: boolean;
  inputType: string;
  inputLabel: string;
  isInputRequired: boolean;
  inputPlaceholder: string;
  maxLength: number;
  rowCount: number;
  inputSelectOptions: any[];
  cancelBtnText: string;
  cancelBtnColor: string;
  confirmeBtnText: string;
  confirmeBtnColor: string;
}

@Component({
  selector: 'jhi-alert-confirmation',
  standalone: true,
  imports: [
    FormsModule,
    NgIf,
    NgForOf,
    MatButton,
    MatDialogContent,
    MatDialogClose,
    MatFormFieldModule,
    MatInputModule,
    MatRadioModule,
    MatDatepickerModule,
    MatSelectModule,
    MatOptionModule,
    MatButtonModule,
    MatIconModule,
    TranslateDirective,
    TranslatePipe,
  ],
  templateUrl: './alert-confirmation.component.html',
})
export class AlertConfirmationComponent {
  icon?: string;
  title = '';
  content?: string | HTMLElement;
  contentMsgValue?: string;
  contentString?: string;
  contentHtml?: HTMLElement;

  isAddMentionIrreversible = false;
  mentionIrreversible = SWAL_MENTION_IRREVERSIBLE;

  inputType?: string;
  inputLabel?: string;
  isInputRequired = false;
  inputPlaceholder = '';
  inputSelectOptions?: any[];
  maxLength = 40;
  rowCount = 7;

  reverseButtons = false;
  showCancelButton = false;
  cancelBtnText = 'Annuler';
  cancelBtnColor = 'danger';
  confirmeBtnText = 'Accepter';
  confirmeBtnColor = 'success';

  showLoaderOnConfirm = false;

  enumIconAlert = EnumIconAlert;
  enumInputType = EnumInputType;
  valueConfirme?: any;

  constructor(
    public dialogRef: MatDialogRef<AlertConfirmationComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
  ) {
    this.title = data.title;
    this.content = data.content;
    this.contentMsgValue = data.contentMsgValue;
    this.icon = data.icon;
    this.showCancelButton = data.showCancelButton;
    this.isAddMentionIrreversible = data.isAddMentionIrreversible;
    this.reverseButtons = data.reverseButtons;
    this.inputType = data.inputType;
    this.inputLabel = data.inputLabel;
    this.isInputRequired = data.isInputRequired;
    this.inputPlaceholder = data.inputPlaceholder;
    this.rowCount = data.rowCount;
    this.maxLength = data.maxLength;
    this.inputSelectOptions = data.inputSelectOptions;
    this.cancelBtnText = data.cancelBtnText || SWAL_TEXT_BTN_CANCEL;
    this.cancelBtnColor = data.cancelBtnColor || 'warn';
    this.confirmeBtnText = data.confirmeBtnText || SWAL_TEXT_BTN_CONFIRME;
    this.confirmeBtnColor = data.confirmeBtnColor || 'primary';
    if (this.isContentStringOrHTMLElement()) {
      this.contentString = this.content;
    } else {
      this.contentHtml = this.content as unknown as HTMLElement;
    }
  }

  confirmer(): void {
    if (this.inputType == EnumInputType.DATE) {
      this.valueConfirme = new Date(this.valueConfirme);
    }
    this.valueConfirme = this.valueConfirme ?? '#';
    this.dialogRef.close(this.valueConfirme);
  }

  isContentStringOrHTMLElement(): boolean {
    return typeof this.content === 'string' || this.content instanceof HTMLElement;
  }
}
