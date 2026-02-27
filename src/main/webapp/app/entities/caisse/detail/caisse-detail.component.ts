import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { ICaisse } from '../caisse.model';

@Component({
  selector: 'jhi-caisse-detail',
  templateUrl: './caisse-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class CaisseDetailComponent {
  caisse = input<ICaisse | null>(null);

  previousState(): void {
    window.history.back();
  }
}
