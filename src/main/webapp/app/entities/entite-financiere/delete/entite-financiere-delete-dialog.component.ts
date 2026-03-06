import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IEntiteFinanciere } from '../../../shared/model/principal/entite-financiere.model';
import { EntiteFinanciereService } from '../service/entite-financiere.service';

@Component({
  templateUrl: './entite-financiere-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class EntiteFinanciereDeleteDialogComponent {
  entiteFinanciere?: IEntiteFinanciere;

  protected entiteFinanciereService = inject(EntiteFinanciereService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.entiteFinanciereService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
