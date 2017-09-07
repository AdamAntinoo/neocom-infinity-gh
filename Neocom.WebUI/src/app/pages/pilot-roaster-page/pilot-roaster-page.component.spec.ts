import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PilotRoasterPageComponent } from './pilot-roaster-page.component';

describe('PilotRoasterPageComponent', () => {
  let component: PilotRoasterPageComponent;
  let fixture: ComponentFixture<PilotRoasterPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PilotRoasterPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PilotRoasterPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
