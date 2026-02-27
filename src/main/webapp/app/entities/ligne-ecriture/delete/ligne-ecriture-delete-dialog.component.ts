import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ILigneEcriture } from '../ligne-ecriture.model';
import { LigneEcritureService } from '../service/ligne-ecriture.service';

@Component({
  templateUrl: './ligne-ecriture-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class LigneEcritureDeleteDialogComponent {
  ligneEcriture?: ILigneEcriture;

  protected ligneEcritureService = inject(LigneEcritureService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ligneEcritureService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
