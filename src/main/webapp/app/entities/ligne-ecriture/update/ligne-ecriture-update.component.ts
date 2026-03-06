import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEcritureComptable } from 'app/shared/model/principal/ecriture-comptable.model';
import { EcritureComptableService } from 'app/entities/ecriture-comptable/service/ecriture-comptable.service';
import { ICompteComptable } from 'app/shared/model/principal/compte-comptable.model';
import { CompteComptableService } from 'app/entities/compte-comptable/service/compte-comptable.service';
import { LigneEcritureService } from '../service/ligne-ecriture.service';
import { ILigneEcriture } from '../ligne-ecriture.model';
import { LigneEcritureFormGroup, LigneEcritureFormService } from './ligne-ecriture-form.service';

@Component({
  selector: 'jhi-ligne-ecriture-update',
  templateUrl: './ligne-ecriture-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class LigneEcritureUpdateComponent implements OnInit {
  isSaving = false;
  ligneEcriture: ILigneEcriture | null = null;

  ecritureComptablesSharedCollection: IEcritureComptable[] = [];
  compteComptablesSharedCollection: ICompteComptable[] = [];

  protected ligneEcritureService = inject(LigneEcritureService);
  protected ligneEcritureFormService = inject(LigneEcritureFormService);
  protected ecritureComptableService = inject(EcritureComptableService);
  protected compteComptableService = inject(CompteComptableService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: LigneEcritureFormGroup = this.ligneEcritureFormService.createLigneEcritureFormGroup();

  compareEcritureComptable = (o1: IEcritureComptable | null, o2: IEcritureComptable | null): boolean =>
    this.ecritureComptableService.compareEcritureComptable(o1, o2);

  compareCompteComptable = (o1: ICompteComptable | null, o2: ICompteComptable | null): boolean =>
    this.compteComptableService.compareCompteComptable(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ligneEcriture }) => {
      this.ligneEcriture = ligneEcriture;
      if (ligneEcriture) {
        this.updateForm(ligneEcriture);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ligneEcriture = this.ligneEcritureFormService.getLigneEcriture(this.editForm);
    if (ligneEcriture.id !== null) {
      this.subscribeToSaveResponse(this.ligneEcritureService.update(ligneEcriture));
    } else {
      this.subscribeToSaveResponse(this.ligneEcritureService.create(ligneEcriture));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILigneEcriture>>): void {
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

  protected updateForm(ligneEcriture: ILigneEcriture): void {
    this.ligneEcriture = ligneEcriture;
    this.ligneEcritureFormService.resetForm(this.editForm, ligneEcriture);

    this.ecritureComptablesSharedCollection = this.ecritureComptableService.addEcritureComptableToCollectionIfMissing<IEcritureComptable>(
      this.ecritureComptablesSharedCollection,
      ligneEcriture.ecriture,
    );
    this.compteComptablesSharedCollection = this.compteComptableService.addCompteComptableToCollectionIfMissing<ICompteComptable>(
      this.compteComptablesSharedCollection,
      ligneEcriture.compte,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.ecritureComptableService
      .query()
      .pipe(map((res: HttpResponse<IEcritureComptable[]>) => res.body ?? []))
      .pipe(
        map((ecritureComptables: IEcritureComptable[]) =>
          this.ecritureComptableService.addEcritureComptableToCollectionIfMissing<IEcritureComptable>(
            ecritureComptables,
            this.ligneEcriture?.ecriture,
          ),
        ),
      )
      .subscribe((ecritureComptables: IEcritureComptable[]) => (this.ecritureComptablesSharedCollection = ecritureComptables));

    this.compteComptableService
      .query()
      .pipe(map((res: HttpResponse<ICompteComptable[]>) => res.body ?? []))
      .pipe(
        map((compteComptables: ICompteComptable[]) =>
          this.compteComptableService.addCompteComptableToCollectionIfMissing<ICompteComptable>(
            compteComptables,
            this.ligneEcriture?.compte,
          ),
        ),
      )
      .subscribe((compteComptables: ICompteComptable[]) => (this.compteComptablesSharedCollection = compteComptables));
  }
}
