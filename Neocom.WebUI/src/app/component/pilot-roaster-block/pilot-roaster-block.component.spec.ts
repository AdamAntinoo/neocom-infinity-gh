import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PilotRoasterBlockComponent } from './pilot-roaster-block.component';

describe('PilotRoasterBlockComponent', () => {
  let component: PilotRoasterBlockComponent;
  let fixture: ComponentFixture<PilotRoasterBlockComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PilotRoasterBlockComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PilotRoasterBlockComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
