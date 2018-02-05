import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomSerializationPageComponent } from './custom-serialization-page.component';

describe('CustomSerializationPageComponent', () => {
  let component: CustomSerializationPageComponent;
  let fixture: ComponentFixture<CustomSerializationPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CustomSerializationPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CustomSerializationPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
