import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { EntiteFinanciereDetailComponent } from './entite-financiere-detail.component';

describe('EntiteFinanciere Management Detail Component', () => {
  let comp: EntiteFinanciereDetailComponent;
  let fixture: ComponentFixture<EntiteFinanciereDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EntiteFinanciereDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./entite-financiere-detail.component').then(m => m.EntiteFinanciereDetailComponent),
              resolve: { entiteFinanciere: () => of({ id: 8941 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(EntiteFinanciereDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EntiteFinanciereDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load entiteFinanciere on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', EntiteFinanciereDetailComponent);

      // THEN
      expect(instance.entiteFinanciere()).toEqual(expect.objectContaining({ id: 8941 }));
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
