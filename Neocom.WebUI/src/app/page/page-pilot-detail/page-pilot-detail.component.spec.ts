import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PagePilotDetailComponent } from './page-pilot-detail.component';

describe('PagePilotDetailComponent', () => {
  let component: PagePilotDetailComponent;
  let fixture: ComponentFixture<PagePilotDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PagePilotDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PagePilotDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
