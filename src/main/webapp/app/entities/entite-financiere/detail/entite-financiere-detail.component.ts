import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IEntiteFinanciere } from '../../../shared/model/principal/entite-financiere.model';

@Component({
  selector: 'jhi-entite-financiere-detail',
  templateUrl: './entite-financiere-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class EntiteFinanciereDetailComponent {
  entiteFinanciere = input<IEntiteFinanciere | null>(null);

  previousState(): void {
    window.history.back();
  }
}
