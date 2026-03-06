import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEntiteFinanciere } from 'app/shared/model/principal/entite-financiere.model';
import { EntiteFinanciereService } from 'app/entities/entite-financiere/service/entite-financiere.service';
import { ICaisse } from 'app/shared/model/principal/caisse.model';
import { CaisseService } from 'app/entities/caisse/service/caisse.service';
import { ICategorie } from 'app/shared/model/referentiel/categorie.model';
import { CategorieService } from 'app/entities/categorie/service/categorie.service';
import { RecetteService } from '../service/recette.service';
import { IRecette } from '../../../shared/model/principal/recette.model';
import { RecetteFormGroup, RecetteFormService } from './recette-form.service';

@Component({
  selector: 'jhi-recette-update',
  templateUrl: './recette-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class RecetteUpdateComponent implements OnInit {
  isSaving = false;
  recette: IRecette | null = null;

  entiteFinancieresSharedCollection: IEntiteFinanciere[] = [];
  caissesSharedCollection: ICaisse[] = [];
  categoriesSharedCollection: ICategorie[] = [];

  protected recetteService = inject(RecetteService);
  protected recetteFormService = inject(RecetteFormService);
  protected entiteFinanciereService = inject(EntiteFinanciereService);
  protected caisseService = inject(CaisseService);
  protected categorieService = inject(CategorieService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: RecetteFormGroup = this.recetteFormService.createRecetteFormGroup();

  compareEntiteFinanciere = (o1: IEntiteFinanciere | null, o2: IEntiteFinanciere | null): boolean =>
    this.entiteFinanciereService.compareEntiteFinanciere(o1, o2);

  compareCaisse = (o1: ICaisse | null, o2: ICaisse | null): boolean => this.caisseService.compareCaisse(o1, o2);

  compareCategorie = (o1: ICategorie | null, o2: ICategorie | null): boolean => this.categorieService.compareCategorie(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ recette }) => {
      this.recette = recette;
      if (recette) {
        this.updateForm(recette);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const recette = this.recetteFormService.getRecette(this.editForm);
    if (recette.id !== null) {
      this.subscribeToSaveResponse(this.recetteService.update(recette));
    } else {
      this.subscribeToSaveResponse(this.recetteService.create(recette));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRecette>>): void {
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

  protected updateForm(recette: IRecette): void {
    this.recette = recette;
    this.recetteFormService.resetForm(this.editForm, recette);

    this.entiteFinancieresSharedCollection = this.entiteFinanciereService.addEntiteFinanciereToCollectionIfMissing<IEntiteFinanciere>(
      this.entiteFinancieresSharedCollection,
      recette.entiteFinanciere,
    );
    this.caissesSharedCollection = this.caisseService.addCaisseToCollectionIfMissing<ICaisse>(this.caissesSharedCollection, recette.caisse);
    this.categoriesSharedCollection = this.categorieService.addCategorieToCollectionIfMissing<ICategorie>(
      this.categoriesSharedCollection,
      recette.categorie,
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
            this.recette?.entiteFinanciere,
          ),
        ),
      )
      .subscribe((entiteFinancieres: IEntiteFinanciere[]) => (this.entiteFinancieresSharedCollection = entiteFinancieres));

    this.caisseService
      .query()
      .pipe(map((res: HttpResponse<ICaisse[]>) => res.body ?? []))
      .pipe(map((caisses: ICaisse[]) => this.caisseService.addCaisseToCollectionIfMissing<ICaisse>(caisses, this.recette?.caisse)))
      .subscribe((caisses: ICaisse[]) => (this.caissesSharedCollection = caisses));

    this.categorieService
      .query()
      .pipe(map((res: HttpResponse<ICategorie[]>) => res.body ?? []))
      .pipe(
        map((categories: ICategorie[]) =>
          this.categorieService.addCategorieToCollectionIfMissing<ICategorie>(categories, this.recette?.categorie),
        ),
      )
      .subscribe((categories: ICategorie[]) => (this.categoriesSharedCollection = categories));
  }
}
