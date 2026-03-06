import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/service/user.service';
import { IEntiteFinanciere } from 'app/shared/model/principal/entite-financiere.model';
import { EntiteFinanciereService } from 'app/entities/entite-financiere/service/entite-financiere.service';
import { IProfil } from 'app/shared/model/security/profil.model';
import { ProfilService } from 'app/entities/profil/service/profil.service';
import { AffectationUtilisateurService } from '../service/affectation-utilisateur.service';
import { IAffectationUtilisateur } from '../../../shared/model/security/affectation-utilisateur.model';
import { AffectationUtilisateurFormGroup, AffectationUtilisateurFormService } from './affectation-utilisateur-form.service';

@Component({
  selector: 'jhi-affectation-utilisateur-update',
  templateUrl: './affectation-utilisateur-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AffectationUtilisateurUpdateComponent implements OnInit {
  isSaving = false;
  affectationUtilisateur: IAffectationUtilisateur | null = null;

  usersSharedCollection: IUser[] = [];
  entiteFinancieresSharedCollection: IEntiteFinanciere[] = [];
  profilsSharedCollection: IProfil[] = [];

  protected affectationUtilisateurService = inject(AffectationUtilisateurService);
  protected affectationUtilisateurFormService = inject(AffectationUtilisateurFormService);
  protected userService = inject(UserService);
  protected entiteFinanciereService = inject(EntiteFinanciereService);
  protected profilService = inject(ProfilService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AffectationUtilisateurFormGroup = this.affectationUtilisateurFormService.createAffectationUtilisateurFormGroup();

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  compareEntiteFinanciere = (o1: IEntiteFinanciere | null, o2: IEntiteFinanciere | null): boolean =>
    this.entiteFinanciereService.compareEntiteFinanciere(o1, o2);

  compareProfil = (o1: IProfil | null, o2: IProfil | null): boolean => this.profilService.compareProfil(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ affectationUtilisateur }) => {
      this.affectationUtilisateur = affectationUtilisateur;
      if (affectationUtilisateur) {
        this.updateForm(affectationUtilisateur);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const affectationUtilisateur = this.affectationUtilisateurFormService.getAffectationUtilisateur(this.editForm);
    if (affectationUtilisateur.id !== null) {
      this.subscribeToSaveResponse(this.affectationUtilisateurService.update(affectationUtilisateur));
    } else {
      this.subscribeToSaveResponse(this.affectationUtilisateurService.create(affectationUtilisateur));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAffectationUtilisateur>>): void {
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

  protected updateForm(affectationUtilisateur: IAffectationUtilisateur): void {
    this.affectationUtilisateur = affectationUtilisateur;
    this.affectationUtilisateurFormService.resetForm(this.editForm, affectationUtilisateur);

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(
      this.usersSharedCollection,
      affectationUtilisateur.user,
    );
    this.entiteFinancieresSharedCollection = this.entiteFinanciereService.addEntiteFinanciereToCollectionIfMissing<IEntiteFinanciere>(
      this.entiteFinancieresSharedCollection,
      affectationUtilisateur.entiteFinanciere,
    );
    this.profilsSharedCollection = this.profilService.addProfilToCollectionIfMissing<IProfil>(
      this.profilsSharedCollection,
      ...(affectationUtilisateur.profils ?? []),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.affectationUtilisateur?.user)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.entiteFinanciereService
      .query()
      .pipe(map((res: HttpResponse<IEntiteFinanciere[]>) => res.body ?? []))
      .pipe(
        map((entiteFinancieres: IEntiteFinanciere[]) =>
          this.entiteFinanciereService.addEntiteFinanciereToCollectionIfMissing<IEntiteFinanciere>(
            entiteFinancieres,
            this.affectationUtilisateur?.entiteFinanciere,
          ),
        ),
      )
      .subscribe((entiteFinancieres: IEntiteFinanciere[]) => (this.entiteFinancieresSharedCollection = entiteFinancieres));

    this.profilService
      .query()
      .pipe(map((res: HttpResponse<IProfil[]>) => res.body ?? []))
      .pipe(
        map((profils: IProfil[]) =>
          this.profilService.addProfilToCollectionIfMissing<IProfil>(profils, ...(this.affectationUtilisateur?.profils ?? [])),
        ),
      )
      .subscribe((profils: IProfil[]) => (this.profilsSharedCollection = profils));
  }
}
