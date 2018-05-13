import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Manager4PilotComponent } from './manager4-pilot.component';

describe('Manager4PilotComponent', () => {
  let component: Manager4PilotComponent;
  let fixture: ComponentFixture<Manager4PilotComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Manager4PilotComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Manager4PilotComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
