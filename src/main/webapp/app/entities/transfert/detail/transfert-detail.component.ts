import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { ITransfert } from '../transfert.model';

@Component({
  selector: 'jhi-transfert-detail',
  templateUrl: './transfert-detail.component.html',
  imports: [SharedModule, RouterModule, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class TransfertDetailComponent {
  transfert = input<ITransfert | null>(null);

  previousState(): void {
    window.history.back();
  }
}
