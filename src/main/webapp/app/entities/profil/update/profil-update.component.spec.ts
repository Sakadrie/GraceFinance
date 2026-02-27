import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IDroit } from 'app/entities/droit/droit.model';
import { DroitService } from 'app/entities/droit/service/droit.service';
import { IAffectationUtilisateur } from 'app/entities/affectation-utilisateur/affectation-utilisateur.model';
import { AffectationUtilisateurService } from 'app/entities/affectation-utilisateur/service/affectation-utilisateur.service';
import { IProfil } from '../profil.model';
import { ProfilService } from '../service/profil.service';
import { ProfilFormService } from './profil-form.service';

import { ProfilUpdateComponent } from './profil-update.component';

describe('Profil Management Update Component', () => {
  let comp: ProfilUpdateComponent;
  let fixture: ComponentFixture<ProfilUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let profilFormService: ProfilFormService;
  let profilService: ProfilService;
  let droitService: DroitService;
  let affectationUtilisateurService: AffectationUtilisateurService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ProfilUpdateComponent],
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
      .overrideTemplate(ProfilUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProfilUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    profilFormService = TestBed.inject(ProfilFormService);
    profilService = TestBed.inject(ProfilService);
    droitService = TestBed.inject(DroitService);
    affectationUtilisateurService = TestBed.inject(AffectationUtilisateurService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call Droit query and add missing value', () => {
      const profil: IProfil = { id: 13621 };
      const droits: IDroit[] = [{ id: 23804 }];
      profil.droits = droits;

      const droitCollection: IDroit[] = [{ id: 23804 }];
      jest.spyOn(droitService, 'query').mockReturnValue(of(new HttpResponse({ body: droitCollection })));
      const additionalDroits = [...droits];
      const expectedCollection: IDroit[] = [...additionalDroits, ...droitCollection];
      jest.spyOn(droitService, 'addDroitToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ profil });
      comp.ngOnInit();

      expect(droitService.query).toHaveBeenCalled();
      expect(droitService.addDroitToCollectionIfMissing).toHaveBeenCalledWith(
        droitCollection,
        ...additionalDroits.map(expect.objectContaining),
      );
      expect(comp.droitsSharedCollection).toEqual(expectedCollection);
    });

    it('should call AffectationUtilisateur query and add missing value', () => {
      const profil: IProfil = { id: 13621 };
      const affectations: IAffectationUtilisateur[] = [{ id: 4001 }];
      profil.affectations = affectations;

      const affectationUtilisateurCollection: IAffectationUtilisateur[] = [{ id: 4001 }];
      jest.spyOn(affectationUtilisateurService, 'query').mockReturnValue(of(new HttpResponse({ body: affectationUtilisateurCollection })));
      const additionalAffectationUtilisateurs = [...affectations];
      const expectedCollection: IAffectationUtilisateur[] = [...additionalAffectationUtilisateurs, ...affectationUtilisateurCollection];
      jest.spyOn(affectationUtilisateurService, 'addAffectationUtilisateurToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ profil });
      comp.ngOnInit();

      expect(affectationUtilisateurService.query).toHaveBeenCalled();
      expect(affectationUtilisateurService.addAffectationUtilisateurToCollectionIfMissing).toHaveBeenCalledWith(
        affectationUtilisateurCollection,
        ...additionalAffectationUtilisateurs.map(expect.objectContaining),
      );
      expect(comp.affectationUtilisateursSharedCollection).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const profil: IProfil = { id: 13621 };
      const droit: IDroit = { id: 23804 };
      profil.droits = [droit];
      const affectation: IAffectationUtilisateur = { id: 4001 };
      profil.affectations = [affectation];

      activatedRoute.data = of({ profil });
      comp.ngOnInit();

      expect(comp.droitsSharedCollection).toContainEqual(droit);
      expect(comp.affectationUtilisateursSharedCollection).toContainEqual(affectation);
      expect(comp.profil).toEqual(profil);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProfil>>();
      const profil = { id: 12279 };
      jest.spyOn(profilFormService, 'getProfil').mockReturnValue(profil);
      jest.spyOn(profilService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ profil });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: profil }));
      saveSubject.complete();

      // THEN
      expect(profilFormService.getProfil).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(profilService.update).toHaveBeenCalledWith(expect.objectContaining(profil));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProfil>>();
      const profil = { id: 12279 };
      jest.spyOn(profilFormService, 'getProfil').mockReturnValue({ id: null });
      jest.spyOn(profilService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ profil: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: profil }));
      saveSubject.complete();

      // THEN
      expect(profilFormService.getProfil).toHaveBeenCalled();
      expect(profilService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProfil>>();
      const profil = { id: 12279 };
      jest.spyOn(profilService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ profil });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(profilService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareDroit', () => {
      it('should forward to droitService', () => {
        const entity = { id: 23804 };
        const entity2 = { id: 30538 };
        jest.spyOn(droitService, 'compareDroit');
        comp.compareDroit(entity, entity2);
        expect(droitService.compareDroit).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareAffectationUtilisateur', () => {
      it('should forward to affectationUtilisateurService', () => {
        const entity = { id: 4001 };
        const entity2 = { id: 21243 };
        jest.spyOn(affectationUtilisateurService, 'compareAffectationUtilisateur');
        comp.compareAffectationUtilisateur(entity, entity2);
        expect(affectationUtilisateurService.compareAffectationUtilisateur).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
