import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IEntiteFinanciere } from 'app/shared/model/principal/entite-financiere.model';
import { EntiteFinanciereService } from 'app/entities/entite-financiere/service/entite-financiere.service';
import { ICaisse } from 'app/shared/model/principal/caisse.model';
import { CaisseService } from 'app/entities/caisse/service/caisse.service';
import { ITransfert } from '../../../shared/model/referentiel/transfert.model';
import { TransfertService } from '../service/transfert.service';
import { TransfertFormService } from './transfert-form.service';

import { TransfertUpdateComponent } from './transfert-update.component';

describe('Transfert Management Update Component', () => {
  let comp: TransfertUpdateComponent;
  let fixture: ComponentFixture<TransfertUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let transfertFormService: TransfertFormService;
  let transfertService: TransfertService;
  let entiteFinanciereService: EntiteFinanciereService;
  let caisseService: CaisseService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TransfertUpdateComponent],
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
      .overrideTemplate(TransfertUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TransfertUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    transfertFormService = TestBed.inject(TransfertFormService);
    transfertService = TestBed.inject(TransfertService);
    entiteFinanciereService = TestBed.inject(EntiteFinanciereService);
    caisseService = TestBed.inject(CaisseService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call EntiteFinanciere query and add missing value', () => {
      const transfert: ITransfert = { id: 14214 };
      const entiteFinanciereSource: IEntiteFinanciere = { id: 8941 };
      transfert.entiteFinanciereSource = entiteFinanciereSource;

      const entiteFinanciereCollection: IEntiteFinanciere[] = [{ id: 8941 }];
      jest.spyOn(entiteFinanciereService, 'query').mockReturnValue(of(new HttpResponse({ body: entiteFinanciereCollection })));
      const additionalEntiteFinancieres = [entiteFinanciereSource];
      const expectedCollection: IEntiteFinanciere[] = [...additionalEntiteFinancieres, ...entiteFinanciereCollection];
      jest.spyOn(entiteFinanciereService, 'addEntiteFinanciereToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ transfert });
      comp.ngOnInit();

      expect(entiteFinanciereService.query).toHaveBeenCalled();
      expect(entiteFinanciereService.addEntiteFinanciereToCollectionIfMissing).toHaveBeenCalledWith(
        entiteFinanciereCollection,
        ...additionalEntiteFinancieres.map(expect.objectContaining),
      );
      expect(comp.entiteFinancieresSharedCollection).toEqual(expectedCollection);
    });

    it('should call Caisse query and add missing value', () => {
      const transfert: ITransfert = { id: 14214 };
      const caisseSource: ICaisse = { id: 3599 };
      transfert.caisseSource = caisseSource;
      const caisseDestination: ICaisse = { id: 3599 };
      transfert.caisseDestination = caisseDestination;

      const caisseCollection: ICaisse[] = [{ id: 3599 }];
      jest.spyOn(caisseService, 'query').mockReturnValue(of(new HttpResponse({ body: caisseCollection })));
      const additionalCaisses = [caisseSource, caisseDestination];
      const expectedCollection: ICaisse[] = [...additionalCaisses, ...caisseCollection];
      jest.spyOn(caisseService, 'addCaisseToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ transfert });
      comp.ngOnInit();

      expect(caisseService.query).toHaveBeenCalled();
      expect(caisseService.addCaisseToCollectionIfMissing).toHaveBeenCalledWith(
        caisseCollection,
        ...additionalCaisses.map(expect.objectContaining),
      );
      expect(comp.caissesSharedCollection).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const transfert: ITransfert = { id: 14214 };
      const entiteFinanciereSource: IEntiteFinanciere = { id: 8941 };
      transfert.entiteFinanciereSource = entiteFinanciereSource;
      const caisseSource: ICaisse = { id: 3599 };
      transfert.caisseSource = caisseSource;
      const caisseDestination: ICaisse = { id: 3599 };
      transfert.caisseDestination = caisseDestination;

      activatedRoute.data = of({ transfert });
      comp.ngOnInit();

      expect(comp.entiteFinancieresSharedCollection).toContainEqual(entiteFinanciereSource);
      expect(comp.caissesSharedCollection).toContainEqual(caisseSource);
      expect(comp.caissesSharedCollection).toContainEqual(caisseDestination);
      expect(comp.transfert).toEqual(transfert);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITransfert>>();
      const transfert = { id: 22898 };
      jest.spyOn(transfertFormService, 'getTransfert').mockReturnValue(transfert);
      jest.spyOn(transfertService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transfert });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: transfert }));
      saveSubject.complete();

      // THEN
      expect(transfertFormService.getTransfert).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(transfertService.update).toHaveBeenCalledWith(expect.objectContaining(transfert));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITransfert>>();
      const transfert = { id: 22898 };
      jest.spyOn(transfertFormService, 'getTransfert').mockReturnValue({ id: null });
      jest.spyOn(transfertService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transfert: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: transfert }));
      saveSubject.complete();

      // THEN
      expect(transfertFormService.getTransfert).toHaveBeenCalled();
      expect(transfertService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITransfert>>();
      const transfert = { id: 22898 };
      jest.spyOn(transfertService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transfert });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(transfertService.update).toHaveBeenCalled();
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
  });
});
