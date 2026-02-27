import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { ICompteComptable } from '../compte-comptable.model';

@Component({
  selector: 'jhi-compte-comptable-detail',
  templateUrl: './compte-comptable-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class CompteComptableDetailComponent {
  compteComptable = input<ICompteComptable | null>(null);

  previousState(): void {
    window.history.back();
  }
}
