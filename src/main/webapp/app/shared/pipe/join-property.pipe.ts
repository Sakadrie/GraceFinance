/* eslint-disable curly */
/* eslint-disable @typescript-eslint/no-unnecessary-condition */
/* eslint-disable @typescript-eslint/no-inferrable-types */
import { Pipe, PipeTransform } from '@angular/core';

type SortOrder = 'asc' | 'desc' | undefined;

@Pipe({ name: 'joinProperty' })
export class JoinPropertyPipe implements PipeTransform {
  // ——— Surcharges pour meilleure inférence ———
  transform(
    value: Record<string, unknown>[],
    propertyPath: string,
    separator?: string,
    listProperty?: never,
    sortOrder?: SortOrder,
    sortPath?: string,
  ): string;

  transform<T extends Record<string, unknown>, L extends string>(
    value: Record<L, T[]>,
    propertyPath: string,
    separator: string | undefined,
    listProperty: L,
    sortOrder?: SortOrder,
    sortPath?: string,
  ): string;

  // ——— Implémentation ———
  transform<T extends Record<string, unknown>, L extends string = 'list'>(
    value: T[] | Record<L, T[]>,
    propertyPath: string,
    separator: string = ', ',
    listProperty?: L,
    sortOrder?: SortOrder,
    sortPath?: string,
  ): string {
    if (!value || !propertyPath) return '';

    // 1) Source des données
    const arr: T[] = Array.isArray(value)
      ? value
      : (() => {
          const key = listProperty ?? ('list' as L);
          // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
          const maybe = (value as Record<L, T[]>)[key];
          return Array.isArray(maybe) ? maybe : [];
        })();

    if (arr.length === 0) return '';

    // 2) Extraction des valeurs et de la clé de tri (indépendante)
    const rows = arr
      .map(item => {
        const val = this.getByPath(item, propertyPath);
        const key = sortPath ? this.getByPath(item, sortPath) : val;
        return { val, key };
      })
      // on ignore les éléments sans valeur à concaténer
      .filter(r => r.val !== undefined && r.val !== null && r.val !== '');

    if (rows.length === 0) return '';

    // 3) Tri optionnel (sur key)
    if (sortOrder) {
      rows.sort((a, b) => this.compare(a.key, b.key, sortOrder));
    }

    // 4) Concaténation finale (sur val)
    return rows.map(r => String(r.val)).join(separator);
  }

  /**
   * Récupère une valeur par chemin profond.
   * Chemins supportés:
   *  - Dot path: "a.b.c"
   *  - Brackets: "a[0].b" ou "a['clé']"
   */
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

  /**
   * Comparateur robuste:
   * - numérique si les deux sont des nombres,
   * - sinon "localeCompare" (numeric: true, insensible à la casse).
   */
  private compare(a: unknown, b: unknown, order: 'asc' | 'desc'): number {
    let res: number;

    if (typeof a === 'number' && typeof b === 'number') {
      res = a - b;
    } else {
      const sa = String(a);
      const sb = String(b);
      res = sa.localeCompare(sb, undefined, { numeric: true, sensitivity: 'base' });
    }

    return order === 'desc' ? -res : res;
  }
}
