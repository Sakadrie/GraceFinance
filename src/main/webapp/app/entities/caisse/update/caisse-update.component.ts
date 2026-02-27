import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEntiteFinanciere } from 'app/entities/entite-financiere/entite-financiere.model';
import { EntiteFinanciereService } from 'app/entities/entite-financiere/service/entite-financiere.service';
import { ICaisse } from '../caisse.model';
import { CaisseService } from '../service/caisse.service';
import { CaisseFormGroup, CaisseFormService } from './caisse-form.service';

@Component({
  selector: 'jhi-caisse-update',
  templateUrl: './caisse-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CaisseUpdateComponent implements OnInit {
  isSaving = false;
  caisse: ICaisse | null = null;

  entiteFinancieresSharedCollection: IEntiteFinanciere[] = [];

  protected caisseService = inject(CaisseService);
  protected caisseFormService = inject(CaisseFormService);
  protected entiteFinanciereService = inject(EntiteFinanciereService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CaisseFormGroup = this.caisseFormService.createCaisseFormGroup();

  compareEntiteFinanciere = (o1: IEntiteFinanciere | null, o2: IEntiteFinanciere | null): boolean =>
    this.entiteFinanciereService.compareEntiteFinanciere(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ caisse }) => {
      this.caisse = caisse;
      if (caisse) {
        this.updateForm(caisse);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const caisse = this.caisseFormService.getCaisse(this.editForm);
    if (caisse.id !== null) {
      this.subscribeToSaveResponse(this.caisseService.update(caisse));
    } else {
      this.subscribeToSaveResponse(this.caisseService.create(caisse));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICaisse>>): void {
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

  protected updateForm(caisse: ICaisse): void {
    this.caisse = caisse;
    this.caisseFormService.resetForm(this.editForm, caisse);

    this.entiteFinancieresSharedCollection = this.entiteFinanciereService.addEntiteFinanciereToCollectionIfMissing<IEntiteFinanciere>(
      this.entiteFinancieresSharedCollection,
      caisse.entiteFinanciere,
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
            this.caisse?.entiteFinanciere,
          ),
        ),
      )
      .subscribe((entiteFinancieres: IEntiteFinanciere[]) => (this.entiteFinancieresSharedCollection = entiteFinancieres));
  }
}
