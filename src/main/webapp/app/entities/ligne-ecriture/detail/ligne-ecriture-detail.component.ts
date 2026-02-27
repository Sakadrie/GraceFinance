import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { ILigneEcriture } from '../ligne-ecriture.model';

@Component({
  selector: 'jhi-ligne-ecriture-detail',
  templateUrl: './ligne-ecriture-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class LigneEcritureDetailComponent {
  ligneEcriture = input<ILigneEcriture | null>(null);

  previousState(): void {
    window.history.back();
  }
}
