import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MVCViewerComponent } from './mvcviewer.component';

describe('MVCViewerComponent', () => {
  let component: MVCViewerComponent;
  let fixture: ComponentFixture<MVCViewerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MVCViewerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MVCViewerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
