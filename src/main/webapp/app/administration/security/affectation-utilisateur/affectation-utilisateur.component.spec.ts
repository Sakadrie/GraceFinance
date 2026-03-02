import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AffectationUtilisateurComponent } from './affectation-utilisateur.component';

describe('AffectationUtilisateurComponent', () => {
  let component: AffectationUtilisateurComponent;
  let fixture: ComponentFixture<AffectationUtilisateurComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AffectationUtilisateurComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(AffectationUtilisateurComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
