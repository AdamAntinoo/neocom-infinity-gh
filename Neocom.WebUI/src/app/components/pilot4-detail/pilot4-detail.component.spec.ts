import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Pilot4DetailComponent } from './pilot4-detail.component';

describe('Pilot4DetailComponent', () => {
  let component: Pilot4DetailComponent;
  let fixture: ComponentFixture<Pilot4DetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Pilot4DetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Pilot4DetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
