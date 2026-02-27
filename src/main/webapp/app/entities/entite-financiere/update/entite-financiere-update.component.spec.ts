import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { EntiteFinanciereService } from '../service/entite-financiere.service';
import { IEntiteFinanciere } from '../entite-financiere.model';
import { EntiteFinanciereFormService } from './entite-financiere-form.service';

import { EntiteFinanciereUpdateComponent } from './entite-financiere-update.component';

describe('EntiteFinanciere Management Update Component', () => {
  let comp: EntiteFinanciereUpdateComponent;
  let fixture: ComponentFixture<EntiteFinanciereUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let entiteFinanciereFormService: EntiteFinanciereFormService;
  let entiteFinanciereService: EntiteFinanciereService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [EntiteFinanciereUpdateComponent],
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
      .overrideTemplate(EntiteFinanciereUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EntiteFinanciereUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    entiteFinanciereFormService = TestBed.inject(EntiteFinanciereFormService);
    entiteFinanciereService = TestBed.inject(EntiteFinanciereService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call EntiteFinanciere query and add missing value', () => {
      const entiteFinanciere: IEntiteFinanciere = { id: 4924 };
      const egliseLiees: IEntiteFinanciere[] = [{ id: 8941 }];
      entiteFinanciere.egliseLiees = egliseLiees;
      const structureLiees: IEntiteFinanciere[] = [{ id: 8941 }];
      entiteFinanciere.structureLiees = structureLiees;

      const entiteFinanciereCollection: IEntiteFinanciere[] = [{ id: 8941 }];
      jest.spyOn(entiteFinanciereService, 'query').mockReturnValue(of(new HttpResponse({ body: entiteFinanciereCollection })));
      const additionalEntiteFinancieres = [...egliseLiees, ...structureLiees];
      const expectedCollection: IEntiteFinanciere[] = [...additionalEntiteFinancieres, ...entiteFinanciereCollection];
      jest.spyOn(entiteFinanciereService, 'addEntiteFinanciereToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ entiteFinanciere });
      comp.ngOnInit();

      expect(entiteFinanciereService.query).toHaveBeenCalled();
      expect(entiteFinanciereService.addEntiteFinanciereToCollectionIfMissing).toHaveBeenCalledWith(
        entiteFinanciereCollection,
        ...additionalEntiteFinancieres.map(expect.objectContaining),
      );
      expect(comp.entiteFinancieresSharedCollection).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const entiteFinanciere: IEntiteFinanciere = { id: 4924 };
      const egliseLiee: IEntiteFinanciere = { id: 8941 };
      entiteFinanciere.egliseLiees = [egliseLiee];
      const structureLiee: IEntiteFinanciere = { id: 8941 };
      entiteFinanciere.structureLiees = [structureLiee];

      activatedRoute.data = of({ entiteFinanciere });
      comp.ngOnInit();

      expect(comp.entiteFinancieresSharedCollection).toContainEqual(egliseLiee);
      expect(comp.entiteFinancieresSharedCollection).toContainEqual(structureLiee);
      expect(comp.entiteFinanciere).toEqual(entiteFinanciere);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEntiteFinanciere>>();
      const entiteFinanciere = { id: 8941 };
      jest.spyOn(entiteFinanciereFormService, 'getEntiteFinanciere').mockReturnValue(entiteFinanciere);
      jest.spyOn(entiteFinanciereService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ entiteFinanciere });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: entiteFinanciere }));
      saveSubject.complete();

      // THEN
      expect(entiteFinanciereFormService.getEntiteFinanciere).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(entiteFinanciereService.update).toHaveBeenCalledWith(expect.objectContaining(entiteFinanciere));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEntiteFinanciere>>();
      const entiteFinanciere = { id: 8941 };
      jest.spyOn(entiteFinanciereFormService, 'getEntiteFinanciere').mockReturnValue({ id: null });
      jest.spyOn(entiteFinanciereService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ entiteFinanciere: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: entiteFinanciere }));
      saveSubject.complete();

      // THEN
      expect(entiteFinanciereFormService.getEntiteFinanciere).toHaveBeenCalled();
      expect(entiteFinanciereService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEntiteFinanciere>>();
      const entiteFinanciere = { id: 8941 };
      jest.spyOn(entiteFinanciereService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ entiteFinanciere });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(entiteFinanciereService.update).toHaveBeenCalled();
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
