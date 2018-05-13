import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PlanetaryManagerComponent } from './planetary-manager.component';

describe('PlanetaryManagerComponent', () => {
  let component: PlanetaryManagerComponent;
  let fixture: ComponentFixture<PlanetaryManagerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PlanetaryManagerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PlanetaryManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
