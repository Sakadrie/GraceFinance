import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAffectationUtilisateur } from '../../../shared/model/security/affectation-utilisateur.model';
import { AffectationUtilisateurService } from '../service/affectation-utilisateur.service';

@Component({
  templateUrl: './affectation-utilisateur-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AffectationUtilisateurDeleteDialogComponent {
  affectationUtilisateur?: IAffectationUtilisateur;

  protected affectationUtilisateurService = inject(AffectationUtilisateurService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.affectationUtilisateurService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
