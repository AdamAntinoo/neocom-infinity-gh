import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AssetsManagerPageComponent } from './assets-manager-page.component';

describe('AssetsManagerPageComponent', () => {
  let component: AssetsManagerPageComponent;
  let fixture: ComponentFixture<AssetsManagerPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AssetsManagerPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssetsManagerPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
