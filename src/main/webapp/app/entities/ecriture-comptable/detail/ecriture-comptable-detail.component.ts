import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { FormatMediumDatePipe } from 'app/shared/date';
import { IEcritureComptable } from '../ecriture-comptable.model';

@Component({
  selector: 'jhi-ecriture-comptable-detail',
  templateUrl: './ecriture-comptable-detail.component.html',
  imports: [SharedModule, RouterModule, FormatMediumDatePipe],
})
export class EcritureComptableDetailComponent {
  ecritureComptable = input<IEcritureComptable | null>(null);

  previousState(): void {
    window.history.back();
  }
}
