/* eslint-disable @typescript-eslint/no-unnecessary-type-assertion */
/* eslint-disable @typescript-eslint/no-inferrable-types */
/* eslint-disable @typescript-eslint/no-unnecessary-condition */
/* eslint-disable curly */
import { Pipe, PipeTransform } from '@angular/core';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';

type SortOrder = 'asc' | 'desc' | undefined;

// TODO: Le sort n'a pas l'air de fonctionner et est à revoir
@Pipe({ name: 'joinPropertyHtml' })
export class JoinPropertyHtmlPipe implements PipeTransform {
  constructor(private sanitizer: DomSanitizer) {}

  transform<T extends Record<string, unknown>, L extends string = 'list'>(
    value: T[] | Record<L, T[]>,
    propertyPath: string,
    separator: string = ', ',
    listProperty?: L,
    sortOrder?: SortOrder,
    sortPath?: string,
    spanClasses?: string | string[],
    separatorClasses?: string | string[],
    maxItems?: number,
    overflowIndicator: string = '…',
    overflowClasses?: string | string[],
    showMoreCount?: boolean,
  ): SafeHtml {
    if (!value || !propertyPath) return '';

    // 1) Source de données
    const arr: T[] = Array.isArray(value)
      ? value
      : (() => {
          const key = listProperty ?? ('list' as L);
          const maybe = (value as Record<L, T[]>)[key];
          return Array.isArray(maybe) ? maybe : [];
        })();

    if (arr.length === 0) return '';

    // 2) Extract (val pour l’affichage, key pour le tri)
    let rows = arr
      .map(item => {
        const val = this.getByPath(item, propertyPath);
        const key = sortPath ? this.getByPath(item, sortPath) : val;
        return { val, key };
      })
      .filter(r => r.val !== undefined && r.val !== null && r.val !== '');

    if (rows.length === 0) return '';

    // 3) Tri optionnel
    if (sortOrder) {
      rows.sort((a, b) => this.compare(a.key, b.key, sortOrder));
    }

    // 4) Limite et calcul du “reste”
    let hiddenCount = 0;
    if (maxItems && maxItems > 0 && rows.length > maxItems) {
      hiddenCount = rows.length - maxItems;
      rows = rows.slice(0, maxItems);
    }

    // 5) Construit HTML des items et du séparateur
    const itemClassAttr = Array.isArray(spanClasses) ? spanClasses.join(' ') : (spanClasses ?? '');
    const sepClassAttr = Array.isArray(separatorClasses) ? separatorClasses.join(' ') : (separatorClasses ?? '');

    const itemHtmlList = rows.map(r => `<span class="${itemClassAttr}">${this.escapeHtml(String(r.val))}</span>`);

    const sepHtmlRaw = this.escapeHtml(separator);
    const sepHtml = sepClassAttr ? `<span class="${sepClassAttr}">${sepHtmlRaw}</span>` : sepHtmlRaw;

    // 6) Ajout éventuel de l’ellipse “…” (avec (+N) si demandé)
    let html = itemHtmlList.join(sepHtml);

    if (hiddenCount > 0) {
      const overflowClassAttr = Array.isArray(overflowClasses) ? overflowClasses.join(' ') : (overflowClasses ?? '');
      const moreText = showMoreCount ? ` (+${hiddenCount})` : '';
      const overRaw = this.escapeHtml(overflowIndicator + moreText);
      const overHtml = overflowClassAttr ? `<span class="${overflowClassAttr}">${overRaw}</span>` : overRaw;

      // On préfixe l’ellipse par le séparateur pour conserver le rythme visuel
      html += sepHtml + overHtml;
    }

    return this.sanitizer.bypassSecurityTrustHtml(html);
  }

  private getByPath(obj: unknown, path: string): unknown {
    if (!obj || !path) return undefined;
    const normalized = path
      .replace(/\[(\d+)\]/g, '.$1') // a[0] -> a.0
      .replace(/\[['"]([^'"]+)['"]\]/g, '.$1'); // a['x'] -> a.x
    const tokens = normalized.split('.').filter(Boolean);
    let cur: any = obj;
    for (const key of tokens) {
      if (cur == null) return undefined;
      cur = cur[key];
    }
    return cur;
  }

  private compare(a: unknown, b: unknown, order: 'asc' | 'desc'): number {
    let res: number;
    if (typeof a === 'number' && typeof b === 'number') {
      res = a - b;
    } else {
      res = String(a).localeCompare(String(b), undefined, { numeric: true, sensitivity: 'base' });
    }
    return order === 'desc' ? -res : res;
  }

  private escapeHtml(str: string): string {
    return str.replace(/[&<>"']/g, m => ({ '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#039;' })[m] as string);
  }
}
