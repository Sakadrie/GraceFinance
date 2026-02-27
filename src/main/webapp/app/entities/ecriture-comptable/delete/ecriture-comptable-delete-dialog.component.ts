import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IEcritureComptable } from '../ecriture-comptable.model';
import { EcritureComptableService } from '../service/ecriture-comptable.service';

@Component({
  templateUrl: './ecriture-comptable-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class EcritureComptableDeleteDialogComponent {
  ecritureComptable?: IEcritureComptable;

  protected ecritureComptableService = inject(EcritureComptableService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ecritureComptableService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
