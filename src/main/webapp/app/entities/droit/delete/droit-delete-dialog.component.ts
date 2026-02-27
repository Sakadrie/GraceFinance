import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IDroit } from '../droit.model';
import { DroitService } from '../service/droit.service';

@Component({
  templateUrl: './droit-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class DroitDeleteDialogComponent {
  droit?: IDroit;

  protected droitService = inject(DroitService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.droitService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
