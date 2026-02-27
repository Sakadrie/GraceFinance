import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEntiteFinanciere } from 'app/entities/entite-financiere/entite-financiere.model';
import { EntiteFinanciereService } from 'app/entities/entite-financiere/service/entite-financiere.service';
import { ICaisse } from 'app/entities/caisse/caisse.model';
import { CaisseService } from 'app/entities/caisse/service/caisse.service';
import { ICategorie } from 'app/entities/categorie/categorie.model';
import { CategorieService } from 'app/entities/categorie/service/categorie.service';
import { DepenseService } from '../service/depense.service';
import { IDepense } from '../depense.model';
import { DepenseFormGroup, DepenseFormService } from './depense-form.service';

@Component({
  selector: 'jhi-depense-update',
  templateUrl: './depense-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DepenseUpdateComponent implements OnInit {
  isSaving = false;
  depense: IDepense | null = null;

  entiteFinancieresSharedCollection: IEntiteFinanciere[] = [];
  caissesSharedCollection: ICaisse[] = [];
  categoriesSharedCollection: ICategorie[] = [];

  protected depenseService = inject(DepenseService);
  protected depenseFormService = inject(DepenseFormService);
  protected entiteFinanciereService = inject(EntiteFinanciereService);
  protected caisseService = inject(CaisseService);
  protected categorieService = inject(CategorieService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: DepenseFormGroup = this.depenseFormService.createDepenseFormGroup();

  compareEntiteFinanciere = (o1: IEntiteFinanciere | null, o2: IEntiteFinanciere | null): boolean =>
    this.entiteFinanciereService.compareEntiteFinanciere(o1, o2);

  compareCaisse = (o1: ICaisse | null, o2: ICaisse | null): boolean => this.caisseService.compareCaisse(o1, o2);

  compareCategorie = (o1: ICategorie | null, o2: ICategorie | null): boolean => this.categorieService.compareCategorie(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ depense }) => {
      this.depense = depense;
      if (depense) {
        this.updateForm(depense);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const depense = this.depenseFormService.getDepense(this.editForm);
    if (depense.id !== null) {
      this.subscribeToSaveResponse(this.depenseService.update(depense));
    } else {
      this.subscribeToSaveResponse(this.depenseService.create(depense));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDepense>>): void {
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

  protected updateForm(depense: IDepense): void {
    this.depense = depense;
    this.depenseFormService.resetForm(this.editForm, depense);

    this.entiteFinancieresSharedCollection = this.entiteFinanciereService.addEntiteFinanciereToCollectionIfMissing<IEntiteFinanciere>(
      this.entiteFinancieresSharedCollection,
      depense.entiteFinanciere,
    );
    this.caissesSharedCollection = this.caisseService.addCaisseToCollectionIfMissing<ICaisse>(this.caissesSharedCollection, depense.caisse);
    this.categoriesSharedCollection = this.categorieService.addCategorieToCollectionIfMissing<ICategorie>(
      this.categoriesSharedCollection,
      depense.categorie,
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
            this.depense?.entiteFinanciere,
          ),
        ),
      )
      .subscribe((entiteFinancieres: IEntiteFinanciere[]) => (this.entiteFinancieresSharedCollection = entiteFinancieres));

    this.caisseService
      .query()
      .pipe(map((res: HttpResponse<ICaisse[]>) => res.body ?? []))
      .pipe(map((caisses: ICaisse[]) => this.caisseService.addCaisseToCollectionIfMissing<ICaisse>(caisses, this.depense?.caisse)))
      .subscribe((caisses: ICaisse[]) => (this.caissesSharedCollection = caisses));

    this.categorieService
      .query()
      .pipe(map((res: HttpResponse<ICategorie[]>) => res.body ?? []))
      .pipe(
        map((categories: ICategorie[]) =>
          this.categorieService.addCategorieToCollectionIfMissing<ICategorie>(categories, this.depense?.categorie),
        ),
      )
      .subscribe((categories: ICategorie[]) => (this.categoriesSharedCollection = categories));
  }
}
