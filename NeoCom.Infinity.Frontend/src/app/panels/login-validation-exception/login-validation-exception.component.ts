// - CORE
import { Component } from '@angular/core';
import { Input } from '@angular/core';

@Component({
    selector: 'app-login-validation-exception',
    templateUrl: './login-validation-exception.component.html',
    styleUrls: ['./login-validation-exception.component.scss']
})
export class LoginValidationExceptionComponent /*implements OnInit*/ {
    @Input() exception: any;
    constructor() { }

    ngOnInit() {
    }
    public getMessage(): string {
        if (null != this.exception) return this.exception.message;
        else return '';
    }
}
