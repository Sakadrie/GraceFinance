import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { DroitDetailComponent } from './droit-detail.component';

describe('Droit Management Detail Component', () => {
  let comp: DroitDetailComponent;
  let fixture: ComponentFixture<DroitDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DroitDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./droit-detail.component').then(m => m.DroitDetailComponent),
              resolve: { droit: () => of({ id: 23804 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(DroitDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DroitDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load droit on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', DroitDetailComponent);

      // THEN
      expect(instance.droit()).toEqual(expect.objectContaining({ id: 23804 }));
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
