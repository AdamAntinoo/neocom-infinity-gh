import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PlanetaryOptimizationPageComponent } from './planetary-optimization-page.component';

describe('PlanetaryOptimizationPageComponent', () => {
  let component: PlanetaryOptimizationPageComponent;
  let fixture: ComponentFixture<PlanetaryOptimizationPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PlanetaryOptimizationPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PlanetaryOptimizationPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
