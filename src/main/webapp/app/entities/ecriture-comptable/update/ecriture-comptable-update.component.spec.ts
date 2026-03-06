import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { EcritureComptableService } from '../service/ecriture-comptable.service';
import { IEcritureComptable } from '../../../shared/model/principal/ecriture-comptable.model';
import { EcritureComptableFormService } from './ecriture-comptable-form.service';

import { EcritureComptableUpdateComponent } from './ecriture-comptable-update.component';

describe('EcritureComptable Management Update Component', () => {
  let comp: EcritureComptableUpdateComponent;
  let fixture: ComponentFixture<EcritureComptableUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let ecritureComptableFormService: EcritureComptableFormService;
  let ecritureComptableService: EcritureComptableService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [EcritureComptableUpdateComponent],
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
      .overrideTemplate(EcritureComptableUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EcritureComptableUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    ecritureComptableFormService = TestBed.inject(EcritureComptableFormService);
    ecritureComptableService = TestBed.inject(EcritureComptableService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const ecritureComptable: IEcritureComptable = { id: 27513 };

      activatedRoute.data = of({ ecritureComptable });
      comp.ngOnInit();

      expect(comp.ecritureComptable).toEqual(ecritureComptable);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEcritureComptable>>();
      const ecritureComptable = { id: 30335 };
      jest.spyOn(ecritureComptableFormService, 'getEcritureComptable').mockReturnValue(ecritureComptable);
      jest.spyOn(ecritureComptableService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ecritureComptable });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ecritureComptable }));
      saveSubject.complete();

      // THEN
      expect(ecritureComptableFormService.getEcritureComptable).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(ecritureComptableService.update).toHaveBeenCalledWith(expect.objectContaining(ecritureComptable));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEcritureComptable>>();
      const ecritureComptable = { id: 30335 };
      jest.spyOn(ecritureComptableFormService, 'getEcritureComptable').mockReturnValue({ id: null });
      jest.spyOn(ecritureComptableService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ecritureComptable: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ecritureComptable }));
      saveSubject.complete();

      // THEN
      expect(ecritureComptableFormService.getEcritureComptable).toHaveBeenCalled();
      expect(ecritureComptableService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEcritureComptable>>();
      const ecritureComptable = { id: 30335 };
      jest.spyOn(ecritureComptableService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ecritureComptable });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(ecritureComptableService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
