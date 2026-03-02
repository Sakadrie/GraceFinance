import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LigneEcritureComponent } from './ligne-ecriture.component';

describe('LigneEcritureComponent', () => {
  let component: LigneEcritureComponent;
  let fixture: ComponentFixture<LigneEcritureComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LigneEcritureComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(LigneEcritureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
