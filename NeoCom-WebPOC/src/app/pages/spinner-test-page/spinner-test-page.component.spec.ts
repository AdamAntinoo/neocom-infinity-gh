import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SpinnerTestPageComponent } from './spinner-test-page.component';

describe('SpinnerTestPageComponent', () => {
  let component: SpinnerTestPageComponent;
  let fixture: ComponentFixture<SpinnerTestPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SpinnerTestPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SpinnerTestPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
