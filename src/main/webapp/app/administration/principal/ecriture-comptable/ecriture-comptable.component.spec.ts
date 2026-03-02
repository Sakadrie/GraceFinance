import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EcritureComptableComponent } from './ecriture-comptable.component';

describe('EcritureComptableComponent', () => {
  let component: EcritureComptableComponent;
  let fixture: ComponentFixture<EcritureComptableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EcritureComptableComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(EcritureComptableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
