// - CORE
import { Component } from '@angular/core';
import { Input } from '@angular/core';

@Component({
    // tslint:disable-next-line: component-selector
    selector: 'app-login-validation-progress',
    templateUrl: './login-validation-progress.component.html',
    styleUrls: ['./login-validation-progress.component.scss']
})
export class LoginValidationProgressComponent /*implements OnInit*/ {
    @Input() code: string;

    // constructor() { }

    // ngOnInit() {
    // }
    public getCode(): string {
        if (null == this.code) return '-AWAITING-CODE-';
        else return this.code;
    }
}
