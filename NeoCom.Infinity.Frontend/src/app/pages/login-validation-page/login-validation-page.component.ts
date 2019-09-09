import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-login-validation-page',
  templateUrl: './login-validation-page.component.html',
  styleUrls: ['./login-validation-page.component.scss']
})
export class LoginValidationPageComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }
  public getAppName(): string {
    return 'NeoCom';
  }
  public getAppVersion(): string {
    return 'v0.16.1';
  }
}
