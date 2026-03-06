import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IProfil } from 'app/shared/model/security/profil.model';
import { ProfilService } from 'app/entities/profil/service/profil.service';
import { DroitService } from '../service/droit.service';
import { IDroit } from '../../../shared/model/security/droit.model';
import { DroitFormService } from './droit-form.service';

import { DroitUpdateComponent } from './droit-update.component';

describe('Droit Management Update Component', () => {
  let comp: DroitUpdateComponent;
  let fixture: ComponentFixture<DroitUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let droitFormService: DroitFormService;
  let droitService: DroitService;
  let profilService: ProfilService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [DroitUpdateComponent],
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
      .overrideTemplate(DroitUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DroitUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    droitFormService = TestBed.inject(DroitFormService);
    droitService = TestBed.inject(DroitService);
    profilService = TestBed.inject(ProfilService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call Profil query and add missing value', () => {
      const droit: IDroit = { id: 30538 };
      const profils: IProfil[] = [{ id: 12279 }];
      droit.profils = profils;

      const profilCollection: IProfil[] = [{ id: 12279 }];
      jest.spyOn(profilService, 'query').mockReturnValue(of(new HttpResponse({ body: profilCollection })));
      const additionalProfils = [...profils];
      const expectedCollection: IProfil[] = [...additionalProfils, ...profilCollection];
      jest.spyOn(profilService, 'addProfilToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ droit });
      comp.ngOnInit();

      expect(profilService.query).toHaveBeenCalled();
      expect(profilService.addProfilToCollectionIfMissing).toHaveBeenCalledWith(
        profilCollection,
        ...additionalProfils.map(expect.objectContaining),
      );
      expect(comp.profilsSharedCollection).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const droit: IDroit = { id: 30538 };
      const profil: IProfil = { id: 12279 };
      droit.profils = [profil];

      activatedRoute.data = of({ droit });
      comp.ngOnInit();

      expect(comp.profilsSharedCollection).toContainEqual(profil);
      expect(comp.droit).toEqual(droit);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDroit>>();
      const droit = { id: 23804 };
      jest.spyOn(droitFormService, 'getDroit').mockReturnValue(droit);
      jest.spyOn(droitService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ droit });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: droit }));
      saveSubject.complete();

      // THEN
      expect(droitFormService.getDroit).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(droitService.update).toHaveBeenCalledWith(expect.objectContaining(droit));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDroit>>();
      const droit = { id: 23804 };
      jest.spyOn(droitFormService, 'getDroit').mockReturnValue({ id: null });
      jest.spyOn(droitService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ droit: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: droit }));
      saveSubject.complete();

      // THEN
      expect(droitFormService.getDroit).toHaveBeenCalled();
      expect(droitService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDroit>>();
      const droit = { id: 23804 };
      jest.spyOn(droitService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ droit });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(droitService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
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
