import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { EcritureComptableDetailComponent } from './ecriture-comptable-detail.component';

describe('EcritureComptable Management Detail Component', () => {
  let comp: EcritureComptableDetailComponent;
  let fixture: ComponentFixture<EcritureComptableDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EcritureComptableDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./ecriture-comptable-detail.component').then(m => m.EcritureComptableDetailComponent),
              resolve: { ecritureComptable: () => of({ id: 30335 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(EcritureComptableDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EcritureComptableDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load ecritureComptable on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', EcritureComptableDetailComponent);

      // THEN
      expect(instance.ecritureComptable()).toEqual(expect.objectContaining({ id: 30335 }));
    });
  });

  describe('PreviousState', () => {
    it('should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
