import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { TransfertDetailComponent } from './transfert-detail.component';

describe('Transfert Management Detail Component', () => {
  let comp: TransfertDetailComponent;
  let fixture: ComponentFixture<TransfertDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TransfertDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./transfert-detail.component').then(m => m.TransfertDetailComponent),
              resolve: { transfert: () => of({ id: 22898 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(TransfertDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TransfertDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load transfert on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', TransfertDetailComponent);

      // THEN
      expect(instance.transfert()).toEqual(expect.objectContaining({ id: 22898 }));
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
