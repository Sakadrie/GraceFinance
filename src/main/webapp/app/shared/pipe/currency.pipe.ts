import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'currency',
})
export class CurrencyPipe implements PipeTransform {
  transform(value?: number | string | null, currency = 'EUR', locale = 'fr-FR'): string {
    if (value === null || value === undefined || value === '') {
      return '';
    }
    const amount = typeof value === 'string' ? parseFloat(value) : value;
    if (isNaN(amount)) {
      return '';
    }
    return new Intl.NumberFormat(locale, {
      style: 'currency',
      currency,
      minimumFractionDigits: 2,
      maximumFractionDigits: 2,
    }).format(amount);
  }
}
