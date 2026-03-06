/* eslint-disable @typescript-eslint/no-unnecessary-type-parameters */
/* eslint-disable @typescript-eslint/no-unnecessary-boolean-literal-compare */
/* eslint-disable eqeqeq */
/* eslint-disable @typescript-eslint/no-inferrable-types */
/* eslint-disable object-shorthand */
/* eslint-disable @typescript-eslint/consistent-indexed-object-style */
import { Direction } from '@angular/cdk/bidi';
import { ComponentType } from '@angular/cdk/overlay';
import { HttpResponse } from '@angular/common/http';
import { EventEmitter, Injectable, SecurityContext } from '@angular/core';

import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import dayjs from 'dayjs';
import weekOfYear from 'dayjs/plugin/weekOfYear';
import { Observable, Subject } from 'rxjs';
import { ASC, DESC } from '../../config/navigation.constants';
import {
  EnumIconAlert,
  HEADER_PREFIX,
  IS_TRANSLATION_ENABLED_ON_TOAST,
  MESSAGE_SERVER_ERROR_CONTACT_ADMIN,
  MESSAGE_TITLE_ERROR,
  MESSAGE_TITLE_INFO,
  MESSAGE_TITLE_SUCCESS,
  MESSAGE_TITLE_WARN,
  SNACKBAR_DURATION,
  SWAL_TEXT_BTN_CANCEL,
  SWAL_TEXT_BTN_CONFIRME,
} from '../../shared/constants/liste.constants';
import { IRequestHeader } from '../../shared/model/request.model';
import { DomSanitizer } from '@angular/platform-browser';
import { AccountService } from '../auth/account.service';
import { UnsubscribeOnDestroyAdapter } from 'app/shared/core/UnsubscribeOnDestroyAdapter';
import { AlertConfirmationComponent } from 'app/shared/components/alert-confirmation/alert-confirmation.component';
import { TranslateService } from '@ngx-translate/core';
import { SharedFilterPaneConfig } from 'app/shared/components/shared-filter-pane/shared-filter-pane.component';
import { ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { IProfil } from 'app/shared/model/security/profil.model';

dayjs.extend(weekOfYear);

// EventEmitter global pour la communication des toasts
export const globalToastEmitter = new EventEmitter<{ toast: any; category?: string }>();

@Injectable({
  providedIn: 'root',
})
export class GlobalService extends UnsubscribeOnDestroyAdapter {
  ascending = true;
  private timeOutId: any;

  private sessionId: string | null = null;

  private dureeSession = 86400000; // 24h

  private dateDerniereRecuperation: { [key: string]: dayjs.Dayjs } = {};
  private delaiMinimumEntreDeuxRecuperation = 300000; // 5 minute en millisecondes

  constructor(
    private router: Router,
    private snackBar: MatSnackBar,
    public dialog: MatDialog,
    protected sanitizer: DomSanitizer,
    private accountService: AccountService,
    private translateService: TranslateService,
  ) {
    super();
  }

  public openDialog<T, D = any, R = any>(component: ComponentType<T>, data?: {}): MatDialogRef<T, R> {
    const direction = this.getCurrentDirection();
    return this.dialog.open(component, {
      data: data,
      direction: direction,
    });
  }

  public openDialogSm<T, D = any, R = any>(component: ComponentType<T>, data?: {}): MatDialogRef<T, R> {
    return this.openDialogWithWidth({ component: component, data: data, width: '40%' });
  }

  public openDialogMd<T, D = any, R = any>(component: ComponentType<T>, data?: {}): MatDialogRef<T, R> {
    return this.openDialogWithWidth({ component: component, data: data, width: '70%' });
  }

  public openDialogLg<T, D = any, R = any>(component: ComponentType<T>, data?: {}): MatDialogRef<T, R> {
    return this.openDialogWithWidth({ component: component, data: data, width: '80%' });
  }

  public showNotifSnackbarSuccess(text: string, duration?: number): void {
    this.showNotifSnackbar('snackbar-success', text, duration);
  }

  public showNotifSnackbarDanger(text: string, duration?: number): void {
    this.showNotifSnackbar('snackbar-danger', text, duration);
  }

  public showNotifSnacbarInfo(text: string, duration?: number): void {
    this.showNotifSnackbar('snackbar-info', text, duration);
  }

  public showNotifSnackbarPrimary(text: string, duration?: number): void {
    this.showNotifSnackbar('snackbar-primary', text, duration);
  }

  public showNotifSnackbarWarning(text: string, duration?: number): void {
    this.showNotifSnackbar('snackbar-warning', text, duration);
  }

  public showSnackBarBackendMessage(param: { httpResponse?: HttpResponse<any>; dialogRef?: MatDialogRef<any>; duration?: number }): void {
    if (param.httpResponse?.headers) {
      const msg = param.httpResponse.headers.get(HEADER_PREFIX + 'Msg');
      const isError = JSON.parse(param.httpResponse.headers.get(HEADER_PREFIX + 'Iserror')!);
      if (isError) {
        this.showNotifSnackbarDanger(msg!, param.duration);
      } else {
        this.showNotifSnackbarSuccess(msg!, param.duration);
        param.dialogRef?.close(param.httpResponse);
      }
    } else {
      this.showNotifSnackbarDanger('Opération effectuée avec succès');
    }
  }

  public showToast(param: {
    isWithTranslation?: boolean;
    category?:
      | 'basic'
      | 'primary'
      | 'secondary'
      | 'success'
      | 'danger'
      | 'warning'
      | 'info'
      | 'solid-primary'
      | 'solid-secondary'
      | 'solid-success'
      | 'solid-danger'
      | 'solid-warning'
      | 'solid-info';
    header?: { label?: string; variables?: { [key: string]: string } };
    content?: { label?: string; variables?: { [key: string]: string } };
    contentBis?: { label?: string; variables?: { [key: string]: string } };
    content1?: { label?: string; variables?: { [key: string]: string } };
    delay?: number;
    sticky?: boolean;
  }): void {
    // Nouvelle implémentation : émet un toast pour ToastsComponent
    let header = param.header;
    let content = param.content;
    let contentBis = param.contentBis;
    let content1 = param.content1;

    if (param.isWithTranslation) {
      if (header?.label) {
        header = {
          ...header,
          label: this.translateService.instant(header.label, header.variables),
        };
      }
      if (content?.label) {
        content = {
          ...content,
          label: this.translateService.instant(content.label, content.variables),
        };
      }
      if (contentBis?.label) {
        contentBis = {
          ...contentBis,
          label: this.translateService.instant(contentBis.label, contentBis.variables),
        };
      }
      if (content1?.label) {
        content1 = {
          ...content1,
          label: this.translateService.instant(content1.label, content1.variables),
        };
      }
    }
    globalToastEmitter.emit({
      toast: {
        autohide: param.sticky !== true,
        title: header?.label,
        content: content?.label,
        contentBis: contentBis?.label,
        content1: content1?.label,
        delay: param.delay ?? 8000,
      },
      category: param.category,
    });
  }

  public showToastSuccess(
    message: { label?: string; variables?: { [key: string]: string } },
    message1?: { label?: string; variables?: { [key: string]: string } },
    duration?: number,
    sticky?: boolean,
    messageBis?: { label?: string; variables?: { [key: string]: string } },
  ): void {
    this.showToast({
      isWithTranslation: IS_TRANSLATION_ENABLED_ON_TOAST,
      category: 'success',
      header: { label: MESSAGE_TITLE_SUCCESS },
      content: message,
      contentBis: messageBis,
      content1: message1,
      delay: duration,
      sticky: sticky,
    });
  }

  public showToastWarn(
    message: { label?: string; variables?: { [key: string]: string } },
    message1?: { label?: string; variables?: { [key: string]: string } },
    duration?: number,
    sticky?: boolean,
    messageBis?: { label?: string; variables?: { [key: string]: string } },
  ): void {
    this.showToast({
      isWithTranslation: IS_TRANSLATION_ENABLED_ON_TOAST,
      category: 'warning',
      header: { label: MESSAGE_TITLE_WARN },
      content: message,
      content1: message1,
      delay: duration,
      sticky: sticky,
      contentBis: messageBis,
    });
  }

  public showToastInfo(
    message: { label?: string; variables?: { [key: string]: string } },
    message1?: { label?: string; variables?: { [key: string]: string } },
    duration?: number,
    sticky?: boolean,
    messageBis?: { label?: string; variables?: { [key: string]: string } },
  ): void {
    this.showToast({
      isWithTranslation: IS_TRANSLATION_ENABLED_ON_TOAST,
      category: 'info',
      header: { label: MESSAGE_TITLE_INFO },
      content: message,
      content1: message1,
      delay: duration,
      sticky: sticky,
      contentBis: messageBis,
    });
  }

  public showToastError(
    message: { label?: string; variables?: { [key: string]: string } },
    message1?: { label?: string; variables?: { [key: string]: string } },
    duration?: number,
    sticky?: boolean,
    messageBis?: { label?: string; variables?: { [key: string]: string } },
  ): void {
    this.showToast({
      isWithTranslation: IS_TRANSLATION_ENABLED_ON_TOAST,
      category: 'danger',
      header: { label: MESSAGE_TITLE_ERROR },
      content: message,
      contentBis: messageBis,
      content1: message1,
      delay: duration,
      sticky: sticky,
    });
  }

  public getBackendIsError(httpResponse: HttpResponse<any>): any {
    let isError: boolean | undefined;
    let msg: string | null = '';
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-condition
    if (httpResponse.headers) {
      msg = httpResponse.headers.get(HEADER_PREFIX + 'Msg');
      isError = JSON.parse(httpResponse.headers.get(HEADER_PREFIX + 'Iserror')!);
    } else {
      isError = undefined;
    }
    return {
      isError: isError,
      message: msg,
    };
  }

  public showToastBackendMessage(
    param: {
      httpResponse?: HttpResponse<any>;
      dialogRef?: MatDialogRef<any>;
      navigateTo?: string[];
      duration?: number;
    },
    customMessageSuccess?: string,
    customMessageError?: string,
  ): void {
    if (param.httpResponse?.headers) {
      const msg = param.httpResponse.headers.get(HEADER_PREFIX + 'Msg');
      const isError = JSON.parse(param.httpResponse.headers.get(HEADER_PREFIX + 'Iserror')!);
      if (isError) {
        this.showToastError({ label: msg ?? customMessageError ?? '' }, undefined, param.duration);
      } else {
        this.showToastSuccess({ label: msg ?? customMessageSuccess ?? '' }, undefined, param.duration);
        param.dialogRef?.close(param.httpResponse);
        if (param.navigateTo) {
          this.router.navigate(param.navigateTo);
        }
      }
    } else {
      //
    }
  }

  public getSortQueryParam(predicate: string = 'id'): string[] {
    this.ascending = !this.ascending;
    const ascendingQueryParam = this.ascending ? ASC : DESC;
    if (predicate === '') {
      return [];
    } else {
      return [predicate + ',' + ascendingQueryParam];
    }
  }

  public requestHeadersInObjet(
    requestHeaders: IRequestHeader[],
    pageIndex?: number,
    pageSize?: number,
    sort?: string[],
  ): { [key: string]: any } {
    const obj: { [key: string]: any } = {};
    requestHeaders.forEach(request => {
      if (request.propriete && request.expression) {
        const id: string = request.propriete.concat('.').concat(request.expression);
        if (
          (request.value != undefined && request.value != 'undefined' && request.value != '') ||
          (typeof request.value == 'boolean' && Boolean(request.value) == false)
        ) {
          // Si request.value est un array on va concatener les valeurs
          if (Array.isArray(request.value)) {
            obj[id] = request.value.join(',');
          } else {
            obj[id] = request.value;
          }
        }
      }
    });
    if (pageIndex !== undefined) {
      obj['page'] = pageIndex;
    }
    if (pageSize != undefined) {
      obj['size'] = pageSize;
    }
    if (sort?.length) {
      obj['sort'] = sort;
    }
    return obj;
  }

  buildRequestHeaders(filterConfig?: SharedFilterPaneConfig, filterValues?: Record<string, any>): IRequestHeader[] {
    const headers: IRequestHeader[] = [];
    // Utilise la config de filtre pour construire les headers
    filterConfig?.inputs.forEach(input => {
      const value = filterValues?.[input.id];
      // Ignore les valeurs vides ou nulles
      if (value !== undefined && value !== null && value !== '' && !(input.type === 'dateRange' && !value.from && !value.to)) {
        if (input.type === 'dateRange') {
          if (value.from) {
            headers.push({
              propriete: input.filterProperty ?? input.id,
              expression: 'greaterThanOrEqual',
              value: value.from,
            });
          }
          if (value.to) {
            headers.push({
              propriete: input.filterProperty ?? input.id,
              expression: 'lessThanOrEqual',
              value: value.to,
            });
          }
        } else {
          headers.push({
            propriete: input.filterProperty ?? input.id,
            expression: input.filterExpression ?? 'equals',
            value,
          });
        }
      }
    });
    return headers;
  }

  buildFilterDatas(
    filterConfig?: SharedFilterPaneConfig,
    filterValues?: Record<string, any>,
    pageSize = ITEMS_PER_PAGE,
    pageIndex = 0,
    sort: string[] = [],
  ): any {
    const req = this.buildRequestHeaders(filterConfig, filterValues);
    return this.requestHeadersInObjet(req, pageIndex, pageSize, sort);
  }

  reverseSortDir(sortDir: 'asc' | 'desc'): 'asc' | 'desc' {
    return sortDir === 'asc' ? 'desc' : 'asc';
  }

  // export table data in excel file
  //   exportToExcel(exportedColumnData: ExportedColumnData[], data: any[], fileName?: string, langKey?: 'zh-cn' | 'fr' | 'en'): void {
  //     if (data.length) {
  //       // key name with space add in brackets
  //       const exportData = data.map(row => {
  //         const mappedRow: { [key: string]: any } = {};
  //         exportedColumnData.forEach(col => {
  //           let value = row[col.key];
  //           switch (col.type) {
  //             case 'date':
  //               if (value) {
  //                 if (langKey === 'fr') {
  //                   value = dayjs(value).format('DD/MM/YYYY');
  //                 } else {
  //                   value = dayjs(value).format('YYYY-MM-DD');
  //                 }
  //               } else {
  //                 value = '';
  //               }
  //               break;
  //             case 'datetime':
  //               if (value) {
  //                 if (langKey === 'fr') {
  //                   value = dayjs(value).format('DD/MM/YYYY HH:mm:ss');
  //                 } else {
  //                   value = dayjs(value).format('YYYY-MM-DD HH:mm:ss');
  //                 }
  //               } else {
  //                 value = '';
  //               }
  //               break;
  //             case 'boolean':
  //               if (value === undefined || value === null) {
  //                 value = '';
  //               } else {
  //                 if (langKey === 'fr') {
  //                   value = value === true ? 'Oui' : 'Non';
  //                 } else if (langKey === 'en') {
  //                   value = value === true ? 'Yes' : 'No';
  //                 } else if (langKey === 'zh-cn') {
  //                   value = value === true ? '是' : '否';
  //                 } else {
  //                   value = value === true ? 'Yes' : 'No';
  //                 }
  //               }
  //               break;
  //             default:
  //               value = value ?? '';
  //           }
  //           const translatedLabel = this.translateService.instant(col.label);
  //           mappedRow[translatedLabel] = value;
  //         });
  //         return mappedRow;
  //       });

  //       TableExportUtil.exportToExcel(exportData, fileName ?? EnumExcelExportConstante.DEFAULT_NAME);
  //     } else {
  //       this.afficherAlertMessage(MESSAGE_TITLE_ERROR, 'main.tooltips.noData', EnumIconAlert.ERROR);
  //     }
  //   }

  public getWeekNumber(date: dayjs.Dayjs): number {
    return date.week();
  }

  /**
   * Trie les périodes encodées dans l'ordre croissant
   * @param periods
   * @returns
   */
  public sortPeriods(periods: string[]): string[] {
    const regex = /(\d+)([TSMASE]+)(\d+)/;

    return periods.sort((a, b) => {
      const matchA = a.match(regex);
      if (!matchA) {
        return 0;
      }
      const [, quarterA, separatorA, yearA] = matchA.map((value, index) => (index === 1 || index === 3 ? Number(value) : value));
      const matchB = b.match(regex);
      if (!matchB) {
        return 0;
      }
      const [, quarterB, separatorB, yearB] = matchB.map((value, index) => (index === 1 || index === 3 ? Number(value) : value));

      if (typeof yearA === 'number' && typeof yearB === 'number' && yearA !== yearB) {
        return yearA - yearB;
      } else if (typeof separatorA === 'string' && typeof separatorB === 'string' && separatorA !== separatorB) {
        return separatorA.localeCompare(separatorB);
      } else if (typeof quarterA === 'number' && typeof quarterB === 'number') {
        return quarterA - quarterB;
      } else {
        return 0;
      }
    });
  }

  /**
   * Trie les périodes encodées dans l'ordre spécifié
   * @param periods
   * @param ascending
   * @returns
   */
  public sortPeriodsByOrder(periods: string[], ascending: boolean = true): string[] {
    const regex = /(\d+)([TSMASE]+)(\d+)/;

    return periods.sort((a, b) => {
      const matchA = a.match(regex);
      if (!matchA) {
        return 0;
      }
      const [, quarterA, separatorA, yearA] = matchA.map((value, index) => (index === 1 || index === 3 ? Number(value) : value));
      const matchB = b.match(regex);
      if (!matchB) {
        return 0;
      }
      const [, quarterB, separatorB, yearB] = matchB.map((value, index) => (index === 1 || index === 3 ? Number(value) : value));

      let comparison = 0;
      if (typeof yearA === 'number' && typeof yearB === 'number' && yearA !== yearB) {
        comparison = yearA - yearB;
      } else if (typeof separatorA === 'string' && typeof separatorB === 'string' && separatorA !== separatorB) {
        comparison = separatorA.localeCompare(separatorB);
      } else if (typeof quarterA === 'number' && typeof quarterB === 'number') {
        comparison = quarterA - quarterB;
      }

      return ascending ? comparison : -comparison;
    });
  }

  /**
   * Methode permettant de nettoyer un texte pour eviter les attaques XSS et echapper les caractères spéciaux
   * @param text Texte à nettoyer
   */
  public sanitizeText(text: string): string | undefined {
    return this.sanitizer.sanitize(SecurityContext.HTML, text) ?? undefined;
  }

  public afficherAlertConfirmation(params: {
    titre: string;
    message: string;
    messageTransValue?: string;
    icon: 'success' | 'error' | 'warning' | 'info' | 'question' | undefined;
    showCancelButton?: boolean;
    isAddMentionIrreversible?: boolean;
    reverseButtons?: boolean;
    inputType?: 'radio' | 'select' | 'text' | 'checkbox' | 'number' | 'textarea' | 'date';
    inputLabel?: string;
    isInputRequired?: boolean;
    inputPlaceholder?: string;
    maxLength?: number;
    rowCount?: number;
    inputSelectOptions?: any[];
    cancelBtnText?: string;
    cancelBtnColor?: 'danger' | 'warning' | 'info' | 'primary' | 'success' | 'dark' | 'secondary' | 'default';
    confirmeBtnText?: string;
    confirmeBtnColor?: 'danger' | 'warning' | 'info' | 'primary' | 'success' | 'dark' | 'secondary' | 'default';
  }): Observable<any> {
    const data = {
      title: params.titre,
      content: params.message,
      contentMsgValue: params.messageTransValue,
      icon: params.icon,
      showCancelButton: params.showCancelButton,
      isAddMentionIrreversible: params.isAddMentionIrreversible,
      reverseButtons: params.reverseButtons,
      inputType: params.inputType,
      inputLabel: params.inputLabel,
      isInputRequired: params.isInputRequired,
      inputPlaceholder: params.inputPlaceholder,
      maxLength: params.maxLength,
      rowCount: params.rowCount,
      inputSelectOptions: params.inputSelectOptions,
      cancelBtnText: params.cancelBtnText ?? SWAL_TEXT_BTN_CANCEL,
      cancelBtnColor: params.cancelBtnColor ?? 'warn',
      confirmeBtnText: params.confirmeBtnText ?? SWAL_TEXT_BTN_CONFIRME,
      confirmeBtnColor: params.confirmeBtnColor ?? 'primary',
    };

    const confirmationSubject = new Subject<any>();
    const response = { isConfirmed: false, value: undefined };

    const dialogRef = this.openDialogSm(AlertConfirmationComponent, data);
    this.subs.sink = dialogRef.afterClosed().subscribe({
      next: value => {
        if (value) {
          response.isConfirmed = true;
          response.value = value != '#' ? value : undefined;
          confirmationSubject.next(response);
          confirmationSubject.complete();
        }
      },
      error: () => {
        confirmationSubject.next(response);
        confirmationSubject.complete();
      },
    });

    return confirmationSubject.asObservable();
  }

  public afficherAlertMessage(
    titre: string,
    message: string,
    icon?: 'success' | 'error' | 'warning' | 'info' | 'question',
    transValue?: any,
  ): Observable<any> {
    return this.afficherAlertConfirmation({ titre: titre, message: message, icon: icon, messageTransValue: transValue });
  }

  public AfficherAlertErrorBackend(error: any, message?: string): void {
    let msg = error.headers.get(HEADER_PREFIX + 'Msg');
    if (typeof msg === 'object' && msg !== null) {
      msg = JSON.stringify(msg);
    }
    this.afficherAlertMessage(MESSAGE_TITLE_ERROR, `<span class="text-danger">${msg ?? message ?? ''}<br/></span>`, 'error');
  }

  public AfficherAlertErrorContactAdmin(): void {
    this.afficherAlertMessage(MESSAGE_TITLE_ERROR, `<span class="text-danger">${MESSAGE_SERVER_ERROR_CONTACT_ADMIN}</span>`, 'error');
  }

  /**
   * Convertit une chaîne contenant un tableau d'objets JSON en un tableau de Record<string, any>
   * @param jsonString La chaîne à parser
   * @returns Un tableau de Record<string, any>
   */
  public parseJsonArrayString(jsonString: string): Record<string, any>[] {
    try {
      const parsed = JSON.parse(jsonString);
      if (Array.isArray(parsed)) {
        return parsed as Record<string, any>[];
      }
      return [];
    } catch {
      return [];
    }
  }

  public storeFiltersInLocalStorage(key: string, filters: Record<string, any> | undefined): void {
    if (filters) {
      localStorage.setItem(key, JSON.stringify(filters));
    } else {
      localStorage.removeItem(key);
    }
  }

  public getFiltersFromLocalStorage(key: string): Record<string, any> | undefined {
    const stored = localStorage.getItem(key);
    if (stored) {
      try {
        return JSON.parse(stored) as Record<string, any>;
      } catch {
        return undefined;
      }
    }
    return undefined;
  }

  public getInstantTranslate(
    label?: string | null,
    variables?: {
      [key: string]: string;
    },
  ): string {
    if (!label) {
      return '';
    }
    return this.translateService.instant(label, variables) as string;
  }

  public downloadCsvFileFromBlob(res: HttpResponse<Blob>, defaultFileName: string): void {
    try {
      const disposition = res.headers.get('content-disposition') ?? res.headers.get('Content-Disposition');
      const filename = this.getFilenameFromContentDisposition(disposition) ?? defaultFileName;
      const contentType = res.headers.get('content-type') ?? 'text/csv;charset=utf-8';
      const blob = new Blob([res.body as Blob], { type: contentType });

      // create temporary URL and anchor to trigger download
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = filename;
      // required for firefox
      document.body.appendChild(a);
      a.click();
      a.remove();
      window.URL.revokeObjectURL(url);
    } catch (err) {
      // fallback: open blob in new window/tab
      const url = window.URL.createObjectURL(res.body as Blob);
      window.open(url);
      window.URL.revokeObjectURL(url);
    }
  }

  /**
   * Extrait le nom de fichier de l'en-tête Content-Disposition
   * @param disposition La valeur de l'en-tête Content-Disposition
   * @returns Le nom de fichier extrait ou null si non trouvé
   */
  public getFilenameFromContentDisposition(disposition: string | null): string | null {
    if (!disposition) {
      return null;
    }
    // Try filename*=UTF-8''encoded and filename="name"
    // Example: attachment; filename="data.csv"
    // Example: attachment; filename*=UTF-8''data%20set.csv
    const filenameStarMatch = /filename\*=(?:[\w'-]+)?''([^;\n\r]+)/i.exec(disposition);
    const filenameStar = filenameStarMatch?.[1];
    if (filenameStar) {
      try {
        return decodeURIComponent(filenameStar.replace(/"/g, ''));
      } catch (e) {
        return filenameStar.replace(/"/g, '');
      }
    }

    const filenameMatch = /filename="?([^";]+)"?/i.exec(disposition);
    const filename = filenameMatch?.[1];
    if (filename) {
      return filename;
    }
    return null;
  }

  public getOptionProfil(listProfiles: IProfil[]): any[] {
    return listProfiles.filter(({ id, nom }) => id !== 1 && id !== 2 && nom !== 'SuperAdmin').map(({ id, nom }) => ({ id, libelle: nom }));
  }

  private openDialogWithWidth<T, D = any, R = any>(param: { component: ComponentType<T>; data?: {}; width?: string }): MatDialogRef<T, R> {
    const direction = this.getCurrentDirection();
    return this.dialog.open(param.component, {
      data: param.data,
      minWidth: param.width,
      direction: direction,
    });
  }

  private getCurrentDirection(): Direction {
    let tempDirection: Direction;
    if (localStorage.getItem('isRtl') === 'true') {
      tempDirection = 'rtl';
    } else {
      tempDirection = 'ltr';
    }
    return tempDirection;
  }

  private showNotifSnackbar(panelClass: string, text: string, duration?: number): void {
    this.snackBar.open(text, '', {
      duration: duration ?? SNACKBAR_DURATION,
      verticalPosition: 'top',
      horizontalPosition: 'center',
      panelClass: panelClass,
    });
  }
}
