import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'percent',
})
export class PercentPipe implements PipeTransform {
  transform(value?: number, fractionDigits = 2): string {
    if (value == null || isNaN(value)) {
      return '';
    }
    return `${(value * 100).toFixed(fractionDigits)}%`;
  }
}
