import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FittingManagerPageComponent } from './fitting-manager-page.component';

describe('FittingManagerPageComponent', () => {
  let component: FittingManagerPageComponent;
  let fixture: ComponentFixture<FittingManagerPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FittingManagerPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FittingManagerPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
