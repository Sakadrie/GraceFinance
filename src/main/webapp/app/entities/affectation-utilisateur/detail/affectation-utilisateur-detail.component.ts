import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { FormatMediumDatePipe } from 'app/shared/date';
import { IAffectationUtilisateur } from '../../../shared/model/security/affectation-utilisateur.model';

@Component({
  selector: 'jhi-affectation-utilisateur-detail',
  templateUrl: './affectation-utilisateur-detail.component.html',
  imports: [SharedModule, RouterModule, FormatMediumDatePipe],
})
export class AffectationUtilisateurDetailComponent {
  affectationUtilisateur = input<IAffectationUtilisateur | null>(null);

  previousState(): void {
    window.history.back();
  }
}
