import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SharedListTableComponent } from './shared-list-table.component';

describe('SharedListTableComponent', () => {
  let component: SharedListTableComponent;
  let fixture: ComponentFixture<SharedListTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SharedListTableComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(SharedListTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
