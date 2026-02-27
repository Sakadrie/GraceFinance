import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { AffectationUtilisateurDetailComponent } from './affectation-utilisateur-detail.component';

describe('AffectationUtilisateur Management Detail Component', () => {
  let comp: AffectationUtilisateurDetailComponent;
  let fixture: ComponentFixture<AffectationUtilisateurDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AffectationUtilisateurDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./affectation-utilisateur-detail.component').then(m => m.AffectationUtilisateurDetailComponent),
              resolve: { affectationUtilisateur: () => of({ id: 4001 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AffectationUtilisateurDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AffectationUtilisateurDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load affectationUtilisateur on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AffectationUtilisateurDetailComponent);

      // THEN
      expect(instance.affectationUtilisateur()).toEqual(expect.objectContaining({ id: 4001 }));
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
