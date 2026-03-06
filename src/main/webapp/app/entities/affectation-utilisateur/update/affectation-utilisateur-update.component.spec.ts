import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/service/user.service';
import { IEntiteFinanciere } from 'app/shared/model/principal/entite-financiere.model';
import { EntiteFinanciereService } from 'app/entities/entite-financiere/service/entite-financiere.service';
import { IProfil } from 'app/shared/model/security/profil.model';
import { ProfilService } from 'app/entities/profil/service/profil.service';
import { IAffectationUtilisateur } from '../../../shared/model/security/affectation-utilisateur.model';
import { AffectationUtilisateurService } from '../service/affectation-utilisateur.service';
import { AffectationUtilisateurFormService } from './affectation-utilisateur-form.service';

import { AffectationUtilisateurUpdateComponent } from './affectation-utilisateur-update.component';

describe('AffectationUtilisateur Management Update Component', () => {
  let comp: AffectationUtilisateurUpdateComponent;
  let fixture: ComponentFixture<AffectationUtilisateurUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let affectationUtilisateurFormService: AffectationUtilisateurFormService;
  let affectationUtilisateurService: AffectationUtilisateurService;
  let userService: UserService;
  let entiteFinanciereService: EntiteFinanciereService;
  let profilService: ProfilService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AffectationUtilisateurUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(AffectationUtilisateurUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AffectationUtilisateurUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    affectationUtilisateurFormService = TestBed.inject(AffectationUtilisateurFormService);
    affectationUtilisateurService = TestBed.inject(AffectationUtilisateurService);
    userService = TestBed.inject(UserService);
    entiteFinanciereService = TestBed.inject(EntiteFinanciereService);
    profilService = TestBed.inject(ProfilService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call User query and add missing value', () => {
      const affectationUtilisateur: IAffectationUtilisateur = { id: 21243 };
      const user: IUser = { id: 3944 };
      affectationUtilisateur.user = user;

      const userCollection: IUser[] = [{ id: 3944 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [user];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ affectationUtilisateur });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining),
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('should call EntiteFinanciere query and add missing value', () => {
      const affectationUtilisateur: IAffectationUtilisateur = { id: 21243 };
      const entiteFinanciere: IEntiteFinanciere = { id: 8941 };
      affectationUtilisateur.entiteFinanciere = entiteFinanciere;

      const entiteFinanciereCollection: IEntiteFinanciere[] = [{ id: 8941 }];
      jest.spyOn(entiteFinanciereService, 'query').mockReturnValue(of(new HttpResponse({ body: entiteFinanciereCollection })));
      const additionalEntiteFinancieres = [entiteFinanciere];
      const expectedCollection: IEntiteFinanciere[] = [...additionalEntiteFinancieres, ...entiteFinanciereCollection];
      jest.spyOn(entiteFinanciereService, 'addEntiteFinanciereToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ affectationUtilisateur });
      comp.ngOnInit();

      expect(entiteFinanciereService.query).toHaveBeenCalled();
      expect(entiteFinanciereService.addEntiteFinanciereToCollectionIfMissing).toHaveBeenCalledWith(
        entiteFinanciereCollection,
        ...additionalEntiteFinancieres.map(expect.objectContaining),
      );
      expect(comp.entiteFinancieresSharedCollection).toEqual(expectedCollection);
    });

    it('should call Profil query and add missing value', () => {
      const affectationUtilisateur: IAffectationUtilisateur = { id: 21243 };
      const profils: IProfil[] = [{ id: 12279 }];
      affectationUtilisateur.profils = profils;

      const profilCollection: IProfil[] = [{ id: 12279 }];
      jest.spyOn(profilService, 'query').mockReturnValue(of(new HttpResponse({ body: profilCollection })));
      const additionalProfils = [...profils];
      const expectedCollection: IProfil[] = [...additionalProfils, ...profilCollection];
      jest.spyOn(profilService, 'addProfilToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ affectationUtilisateur });
      comp.ngOnInit();

      expect(profilService.query).toHaveBeenCalled();
      expect(profilService.addProfilToCollectionIfMissing).toHaveBeenCalledWith(
        profilCollection,
        ...additionalProfils.map(expect.objectContaining),
      );
      expect(comp.profilsSharedCollection).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const affectationUtilisateur: IAffectationUtilisateur = { id: 21243 };
      const user: IUser = { id: 3944 };
      affectationUtilisateur.user = user;
      const entiteFinanciere: IEntiteFinanciere = { id: 8941 };
      affectationUtilisateur.entiteFinanciere = entiteFinanciere;
      const profil: IProfil = { id: 12279 };
      affectationUtilisateur.profils = [profil];

      activatedRoute.data = of({ affectationUtilisateur });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContainEqual(user);
      expect(comp.entiteFinancieresSharedCollection).toContainEqual(entiteFinanciere);
      expect(comp.profilsSharedCollection).toContainEqual(profil);
      expect(comp.affectationUtilisateur).toEqual(affectationUtilisateur);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAffectationUtilisateur>>();
      const affectationUtilisateur = { id: 4001 };
      jest.spyOn(affectationUtilisateurFormService, 'getAffectationUtilisateur').mockReturnValue(affectationUtilisateur);
      jest.spyOn(affectationUtilisateurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ affectationUtilisateur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: affectationUtilisateur }));
      saveSubject.complete();

      // THEN
      expect(affectationUtilisateurFormService.getAffectationUtilisateur).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(affectationUtilisateurService.update).toHaveBeenCalledWith(expect.objectContaining(affectationUtilisateur));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAffectationUtilisateur>>();
      const affectationUtilisateur = { id: 4001 };
      jest.spyOn(affectationUtilisateurFormService, 'getAffectationUtilisateur').mockReturnValue({ id: null });
      jest.spyOn(affectationUtilisateurService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ affectationUtilisateur: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: affectationUtilisateur }));
      saveSubject.complete();

      // THEN
      expect(affectationUtilisateurFormService.getAffectationUtilisateur).toHaveBeenCalled();
      expect(affectationUtilisateurService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAffectationUtilisateur>>();
      const affectationUtilisateur = { id: 4001 };
      jest.spyOn(affectationUtilisateurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ affectationUtilisateur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(affectationUtilisateurService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareUser', () => {
      it('should forward to userService', () => {
        const entity = { id: 3944 };
        const entity2 = { id: 6275 };
        jest.spyOn(userService, 'compareUser');
        comp.compareUser(entity, entity2);
        expect(userService.compareUser).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareEntiteFinanciere', () => {
      it('should forward to entiteFinanciereService', () => {
        const entity = { id: 8941 };
        const entity2 = { id: 4924 };
        jest.spyOn(entiteFinanciereService, 'compareEntiteFinanciere');
        comp.compareEntiteFinanciere(entity, entity2);
        expect(entiteFinanciereService.compareEntiteFinanciere).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareProfil', () => {
      it('should forward to profilService', () => {
        const entity = { id: 12279 };
        const entity2 = { id: 13621 };
        jest.spyOn(profilService, 'compareProfil');
        comp.compareProfil(entity, entity2);
        expect(profilService.compareProfil).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
