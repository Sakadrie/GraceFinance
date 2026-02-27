import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IProfil } from 'app/entities/profil/profil.model';
import { ProfilService } from 'app/entities/profil/service/profil.service';
import { IDroit } from '../droit.model';
import { DroitService } from '../service/droit.service';
import { DroitFormGroup, DroitFormService } from './droit-form.service';

@Component({
  selector: 'jhi-droit-update',
  templateUrl: './droit-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DroitUpdateComponent implements OnInit {
  isSaving = false;
  droit: IDroit | null = null;

  profilsSharedCollection: IProfil[] = [];

  protected droitService = inject(DroitService);
  protected droitFormService = inject(DroitFormService);
  protected profilService = inject(ProfilService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: DroitFormGroup = this.droitFormService.createDroitFormGroup();

  compareProfil = (o1: IProfil | null, o2: IProfil | null): boolean => this.profilService.compareProfil(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ droit }) => {
      this.droit = droit;
      if (droit) {
        this.updateForm(droit);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const droit = this.droitFormService.getDroit(this.editForm);
    if (droit.id !== null) {
      this.subscribeToSaveResponse(this.droitService.update(droit));
    } else {
      this.subscribeToSaveResponse(this.droitService.create(droit));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDroit>>): void {
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

  protected updateForm(droit: IDroit): void {
    this.droit = droit;
    this.droitFormService.resetForm(this.editForm, droit);

    this.profilsSharedCollection = this.profilService.addProfilToCollectionIfMissing<IProfil>(
      this.profilsSharedCollection,
      ...(droit.profils ?? []),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.profilService
      .query()
      .pipe(map((res: HttpResponse<IProfil[]>) => res.body ?? []))
      .pipe(
        map((profils: IProfil[]) => this.profilService.addProfilToCollectionIfMissing<IProfil>(profils, ...(this.droit?.profils ?? []))),
      )
      .subscribe((profils: IProfil[]) => (this.profilsSharedCollection = profils));
  }
}
