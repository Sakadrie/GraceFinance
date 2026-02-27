import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { LigneEcritureDetailComponent } from './ligne-ecriture-detail.component';

describe('LigneEcriture Management Detail Component', () => {
  let comp: LigneEcritureDetailComponent;
  let fixture: ComponentFixture<LigneEcritureDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LigneEcritureDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./ligne-ecriture-detail.component').then(m => m.LigneEcritureDetailComponent),
              resolve: { ligneEcriture: () => of({ id: 32726 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(LigneEcritureDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LigneEcritureDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load ligneEcriture on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', LigneEcritureDetailComponent);

      // THEN
      expect(instance.ligneEcriture()).toEqual(expect.objectContaining({ id: 32726 }));
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
