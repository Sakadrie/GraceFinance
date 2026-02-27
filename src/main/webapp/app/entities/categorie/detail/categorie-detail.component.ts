import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { ICategorie } from '../categorie.model';

@Component({
  selector: 'jhi-categorie-detail',
  templateUrl: './categorie-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class CategorieDetailComponent {
  categorie = input<ICategorie | null>(null);

  previousState(): void {
    window.history.back();
  }
}
