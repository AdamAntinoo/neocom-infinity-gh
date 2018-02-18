import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FittingItemComponent } from './fitting-item.component';

describe('FittingItemComponent', () => {
  let component: FittingItemComponent;
  let fixture: ComponentFixture<FittingItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FittingItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FittingItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
