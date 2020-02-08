import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TabContainerPanelComponent } from './tab-container-panel.component';

describe('TabContainerPanelComponent', () => {
  let component: TabContainerPanelComponent;
  let fixture: ComponentFixture<TabContainerPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TabContainerPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TabContainerPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
