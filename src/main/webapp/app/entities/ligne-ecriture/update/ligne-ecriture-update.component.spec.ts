import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IEcritureComptable } from 'app/entities/ecriture-comptable/ecriture-comptable.model';
import { EcritureComptableService } from 'app/entities/ecriture-comptable/service/ecriture-comptable.service';
import { ICompteComptable } from 'app/entities/compte-comptable/compte-comptable.model';
import { CompteComptableService } from 'app/entities/compte-comptable/service/compte-comptable.service';
import { ILigneEcriture } from '../ligne-ecriture.model';
import { LigneEcritureService } from '../service/ligne-ecriture.service';
import { LigneEcritureFormService } from './ligne-ecriture-form.service';

import { LigneEcritureUpdateComponent } from './ligne-ecriture-update.component';

describe('LigneEcriture Management Update Component', () => {
  let comp: LigneEcritureUpdateComponent;
  let fixture: ComponentFixture<LigneEcritureUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let ligneEcritureFormService: LigneEcritureFormService;
  let ligneEcritureService: LigneEcritureService;
  let ecritureComptableService: EcritureComptableService;
  let compteComptableService: CompteComptableService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [LigneEcritureUpdateComponent],
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
      .overrideTemplate(LigneEcritureUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LigneEcritureUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    ligneEcritureFormService = TestBed.inject(LigneEcritureFormService);
    ligneEcritureService = TestBed.inject(LigneEcritureService);
    ecritureComptableService = TestBed.inject(EcritureComptableService);
    compteComptableService = TestBed.inject(CompteComptableService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call EcritureComptable query and add missing value', () => {
      const ligneEcriture: ILigneEcriture = { id: 28772 };
      const ecriture: IEcritureComptable = { id: 30335 };
      ligneEcriture.ecriture = ecriture;

      const ecritureComptableCollection: IEcritureComptable[] = [{ id: 30335 }];
      jest.spyOn(ecritureComptableService, 'query').mockReturnValue(of(new HttpResponse({ body: ecritureComptableCollection })));
      const additionalEcritureComptables = [ecriture];
      const expectedCollection: IEcritureComptable[] = [...additionalEcritureComptables, ...ecritureComptableCollection];
      jest.spyOn(ecritureComptableService, 'addEcritureComptableToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ ligneEcriture });
      comp.ngOnInit();

      expect(ecritureComptableService.query).toHaveBeenCalled();
      expect(ecritureComptableService.addEcritureComptableToCollectionIfMissing).toHaveBeenCalledWith(
        ecritureComptableCollection,
        ...additionalEcritureComptables.map(expect.objectContaining),
      );
      expect(comp.ecritureComptablesSharedCollection).toEqual(expectedCollection);
    });

    it('should call CompteComptable query and add missing value', () => {
      const ligneEcriture: ILigneEcriture = { id: 28772 };
      const compte: ICompteComptable = { id: 9635 };
      ligneEcriture.compte = compte;

      const compteComptableCollection: ICompteComptable[] = [{ id: 9635 }];
      jest.spyOn(compteComptableService, 'query').mockReturnValue(of(new HttpResponse({ body: compteComptableCollection })));
      const additionalCompteComptables = [compte];
      const expectedCollection: ICompteComptable[] = [...additionalCompteComptables, ...compteComptableCollection];
      jest.spyOn(compteComptableService, 'addCompteComptableToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ ligneEcriture });
      comp.ngOnInit();

      expect(compteComptableService.query).toHaveBeenCalled();
      expect(compteComptableService.addCompteComptableToCollectionIfMissing).toHaveBeenCalledWith(
        compteComptableCollection,
        ...additionalCompteComptables.map(expect.objectContaining),
      );
      expect(comp.compteComptablesSharedCollection).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const ligneEcriture: ILigneEcriture = { id: 28772 };
      const ecriture: IEcritureComptable = { id: 30335 };
      ligneEcriture.ecriture = ecriture;
      const compte: ICompteComptable = { id: 9635 };
      ligneEcriture.compte = compte;

      activatedRoute.data = of({ ligneEcriture });
      comp.ngOnInit();

      expect(comp.ecritureComptablesSharedCollection).toContainEqual(ecriture);
      expect(comp.compteComptablesSharedCollection).toContainEqual(compte);
      expect(comp.ligneEcriture).toEqual(ligneEcriture);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILigneEcriture>>();
      const ligneEcriture = { id: 32726 };
      jest.spyOn(ligneEcritureFormService, 'getLigneEcriture').mockReturnValue(ligneEcriture);
      jest.spyOn(ligneEcritureService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ligneEcriture });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ligneEcriture }));
      saveSubject.complete();

      // THEN
      expect(ligneEcritureFormService.getLigneEcriture).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(ligneEcritureService.update).toHaveBeenCalledWith(expect.objectContaining(ligneEcriture));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILigneEcriture>>();
      const ligneEcriture = { id: 32726 };
      jest.spyOn(ligneEcritureFormService, 'getLigneEcriture').mockReturnValue({ id: null });
      jest.spyOn(ligneEcritureService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ligneEcriture: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ligneEcriture }));
      saveSubject.complete();

      // THEN
      expect(ligneEcritureFormService.getLigneEcriture).toHaveBeenCalled();
      expect(ligneEcritureService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILigneEcriture>>();
      const ligneEcriture = { id: 32726 };
      jest.spyOn(ligneEcritureService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ligneEcriture });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(ligneEcritureService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEcritureComptable', () => {
      it('should forward to ecritureComptableService', () => {
        const entity = { id: 30335 };
        const entity2 = { id: 27513 };
        jest.spyOn(ecritureComptableService, 'compareEcritureComptable');
        comp.compareEcritureComptable(entity, entity2);
        expect(ecritureComptableService.compareEcritureComptable).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCompteComptable', () => {
      it('should forward to compteComptableService', () => {
        const entity = { id: 9635 };
        const entity2 = { id: 15299 };
        jest.spyOn(compteComptableService, 'compareCompteComptable');
        comp.compareCompteComptable(entity, entity2);
        expect(compteComptableService.compareCompteComptable).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
