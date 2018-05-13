import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PilotManagerComponent } from './pilot-manager.component';

describe('PilotManagerComponent', () => {
  let component: PilotManagerComponent;
  let fixture: ComponentFixture<PilotManagerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PilotManagerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PilotManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
