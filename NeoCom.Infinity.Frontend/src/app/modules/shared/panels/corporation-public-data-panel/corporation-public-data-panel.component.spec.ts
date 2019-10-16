import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CorporationPublicDataPanelComponent } from './corporation-public-data-panel.component';

describe('CorporationPublicDataPanelComponent', () => {
  let component: CorporationPublicDataPanelComponent;
  let fixture: ComponentFixture<CorporationPublicDataPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CorporationPublicDataPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CorporationPublicDataPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
