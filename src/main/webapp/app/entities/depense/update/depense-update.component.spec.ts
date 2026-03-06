import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IEntiteFinanciere } from 'app/shared/model/principal/entite-financiere.model';
import { EntiteFinanciereService } from 'app/entities/entite-financiere/service/entite-financiere.service';
import { ICaisse } from 'app/shared/model/principal/caisse.model';
import { CaisseService } from 'app/entities/caisse/service/caisse.service';
import { ICategorie } from 'app/shared/model/referentiel/categorie.model';
import { CategorieService } from 'app/entities/categorie/service/categorie.service';
import { IDepense } from '../../../shared/model/principal/depense.model';
import { DepenseService } from '../service/depense.service';
import { DepenseFormService } from './depense-form.service';

import { DepenseUpdateComponent } from './depense-update.component';

describe('Depense Management Update Component', () => {
  let comp: DepenseUpdateComponent;
  let fixture: ComponentFixture<DepenseUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let depenseFormService: DepenseFormService;
  let depenseService: DepenseService;
  let entiteFinanciereService: EntiteFinanciereService;
  let caisseService: CaisseService;
  let categorieService: CategorieService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [DepenseUpdateComponent],
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
      .overrideTemplate(DepenseUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DepenseUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    depenseFormService = TestBed.inject(DepenseFormService);
    depenseService = TestBed.inject(DepenseService);
    entiteFinanciereService = TestBed.inject(EntiteFinanciereService);
    caisseService = TestBed.inject(CaisseService);
    categorieService = TestBed.inject(CategorieService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call EntiteFinanciere query and add missing value', () => {
      const depense: IDepense = { id: 17191 };
      const entiteFinanciere: IEntiteFinanciere = { id: 8941 };
      depense.entiteFinanciere = entiteFinanciere;

      const entiteFinanciereCollection: IEntiteFinanciere[] = [{ id: 8941 }];
      jest.spyOn(entiteFinanciereService, 'query').mockReturnValue(of(new HttpResponse({ body: entiteFinanciereCollection })));
      const additionalEntiteFinancieres = [entiteFinanciere];
      const expectedCollection: IEntiteFinanciere[] = [...additionalEntiteFinancieres, ...entiteFinanciereCollection];
      jest.spyOn(entiteFinanciereService, 'addEntiteFinanciereToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ depense });
      comp.ngOnInit();

      expect(entiteFinanciereService.query).toHaveBeenCalled();
      expect(entiteFinanciereService.addEntiteFinanciereToCollectionIfMissing).toHaveBeenCalledWith(
        entiteFinanciereCollection,
        ...additionalEntiteFinancieres.map(expect.objectContaining),
      );
      expect(comp.entiteFinancieresSharedCollection).toEqual(expectedCollection);
    });

    it('should call Caisse query and add missing value', () => {
      const depense: IDepense = { id: 17191 };
      const caisse: ICaisse = { id: 3599 };
      depense.caisse = caisse;

      const caisseCollection: ICaisse[] = [{ id: 3599 }];
      jest.spyOn(caisseService, 'query').mockReturnValue(of(new HttpResponse({ body: caisseCollection })));
      const additionalCaisses = [caisse];
      const expectedCollection: ICaisse[] = [...additionalCaisses, ...caisseCollection];
      jest.spyOn(caisseService, 'addCaisseToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ depense });
      comp.ngOnInit();

      expect(caisseService.query).toHaveBeenCalled();
      expect(caisseService.addCaisseToCollectionIfMissing).toHaveBeenCalledWith(
        caisseCollection,
        ...additionalCaisses.map(expect.objectContaining),
      );
      expect(comp.caissesSharedCollection).toEqual(expectedCollection);
    });

    it('should call Categorie query and add missing value', () => {
      const depense: IDepense = { id: 17191 };
      const categorie: ICategorie = { id: 7213 };
      depense.categorie = categorie;

      const categorieCollection: ICategorie[] = [{ id: 7213 }];
      jest.spyOn(categorieService, 'query').mockReturnValue(of(new HttpResponse({ body: categorieCollection })));
      const additionalCategories = [categorie];
      const expectedCollection: ICategorie[] = [...additionalCategories, ...categorieCollection];
      jest.spyOn(categorieService, 'addCategorieToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ depense });
      comp.ngOnInit();

      expect(categorieService.query).toHaveBeenCalled();
      expect(categorieService.addCategorieToCollectionIfMissing).toHaveBeenCalledWith(
        categorieCollection,
        ...additionalCategories.map(expect.objectContaining),
      );
      expect(comp.categoriesSharedCollection).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const depense: IDepense = { id: 17191 };
      const entiteFinanciere: IEntiteFinanciere = { id: 8941 };
      depense.entiteFinanciere = entiteFinanciere;
      const caisse: ICaisse = { id: 3599 };
      depense.caisse = caisse;
      const categorie: ICategorie = { id: 7213 };
      depense.categorie = categorie;

      activatedRoute.data = of({ depense });
      comp.ngOnInit();

      expect(comp.entiteFinancieresSharedCollection).toContainEqual(entiteFinanciere);
      expect(comp.caissesSharedCollection).toContainEqual(caisse);
      expect(comp.categoriesSharedCollection).toContainEqual(categorie);
      expect(comp.depense).toEqual(depense);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDepense>>();
      const depense = { id: 1915 };
      jest.spyOn(depenseFormService, 'getDepense').mockReturnValue(depense);
      jest.spyOn(depenseService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ depense });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: depense }));
      saveSubject.complete();

      // THEN
      expect(depenseFormService.getDepense).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(depenseService.update).toHaveBeenCalledWith(expect.objectContaining(depense));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDepense>>();
      const depense = { id: 1915 };
      jest.spyOn(depenseFormService, 'getDepense').mockReturnValue({ id: null });
      jest.spyOn(depenseService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ depense: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: depense }));
      saveSubject.complete();

      // THEN
      expect(depenseFormService.getDepense).toHaveBeenCalled();
      expect(depenseService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDepense>>();
      const depense = { id: 1915 };
      jest.spyOn(depenseService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ depense });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(depenseService.update).toHaveBeenCalled();
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

    describe('compareCaisse', () => {
      it('should forward to caisseService', () => {
        const entity = { id: 3599 };
        const entity2 = { id: 13258 };
        jest.spyOn(caisseService, 'compareCaisse');
        comp.compareCaisse(entity, entity2);
        expect(caisseService.compareCaisse).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCategorie', () => {
      it('should forward to categorieService', () => {
        const entity = { id: 7213 };
        const entity2 = { id: 18684 };
        jest.spyOn(categorieService, 'compareCategorie');
        comp.compareCategorie(entity, entity2);
        expect(categorieService.compareCategorie).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
