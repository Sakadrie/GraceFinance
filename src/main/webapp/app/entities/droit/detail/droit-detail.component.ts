import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IDroit } from '../droit.model';

@Component({
  selector: 'jhi-droit-detail',
  templateUrl: './droit-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class DroitDetailComponent {
  droit = input<IDroit | null>(null);

  previousState(): void {
    window.history.back();
  }
}
