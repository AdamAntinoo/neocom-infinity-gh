import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SpinnerCentralComponent } from './spinner-central.component';

describe('SpinnerCentralComponent', () => {
  let component: SpinnerCentralComponent;
  let fixture: ComponentFixture<SpinnerCentralComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SpinnerCentralComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SpinnerCentralComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
