import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PilotPublicDataPanelComponent } from './pilot-public-data-panel.component';

describe('PilotPublicDataPanelComponent', () => {
  let component: PilotPublicDataPanelComponent;
  let fixture: ComponentFixture<PilotPublicDataPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PilotPublicDataPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PilotPublicDataPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
