import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PlanetaryManagerPageComponent } from './planetary-manager-page.component';

describe('PlanetaryManagerPageComponent', () => {
  let component: PlanetaryManagerPageComponent;
  let fixture: ComponentFixture<PlanetaryManagerPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PlanetaryManagerPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PlanetaryManagerPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
