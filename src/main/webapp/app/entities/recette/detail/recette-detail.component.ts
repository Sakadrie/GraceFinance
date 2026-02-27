import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { FormatMediumDatePipe } from 'app/shared/date';
import { IRecette } from '../recette.model';

@Component({
  selector: 'jhi-recette-detail',
  templateUrl: './recette-detail.component.html',
  imports: [SharedModule, RouterModule, FormatMediumDatePipe],
})
export class RecetteDetailComponent {
  recette = input<IRecette | null>(null);

  previousState(): void {
    window.history.back();
  }
}
