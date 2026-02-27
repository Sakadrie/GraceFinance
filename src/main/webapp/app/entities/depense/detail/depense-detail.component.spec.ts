import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { DepenseDetailComponent } from './depense-detail.component';

describe('Depense Management Detail Component', () => {
  let comp: DepenseDetailComponent;
  let fixture: ComponentFixture<DepenseDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DepenseDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./depense-detail.component').then(m => m.DepenseDetailComponent),
              resolve: { depense: () => of({ id: 1915 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(DepenseDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DepenseDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load depense on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', DepenseDetailComponent);

      // THEN
      expect(instance.depense()).toEqual(expect.objectContaining({ id: 1915 }));
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
