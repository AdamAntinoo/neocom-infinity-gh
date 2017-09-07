import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Pilot4RoasterComponent } from './pilot4-roaster.component';

describe('Pilot4RoasterComponent', () => {
  let component: Pilot4RoasterComponent;
  let fixture: ComponentFixture<Pilot4RoasterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Pilot4RoasterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Pilot4RoasterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
