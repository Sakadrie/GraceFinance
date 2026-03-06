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
import { TransfertService } from '../service/transfert.service';
import { ITransfert } from '../../../shared/model/referentiel/transfert.model';
import { TransfertFormGroup, TransfertFormService } from './transfert-form.service';

@Component({
  selector: 'jhi-transfert-update',
  templateUrl: './transfert-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class TransfertUpdateComponent implements OnInit {
  isSaving = false;
  transfert: ITransfert | null = null;

  entiteFinancieresSharedCollection: IEntiteFinanciere[] = [];
  caissesSharedCollection: ICaisse[] = [];

  protected transfertService = inject(TransfertService);
  protected transfertFormService = inject(TransfertFormService);
  protected entiteFinanciereService = inject(EntiteFinanciereService);
  protected caisseService = inject(CaisseService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: TransfertFormGroup = this.transfertFormService.createTransfertFormGroup();

  compareEntiteFinanciere = (o1: IEntiteFinanciere | null, o2: IEntiteFinanciere | null): boolean =>
    this.entiteFinanciereService.compareEntiteFinanciere(o1, o2);

  compareCaisse = (o1: ICaisse | null, o2: ICaisse | null): boolean => this.caisseService.compareCaisse(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transfert }) => {
      this.transfert = transfert;
      if (transfert) {
        this.updateForm(transfert);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const transfert = this.transfertFormService.getTransfert(this.editForm);
    if (transfert.id !== null) {
      this.subscribeToSaveResponse(this.transfertService.update(transfert));
    } else {
      this.subscribeToSaveResponse(this.transfertService.create(transfert));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransfert>>): void {
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

  protected updateForm(transfert: ITransfert): void {
    this.transfert = transfert;
    this.transfertFormService.resetForm(this.editForm, transfert);

    this.entiteFinancieresSharedCollection = this.entiteFinanciereService.addEntiteFinanciereToCollectionIfMissing<IEntiteFinanciere>(
      this.entiteFinancieresSharedCollection,
      transfert.entiteFinanciereSource,
    );
    this.caissesSharedCollection = this.caisseService.addCaisseToCollectionIfMissing<ICaisse>(
      this.caissesSharedCollection,
      transfert.caisseSource,
      transfert.caisseDestination,
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
            this.transfert?.entiteFinanciereSource,
          ),
        ),
      )
      .subscribe((entiteFinancieres: IEntiteFinanciere[]) => (this.entiteFinancieresSharedCollection = entiteFinancieres));

    this.caisseService
      .query()
      .pipe(map((res: HttpResponse<ICaisse[]>) => res.body ?? []))
      .pipe(
        map((caisses: ICaisse[]) =>
          this.caisseService.addCaisseToCollectionIfMissing<ICaisse>(
            caisses,
            this.transfert?.caisseSource,
            this.transfert?.caisseDestination,
          ),
        ),
      )
      .subscribe((caisses: ICaisse[]) => (this.caissesSharedCollection = caisses));
  }
}
