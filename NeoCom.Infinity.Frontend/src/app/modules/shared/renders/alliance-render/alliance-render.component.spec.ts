import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AllianceRenderComponent } from './alliance-render.component';

describe('AllianceRenderComponent', () => {
  let component: AllianceRenderComponent;
  let fixture: ComponentFixture<AllianceRenderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AllianceRenderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AllianceRenderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
