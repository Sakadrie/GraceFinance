import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { CompteComptableDetailComponent } from './compte-comptable-detail.component';

describe('CompteComptable Management Detail Component', () => {
  let comp: CompteComptableDetailComponent;
  let fixture: ComponentFixture<CompteComptableDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CompteComptableDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./compte-comptable-detail.component').then(m => m.CompteComptableDetailComponent),
              resolve: { compteComptable: () => of({ id: 9635 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CompteComptableDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CompteComptableDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load compteComptable on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CompteComptableDetailComponent);

      // THEN
      expect(instance.compteComptable()).toEqual(expect.objectContaining({ id: 9635 }));
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
