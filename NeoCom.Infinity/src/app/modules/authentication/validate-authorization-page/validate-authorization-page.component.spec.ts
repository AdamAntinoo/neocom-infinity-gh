import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ValidateAuthorizationPageComponent } from './validate-authorization-page.component';

describe('ValidateAuthorizationPageComponent', () => {
  let component: ValidateAuthorizationPageComponent;
  let fixture: ComponentFixture<ValidateAuthorizationPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ValidateAuthorizationPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ValidateAuthorizationPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
