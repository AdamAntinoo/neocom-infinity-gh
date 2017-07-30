import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PageSplashComponent } from './page-splash.component';

describe('PageSplashComponent', () => {
  let component: PageSplashComponent;
  let fixture: ComponentFixture<PageSplashComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PageSplashComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PageSplashComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
