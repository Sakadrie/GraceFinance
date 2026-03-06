import { Pipe, PipeTransform } from '@angular/core';

type SeparatorPair = {
  thousand: string;
  decimal: string;
};

@Pipe({
  name: 'numberDivider',
})
export class NumberDividerPipe implements PipeTransform {
  transform(
    value?: number | string | null,
    thousandSeparator: '.' | ',' = '.',
    decimalSeparator: ',' | '.' = ',',
    decimalDigits = 2,
  ): string {
    if (value === null || value === undefined || value === '') {
      return '';
    }

    const separators: SeparatorPair[] = [
      { thousand: '.', decimal: ',' },
      { thousand: ',', decimal: '.' },
    ];

    // Validate separator pair
    const isValidPair = separators.some(s => s.thousand === thousandSeparator && s.decimal === decimalSeparator);
    if (!isValidPair) {
      throw new Error('Invalid separator pair');
    }

    // Parse value to float
    const num = typeof value === 'number' ? value : parseFloat(value.toString().replace(/,/g, '.'));
    if (isNaN(num)) {
      return '';
    }

    // Format number with fixed decimals
    // eslint-disable-next-line prefer-const
    let [integer, fraction = ''] = num.toFixed(decimalDigits).split('.');

    // Add thousand separator
    integer = integer.replace(/\B(?=(\d{3})+(?!\d))/g, thousandSeparator);

    // Compose final string
    if (decimalDigits > 0) {
      return integer + decimalSeparator + fraction;
    } else {
      return integer;
    }
  }
}
