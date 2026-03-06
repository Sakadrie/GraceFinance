/* eslint-disable @typescript-eslint/no-redundant-type-constituents */
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  standalone: true,
  name: 'formatBytes',
})
export default class FormatBytesPipe implements PipeTransform {
  transform(bytes: any | null | undefined): any {
    if (!bytes) {
      return bytes;
    }

    if (isNaN(bytes)) {
      return bytes;
    }
    if (bytes === 0) {
      return '0 Octets';
    }
    const k = 1024;
    const sizes = ['Octets', 'Ko', 'Mo', 'Go', 'To'];
    const i = Math.floor(Math.log(bytes) / Math.log(k));
    const size = parseFloat((bytes / Math.pow(k, i)).toFixed(2));
    return `${size} ${sizes[i]}`;
  }
}
