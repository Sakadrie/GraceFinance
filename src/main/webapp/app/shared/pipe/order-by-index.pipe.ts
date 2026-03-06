import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'orderByIndex',
  standalone: true,
})
export class OrderByIndexPipe implements PipeTransform {
  transform(inputs: any[]): any[] {
    if (!Array.isArray(inputs)) {
      return inputs;
    }
    return [...inputs].sort((a, b) => (a.index ?? 0) - (b.index ?? 0));
  }
}
