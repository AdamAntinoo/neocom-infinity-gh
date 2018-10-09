import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MVControllerComponent } from './mvcontroller.component';

describe('MVCViewerComponent', () => {
  let component: MVControllerComponent;
  let fixture: ComponentFixture<MVControllerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MVControllerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MVControllerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
