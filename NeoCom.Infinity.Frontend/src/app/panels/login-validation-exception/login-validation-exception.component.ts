import { Component, OnInit, Input } from '@angular/core';

@Component({
    selector: 'app-login-validation-exception',
    templateUrl: './login-validation-exception.component.html',
    styleUrls: ['./login-validation-exception.component.scss']
})
export class LoginValidationExceptionComponent implements OnInit {
    @Input() validationException: any;
    constructor() { }

    ngOnInit() {
    }
    public getMessage(): string {
        if (null != this.validationException) return this.validationException.message;
        else return '';
    }
}
