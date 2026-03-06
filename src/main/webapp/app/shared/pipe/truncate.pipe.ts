/* eslint-disable @typescript-eslint/no-redundant-type-constituents */
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  standalone: true,
  name: 'truncate',
})
export default class TruncatePipe implements PipeTransform {
  transform(value: string | unknown, limit: number, iswithSpace?: boolean): any {
    if (!value) {
      return '';
    }
    if (typeof value === 'string') {
      const val: string = this.decodeHtmlEntities(value);
      if (iswithSpace) {
        if (val.length <= limit) {
          return val;
        }
        const suite = val.slice(limit);
        const firstSpaceIndexAfterLimit = suite.indexOf(' ');
        return firstSpaceIndexAfterLimit === -1 ? val : val.slice(0, limit + firstSpaceIndexAfterLimit) + '...';
      }
      return val.length < limit ? val : val.slice(0, limit) + '...';
    } else {
      return value;
    }
  }

  private decodeHtmlEntities(text: string): string {
    const textArea = document.createElement('textarea');
    textArea.innerHTML = text;
    return textArea.value;
  }
}
