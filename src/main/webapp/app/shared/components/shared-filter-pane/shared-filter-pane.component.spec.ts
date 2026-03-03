import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SharedFilterPaneComponent } from './shared-filter-pane.component';

describe('SharedFilterPaneComponent', () => {
  let component: SharedFilterPaneComponent;
  let fixture: ComponentFixture<SharedFilterPaneComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SharedFilterPaneComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(SharedFilterPaneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
