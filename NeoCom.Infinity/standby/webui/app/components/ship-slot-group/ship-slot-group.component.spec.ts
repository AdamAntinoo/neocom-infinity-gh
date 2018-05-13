import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShipSlotGroupComponent } from './ship-slot-group.component';

describe('ShipSlotGroupComponent', () => {
  let component: ShipSlotGroupComponent;
  let fixture: ComponentFixture<ShipSlotGroupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShipSlotGroupComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShipSlotGroupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
