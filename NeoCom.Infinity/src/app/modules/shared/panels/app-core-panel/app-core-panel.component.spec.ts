import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppCorePanelComponent } from './app-core-panel.component';

describe('AppCorePanelComponent', () => {
  let component: AppCorePanelComponent;
  let fixture: ComponentFixture<AppCorePanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppCorePanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppCorePanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
