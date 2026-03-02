import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EntiteFinanciereComponent } from './entite-financiere.component';

describe('EntiteFinanciereComponent', () => {
  let component: EntiteFinanciereComponent;
  let fixture: ComponentFixture<EntiteFinanciereComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EntiteFinanciereComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(EntiteFinanciereComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
