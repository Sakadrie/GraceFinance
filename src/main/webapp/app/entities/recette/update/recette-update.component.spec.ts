import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IEntiteFinanciere } from 'app/entities/entite-financiere/entite-financiere.model';
import { EntiteFinanciereService } from 'app/entities/entite-financiere/service/entite-financiere.service';
import { ICaisse } from 'app/entities/caisse/caisse.model';
import { CaisseService } from 'app/entities/caisse/service/caisse.service';
import { ICategorie } from 'app/entities/categorie/categorie.model';
import { CategorieService } from 'app/entities/categorie/service/categorie.service';
import { IRecette } from '../recette.model';
import { RecetteService } from '../service/recette.service';
import { RecetteFormService } from './recette-form.service';

import { RecetteUpdateComponent } from './recette-update.component';

describe('Recette Management Update Component', () => {
  let comp: RecetteUpdateComponent;
  let fixture: ComponentFixture<RecetteUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let recetteFormService: RecetteFormService;
  let recetteService: RecetteService;
  let entiteFinanciereService: EntiteFinanciereService;
  let caisseService: CaisseService;
  let categorieService: CategorieService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RecetteUpdateComponent],
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
      .overrideTemplate(RecetteUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RecetteUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    recetteFormService = TestBed.inject(RecetteFormService);
    recetteService = TestBed.inject(RecetteService);
    entiteFinanciereService = TestBed.inject(EntiteFinanciereService);
    caisseService = TestBed.inject(CaisseService);
    categorieService = TestBed.inject(CategorieService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call EntiteFinanciere query and add missing value', () => {
      const recette: IRecette = { id: 7863 };
      const entiteFinanciere: IEntiteFinanciere = { id: 8941 };
      recette.entiteFinanciere = entiteFinanciere;

      const entiteFinanciereCollection: IEntiteFinanciere[] = [{ id: 8941 }];
      jest.spyOn(entiteFinanciereService, 'query').mockReturnValue(of(new HttpResponse({ body: entiteFinanciereCollection })));
      const additionalEntiteFinancieres = [entiteFinanciere];
      const expectedCollection: IEntiteFinanciere[] = [...additionalEntiteFinancieres, ...entiteFinanciereCollection];
      jest.spyOn(entiteFinanciereService, 'addEntiteFinanciereToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ recette });
      comp.ngOnInit();

      expect(entiteFinanciereService.query).toHaveBeenCalled();
      expect(entiteFinanciereService.addEntiteFinanciereToCollectionIfMissing).toHaveBeenCalledWith(
        entiteFinanciereCollection,
        ...additionalEntiteFinancieres.map(expect.objectContaining),
      );
      expect(comp.entiteFinancieresSharedCollection).toEqual(expectedCollection);
    });

    it('should call Caisse query and add missing value', () => {
      const recette: IRecette = { id: 7863 };
      const caisse: ICaisse = { id: 3599 };
      recette.caisse = caisse;

      const caisseCollection: ICaisse[] = [{ id: 3599 }];
      jest.spyOn(caisseService, 'query').mockReturnValue(of(new HttpResponse({ body: caisseCollection })));
      const additionalCaisses = [caisse];
      const expectedCollection: ICaisse[] = [...additionalCaisses, ...caisseCollection];
      jest.spyOn(caisseService, 'addCaisseToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ recette });
      comp.ngOnInit();

      expect(caisseService.query).toHaveBeenCalled();
      expect(caisseService.addCaisseToCollectionIfMissing).toHaveBeenCalledWith(
        caisseCollection,
        ...additionalCaisses.map(expect.objectContaining),
      );
      expect(comp.caissesSharedCollection).toEqual(expectedCollection);
    });

    it('should call Categorie query and add missing value', () => {
      const recette: IRecette = { id: 7863 };
      const categorie: ICategorie = { id: 7213 };
      recette.categorie = categorie;

      const categorieCollection: ICategorie[] = [{ id: 7213 }];
      jest.spyOn(categorieService, 'query').mockReturnValue(of(new HttpResponse({ body: categorieCollection })));
      const additionalCategories = [categorie];
      const expectedCollection: ICategorie[] = [...additionalCategories, ...categorieCollection];
      jest.spyOn(categorieService, 'addCategorieToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ recette });
      comp.ngOnInit();

      expect(categorieService.query).toHaveBeenCalled();
      expect(categorieService.addCategorieToCollectionIfMissing).toHaveBeenCalledWith(
        categorieCollection,
        ...additionalCategories.map(expect.objectContaining),
      );
      expect(comp.categoriesSharedCollection).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const recette: IRecette = { id: 7863 };
      const entiteFinanciere: IEntiteFinanciere = { id: 8941 };
      recette.entiteFinanciere = entiteFinanciere;
      const caisse: ICaisse = { id: 3599 };
      recette.caisse = caisse;
      const categorie: ICategorie = { id: 7213 };
      recette.categorie = categorie;

      activatedRoute.data = of({ recette });
      comp.ngOnInit();

      expect(comp.entiteFinancieresSharedCollection).toContainEqual(entiteFinanciere);
      expect(comp.caissesSharedCollection).toContainEqual(caisse);
      expect(comp.categoriesSharedCollection).toContainEqual(categorie);
      expect(comp.recette).toEqual(recette);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRecette>>();
      const recette = { id: 25305 };
      jest.spyOn(recetteFormService, 'getRecette').mockReturnValue(recette);
      jest.spyOn(recetteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ recette });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: recette }));
      saveSubject.complete();

      // THEN
      expect(recetteFormService.getRecette).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(recetteService.update).toHaveBeenCalledWith(expect.objectContaining(recette));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRecette>>();
      const recette = { id: 25305 };
      jest.spyOn(recetteFormService, 'getRecette').mockReturnValue({ id: null });
      jest.spyOn(recetteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ recette: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: recette }));
      saveSubject.complete();

      // THEN
      expect(recetteFormService.getRecette).toHaveBeenCalled();
      expect(recetteService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRecette>>();
      const recette = { id: 25305 };
      jest.spyOn(recetteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ recette });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(recetteService.update).toHaveBeenCalled();
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
