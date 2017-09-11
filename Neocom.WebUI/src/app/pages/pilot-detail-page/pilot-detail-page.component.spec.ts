import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PilotDetailPageComponent } from './pilot-detail-page.component';

describe('PilotDetailPageComponent', () => {
  let component: PilotDetailPageComponent;
  let fixture: ComponentFixture<PilotDetailPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PilotDetailPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PilotDetailPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
