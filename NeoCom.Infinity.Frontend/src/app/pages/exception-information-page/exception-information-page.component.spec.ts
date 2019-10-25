import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ExceptionInformationPageComponent } from './exception-information-page.component';

xdescribe('ExceptionInformationPageComponent', () => {
    let component: ExceptionInformationPageComponent;
    let fixture: ComponentFixture<ExceptionInformationPageComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [ExceptionInformationPageComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(ExceptionInformationPageComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
