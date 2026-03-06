import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IDepense } from '../../../shared/model/principal/depense.model';

@Component({
  selector: 'jhi-depense-detail',
  templateUrl: './depense-detail.component.html',
  imports: [SharedModule, RouterModule, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class DepenseDetailComponent {
  depense = input<IDepense | null>(null);

  previousState(): void {
    window.history.back();
  }
}
