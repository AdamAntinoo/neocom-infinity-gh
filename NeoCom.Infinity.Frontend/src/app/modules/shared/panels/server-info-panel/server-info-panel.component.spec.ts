import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ServerInfoPanelComponent } from './server-info-panel.component';

describe('ServerInfoPanelComponent', () => {
  let component: ServerInfoPanelComponent;
  let fixture: ComponentFixture<ServerInfoPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ServerInfoPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ServerInfoPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
