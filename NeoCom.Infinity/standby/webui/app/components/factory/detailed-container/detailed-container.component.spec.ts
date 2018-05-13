import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailedContainerComponent } from './detailed-container.component';

describe('DetailedContainerComponent', () => {
  let component: DetailedContainerComponent;
  let fixture: ComponentFixture<DetailedContainerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DetailedContainerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DetailedContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
