import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ExpandableNodeComponent } from './expandable-node.component';

describe('ExpandableNodeComponent', () => {
  let component: ExpandableNodeComponent;
  let fixture: ComponentFixture<ExpandableNodeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ExpandableNodeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ExpandableNodeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
