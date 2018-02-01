import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProcessingActionComponent } from './processing-action.component';

describe('ProcessingActionComponent', () => {
  let component: ProcessingActionComponent;
  let fixture: ComponentFixture<ProcessingActionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProcessingActionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProcessingActionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
