import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IEntiteFinanciere } from 'app/entities/entite-financiere/entite-financiere.model';
import { EntiteFinanciereService } from 'app/entities/entite-financiere/service/entite-financiere.service';
import { CaisseService } from '../service/caisse.service';
import { ICaisse } from '../caisse.model';
import { CaisseFormService } from './caisse-form.service';

import { CaisseUpdateComponent } from './caisse-update.component';

describe('Caisse Management Update Component', () => {
  let comp: CaisseUpdateComponent;
  let fixture: ComponentFixture<CaisseUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let caisseFormService: CaisseFormService;
  let caisseService: CaisseService;
  let entiteFinanciereService: EntiteFinanciereService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [CaisseUpdateComponent],
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
      .overrideTemplate(CaisseUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CaisseUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    caisseFormService = TestBed.inject(CaisseFormService);
    caisseService = TestBed.inject(CaisseService);
    entiteFinanciereService = TestBed.inject(EntiteFinanciereService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call EntiteFinanciere query and add missing value', () => {
      const caisse: ICaisse = { id: 13258 };
      const entiteFinanciere: IEntiteFinanciere = { id: 8941 };
      caisse.entiteFinanciere = entiteFinanciere;

      const entiteFinanciereCollection: IEntiteFinanciere[] = [{ id: 8941 }];
      jest.spyOn(entiteFinanciereService, 'query').mockReturnValue(of(new HttpResponse({ body: entiteFinanciereCollection })));
      const additionalEntiteFinancieres = [entiteFinanciere];
      const expectedCollection: IEntiteFinanciere[] = [...additionalEntiteFinancieres, ...entiteFinanciereCollection];
      jest.spyOn(entiteFinanciereService, 'addEntiteFinanciereToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ caisse });
      comp.ngOnInit();

      expect(entiteFinanciereService.query).toHaveBeenCalled();
      expect(entiteFinanciereService.addEntiteFinanciereToCollectionIfMissing).toHaveBeenCalledWith(
        entiteFinanciereCollection,
        ...additionalEntiteFinancieres.map(expect.objectContaining),
      );
      expect(comp.entiteFinancieresSharedCollection).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const caisse: ICaisse = { id: 13258 };
      const entiteFinanciere: IEntiteFinanciere = { id: 8941 };
      caisse.entiteFinanciere = entiteFinanciere;

      activatedRoute.data = of({ caisse });
      comp.ngOnInit();

      expect(comp.entiteFinancieresSharedCollection).toContainEqual(entiteFinanciere);
      expect(comp.caisse).toEqual(caisse);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICaisse>>();
      const caisse = { id: 3599 };
      jest.spyOn(caisseFormService, 'getCaisse').mockReturnValue(caisse);
      jest.spyOn(caisseService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ caisse });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: caisse }));
      saveSubject.complete();

      // THEN
      expect(caisseFormService.getCaisse).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(caisseService.update).toHaveBeenCalledWith(expect.objectContaining(caisse));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICaisse>>();
      const caisse = { id: 3599 };
      jest.spyOn(caisseFormService, 'getCaisse').mockReturnValue({ id: null });
      jest.spyOn(caisseService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ caisse: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: caisse }));
      saveSubject.complete();

      // THEN
      expect(caisseFormService.getCaisse).toHaveBeenCalled();
      expect(caisseService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICaisse>>();
      const caisse = { id: 3599 };
      jest.spyOn(caisseService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ caisse });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(caisseService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEntiteFinanciere', () => {
      it('should forward to entiteFinanciereService', () => {
        const entity = { id: 8941 };
        const entity2 = { id: 4924 };
        jest.spyOn(entiteFinanciereService, 'compareEntiteFinanciere');
        comp.compareEntiteFinanciere(entity, entity2);
        expect(entiteFinanciereService.compareEntiteFinanciere).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
