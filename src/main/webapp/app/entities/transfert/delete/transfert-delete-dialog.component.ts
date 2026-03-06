import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ITransfert } from '../../../shared/model/referentiel/transfert.model';
import { TransfertService } from '../service/transfert.service';

@Component({
  templateUrl: './transfert-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class TransfertDeleteDialogComponent {
  transfert?: ITransfert;

  protected transfertService = inject(TransfertService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.transfertService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
