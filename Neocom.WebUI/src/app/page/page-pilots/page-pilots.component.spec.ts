import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PagePilotsComponent } from './page-pilots.component';

describe('PagePilotsComponent', () => {
  let component: PagePilotsComponent;
  let fixture: ComponentFixture<PagePilotsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PagePilotsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PagePilotsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
