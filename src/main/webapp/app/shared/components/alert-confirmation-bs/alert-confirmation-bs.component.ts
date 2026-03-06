import { Component, inject, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { EnumIconAlert, EnumInputType, SWAL_MENTION_IRREVERSIBLE } from 'app/shared/constants/liste.constants';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { NgClass } from '@angular/common';
import TranslateDirective from '../../language/translate.directive';
import { TranslatePipe } from '@ngx-translate/core';
import { animate, style, transition, trigger } from '@angular/animations';
import { NgxMaskDirective } from 'ngx-mask';
import { FormControlStateDirective } from '../../directive/form-control-state.directive';

@Component({
  selector: 'jhi-alert-confirmation',
  standalone: true,
  imports: [FormsModule, NgClass, TranslateDirective, TranslatePipe, NgxMaskDirective, FormControlStateDirective],
  templateUrl: './alert-confirmation-bs.component.html',
  styleUrl: './alert-confirmation-bs.component.scss',
  animations: [
    trigger('fadeInOut', [
      transition(':enter', [
        style({ opacity: 0, transform: 'translateY(-10px)' }),
        animate('500ms ease-out', style({ opacity: 1, transform: 'translateY(0)' })),
      ]),
      transition(':leave', [animate('300ms ease-in', style({ opacity: 0, transform: 'translateY(-10px)' }))]),
    ]),
  ],
})
export class AlertConfirmationBsComponent implements OnInit, OnChanges {
  // === PARAMÈTRES D’ENTRÉE (injectés dynamiquement via openModal) ===
  @Input() icon: EnumIconAlert | string = EnumIconAlert.WARNING;
  @Input() size: 'sm' | 'lg' | 'xl' | '' = '';
  @Input() title = '';
  @Input() content?: string | HTMLElement;
  @Input() contentMsgValue?: string;
  @Input() isAddMentionIrreversible = false;

  @Input() inputType?: EnumInputType;
  @Input() inputLabel?: string;
  @Input() inputPlaceholder = '';
  @Input() inputSelectOptions: any[] = [];
  @Input() isInputRequired = false;
  @Input() maxLength = 40;
  @Input() rowCount = 7;

  @Input() reverseButtons = false;
  @Input() showCancelButton = true;
  @Input() okBtnText = 'main.components.AlertConfirmation.okButtonLabel';
  @Input() cancelBtnText = 'main.components.AlertConfirmation.cancelButtonLabel';
  @Input() cancelBtnColor = 'danger';
  @Input() confirmeBtnText = 'main.components.AlertConfirmation.confirmButtonLabel';
  @Input() confirmeBtnColor = 'success';
  @Input() showLoaderOnConfirm = false;

  // === ÉTATS INTERNES ===
  mentionIrreversible = SWAL_MENTION_IRREVERSIBLE;
  contentString?: string;
  contentHtml?: SafeHtml | HTMLElement;
  valueConfirme?: any;

  // === ENUMS exposés au template ===
  enumIconAlert = EnumIconAlert;
  enumInputType = EnumInputType;

  private readonly activeModal = inject(NgbActiveModal);
  private readonly sanitizer = inject(DomSanitizer);

  constructor() {}

  // === CYCLE DE VIE ===
  ngOnInit(): void {
    this.valueConfirme = undefined;
    this.prepareContent();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['content']) {
      this.prepareContent();
    }
  }

  // === ACTIONS ===
  confirmer(): void {
    // Si champ de type date, convertir proprement
    if (this.inputType === EnumInputType.DATE && this.valueConfirme) {
      this.valueConfirme = new Date(this.valueConfirme);
    }
    this.valueConfirme = this.valueConfirme ?? '#';
    this.activeModal.close(this.valueConfirme ?? true);
  }

  annuler(): void {
    this.valueConfirme = undefined;
    this.activeModal.dismiss();
  }

  getIconClass(icon: string): Record<string, boolean> {
    return {
      fas: true,
      'fa-question': icon === this.enumIconAlert.QUESTION,
      'fa-exclamation': icon === this.enumIconAlert.INFO,
      'fa-exclamation-triangle': icon === this.enumIconAlert.WARNING,
      'fa-times': icon === this.enumIconAlert.ERROR,
      'fa-check': icon === this.enumIconAlert.SUCCESS,
      'animated-icon': true,
      'icon-bounce': icon === this.enumIconAlert.SUCCESS,
      'icon-shake': icon === this.enumIconAlert.ERROR,
      'icon-pulse': icon === this.enumIconAlert.INFO || icon === this.enumIconAlert.WARNING,
      'icon-fadein': true, // animation d’entrée douce pour tous
    };
  }

  getIconColor(icon: string): string {
    switch (icon) {
      case this.enumIconAlert.QUESTION:
      case this.enumIconAlert.INFO:
        return 'rgb(103,155,240)';
      case this.enumIconAlert.WARNING:
        return 'rgb(252,177,4)';
      case this.enumIconAlert.ERROR:
        return 'rgb(211,5,5)';
      case this.enumIconAlert.SUCCESS:
        return 'rgb(63,158,11)';
      default:
        return 'inherit';
    }
  }

  private prepareContent(): void {
    if (typeof this.content === 'string') {
      // Assainir le HTML en toute sécurité
      // this.contentHtml = this.sanitizer.bypassSecurityTrustHtml(this.content);
      this.contentString = this.content; // Garde aussi la version brute pour translate
    } else if (this.content instanceof HTMLElement) {
      this.contentHtml = this.content;
    } else {
      this.contentHtml = undefined;
    }
  }
}
