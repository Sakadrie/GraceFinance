import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IDroit } from 'app/entities/droit/droit.model';
import { DroitService } from 'app/entities/droit/service/droit.service';
import { IAffectationUtilisateur } from 'app/entities/affectation-utilisateur/affectation-utilisateur.model';
import { AffectationUtilisateurService } from 'app/entities/affectation-utilisateur/service/affectation-utilisateur.service';
import { ProfilService } from '../service/profil.service';
import { IProfil } from '../profil.model';
import { ProfilFormGroup, ProfilFormService } from './profil-form.service';

@Component({
  selector: 'jhi-profil-update',
  templateUrl: './profil-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ProfilUpdateComponent implements OnInit {
  isSaving = false;
  profil: IProfil | null = null;

  droitsSharedCollection: IDroit[] = [];
  affectationUtilisateursSharedCollection: IAffectationUtilisateur[] = [];

  protected profilService = inject(ProfilService);
  protected profilFormService = inject(ProfilFormService);
  protected droitService = inject(DroitService);
  protected affectationUtilisateurService = inject(AffectationUtilisateurService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ProfilFormGroup = this.profilFormService.createProfilFormGroup();

  compareDroit = (o1: IDroit | null, o2: IDroit | null): boolean => this.droitService.compareDroit(o1, o2);

  compareAffectationUtilisateur = (o1: IAffectationUtilisateur | null, o2: IAffectationUtilisateur | null): boolean =>
    this.affectationUtilisateurService.compareAffectationUtilisateur(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ profil }) => {
      this.profil = profil;
      if (profil) {
        this.updateForm(profil);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const profil = this.profilFormService.getProfil(this.editForm);
    if (profil.id !== null) {
      this.subscribeToSaveResponse(this.profilService.update(profil));
    } else {
      this.subscribeToSaveResponse(this.profilService.create(profil));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProfil>>): void {
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

  protected updateForm(profil: IProfil): void {
    this.profil = profil;
    this.profilFormService.resetForm(this.editForm, profil);

    this.droitsSharedCollection = this.droitService.addDroitToCollectionIfMissing<IDroit>(
      this.droitsSharedCollection,
      ...(profil.droits ?? []),
    );
    this.affectationUtilisateursSharedCollection =
      this.affectationUtilisateurService.addAffectationUtilisateurToCollectionIfMissing<IAffectationUtilisateur>(
        this.affectationUtilisateursSharedCollection,
        ...(profil.affectations ?? []),
      );
  }

  protected loadRelationshipsOptions(): void {
    this.droitService
      .query()
      .pipe(map((res: HttpResponse<IDroit[]>) => res.body ?? []))
      .pipe(map((droits: IDroit[]) => this.droitService.addDroitToCollectionIfMissing<IDroit>(droits, ...(this.profil?.droits ?? []))))
      .subscribe((droits: IDroit[]) => (this.droitsSharedCollection = droits));

    this.affectationUtilisateurService
      .query()
      .pipe(map((res: HttpResponse<IAffectationUtilisateur[]>) => res.body ?? []))
      .pipe(
        map((affectationUtilisateurs: IAffectationUtilisateur[]) =>
          this.affectationUtilisateurService.addAffectationUtilisateurToCollectionIfMissing<IAffectationUtilisateur>(
            affectationUtilisateurs,
            ...(this.profil?.affectations ?? []),
          ),
        ),
      )
      .subscribe(
        (affectationUtilisateurs: IAffectationUtilisateur[]) => (this.affectationUtilisateursSharedCollection = affectationUtilisateurs),
      );
  }
}
