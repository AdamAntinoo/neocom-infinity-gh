import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PilotDashboardPageComponent } from './pilot-dashboard-page.component';

describe('PilotDashboardPageComponent', () => {
  let component: PilotDashboardPageComponent;
  let fixture: ComponentFixture<PilotDashboardPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PilotDashboardPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PilotDashboardPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
