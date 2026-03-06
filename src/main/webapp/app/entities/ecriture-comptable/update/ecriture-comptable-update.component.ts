import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEcritureComptable } from '../../../shared/model/principal/ecriture-comptable.model';
import { EcritureComptableService } from '../service/ecriture-comptable.service';
import { EcritureComptableFormGroup, EcritureComptableFormService } from './ecriture-comptable-form.service';

@Component({
  selector: 'jhi-ecriture-comptable-update',
  templateUrl: './ecriture-comptable-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class EcritureComptableUpdateComponent implements OnInit {
  isSaving = false;
  ecritureComptable: IEcritureComptable | null = null;

  protected ecritureComptableService = inject(EcritureComptableService);
  protected ecritureComptableFormService = inject(EcritureComptableFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: EcritureComptableFormGroup = this.ecritureComptableFormService.createEcritureComptableFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ecritureComptable }) => {
      this.ecritureComptable = ecritureComptable;
      if (ecritureComptable) {
        this.updateForm(ecritureComptable);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ecritureComptable = this.ecritureComptableFormService.getEcritureComptable(this.editForm);
    if (ecritureComptable.id !== null) {
      this.subscribeToSaveResponse(this.ecritureComptableService.update(ecritureComptable));
    } else {
      this.subscribeToSaveResponse(this.ecritureComptableService.create(ecritureComptable));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEcritureComptable>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(ecritureComptable: IEcritureComptable): void {
    this.ecritureComptable = ecritureComptable;
    this.ecritureComptableFormService.resetForm(this.editForm, ecritureComptable);
  }
}
