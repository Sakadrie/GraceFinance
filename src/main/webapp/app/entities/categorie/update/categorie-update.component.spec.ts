import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IEntiteFinanciere } from 'app/entities/entite-financiere/entite-financiere.model';
import { EntiteFinanciereService } from 'app/entities/entite-financiere/service/entite-financiere.service';
import { CategorieService } from '../service/categorie.service';
import { ICategorie } from '../categorie.model';
import { CategorieFormService } from './categorie-form.service';

import { CategorieUpdateComponent } from './categorie-update.component';

describe('Categorie Management Update Component', () => {
  let comp: CategorieUpdateComponent;
  let fixture: ComponentFixture<CategorieUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let categorieFormService: CategorieFormService;
  let categorieService: CategorieService;
  let entiteFinanciereService: EntiteFinanciereService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [CategorieUpdateComponent],
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
      .overrideTemplate(CategorieUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CategorieUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    categorieFormService = TestBed.inject(CategorieFormService);
    categorieService = TestBed.inject(CategorieService);
    entiteFinanciereService = TestBed.inject(EntiteFinanciereService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call EntiteFinanciere query and add missing value', () => {
      const categorie: ICategorie = { id: 18684 };
      const entiteFinanciere: IEntiteFinanciere = { id: 8941 };
      categorie.entiteFinanciere = entiteFinanciere;

      const entiteFinanciereCollection: IEntiteFinanciere[] = [{ id: 8941 }];
      jest.spyOn(entiteFinanciereService, 'query').mockReturnValue(of(new HttpResponse({ body: entiteFinanciereCollection })));
      const additionalEntiteFinancieres = [entiteFinanciere];
      const expectedCollection: IEntiteFinanciere[] = [...additionalEntiteFinancieres, ...entiteFinanciereCollection];
      jest.spyOn(entiteFinanciereService, 'addEntiteFinanciereToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ categorie });
      comp.ngOnInit();

      expect(entiteFinanciereService.query).toHaveBeenCalled();
      expect(entiteFinanciereService.addEntiteFinanciereToCollectionIfMissing).toHaveBeenCalledWith(
        entiteFinanciereCollection,
        ...additionalEntiteFinancieres.map(expect.objectContaining),
      );
      expect(comp.entiteFinancieresSharedCollection).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const categorie: ICategorie = { id: 18684 };
      const entiteFinanciere: IEntiteFinanciere = { id: 8941 };
      categorie.entiteFinanciere = entiteFinanciere;

      activatedRoute.data = of({ categorie });
      comp.ngOnInit();

      expect(comp.entiteFinancieresSharedCollection).toContainEqual(entiteFinanciere);
      expect(comp.categorie).toEqual(categorie);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICategorie>>();
      const categorie = { id: 7213 };
      jest.spyOn(categorieFormService, 'getCategorie').mockReturnValue(categorie);
      jest.spyOn(categorieService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ categorie });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: categorie }));
      saveSubject.complete();

      // THEN
      expect(categorieFormService.getCategorie).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(categorieService.update).toHaveBeenCalledWith(expect.objectContaining(categorie));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICategorie>>();
      const categorie = { id: 7213 };
      jest.spyOn(categorieFormService, 'getCategorie').mockReturnValue({ id: null });
      jest.spyOn(categorieService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ categorie: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: categorie }));
      saveSubject.complete();

      // THEN
      expect(categorieFormService.getCategorie).toHaveBeenCalled();
      expect(categorieService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICategorie>>();
      const categorie = { id: 7213 };
      jest.spyOn(categorieService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ categorie });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(categorieService.update).toHaveBeenCalled();
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
