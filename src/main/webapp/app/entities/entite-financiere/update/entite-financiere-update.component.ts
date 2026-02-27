import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEntiteFinanciere } from '../entite-financiere.model';
import { EntiteFinanciereService } from '../service/entite-financiere.service';
import { EntiteFinanciereFormGroup, EntiteFinanciereFormService } from './entite-financiere-form.service';

@Component({
  selector: 'jhi-entite-financiere-update',
  templateUrl: './entite-financiere-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class EntiteFinanciereUpdateComponent implements OnInit {
  isSaving = false;
  entiteFinanciere: IEntiteFinanciere | null = null;

  entiteFinancieresSharedCollection: IEntiteFinanciere[] = [];

  protected entiteFinanciereService = inject(EntiteFinanciereService);
  protected entiteFinanciereFormService = inject(EntiteFinanciereFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: EntiteFinanciereFormGroup = this.entiteFinanciereFormService.createEntiteFinanciereFormGroup();

  compareEntiteFinanciere = (o1: IEntiteFinanciere | null, o2: IEntiteFinanciere | null): boolean =>
    this.entiteFinanciereService.compareEntiteFinanciere(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ entiteFinanciere }) => {
      this.entiteFinanciere = entiteFinanciere;
      if (entiteFinanciere) {
        this.updateForm(entiteFinanciere);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const entiteFinanciere = this.entiteFinanciereFormService.getEntiteFinanciere(this.editForm);
    if (entiteFinanciere.id !== null) {
      this.subscribeToSaveResponse(this.entiteFinanciereService.update(entiteFinanciere));
    } else {
      this.subscribeToSaveResponse(this.entiteFinanciereService.create(entiteFinanciere));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEntiteFinanciere>>): void {
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

  protected updateForm(entiteFinanciere: IEntiteFinanciere): void {
    this.entiteFinanciere = entiteFinanciere;
    this.entiteFinanciereFormService.resetForm(this.editForm, entiteFinanciere);

    this.entiteFinancieresSharedCollection = this.entiteFinanciereService.addEntiteFinanciereToCollectionIfMissing<IEntiteFinanciere>(
      this.entiteFinancieresSharedCollection,
      ...(entiteFinanciere.egliseLiees ?? []),
      ...(entiteFinanciere.structureLiees ?? []),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.entiteFinanciereService
      .query()
      .pipe(map((res: HttpResponse<IEntiteFinanciere[]>) => res.body ?? []))
      .pipe(
        map((entiteFinancieres: IEntiteFinanciere[]) =>
          this.entiteFinanciereService.addEntiteFinanciereToCollectionIfMissing<IEntiteFinanciere>(
            entiteFinancieres,
            ...(this.entiteFinanciere?.egliseLiees ?? []),
            ...(this.entiteFinanciere?.structureLiees ?? []),
          ),
        ),
      )
      .subscribe((entiteFinancieres: IEntiteFinanciere[]) => (this.entiteFinancieresSharedCollection = entiteFinancieres));
  }
}
