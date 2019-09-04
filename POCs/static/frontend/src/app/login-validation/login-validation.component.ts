import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-login-validation',
  templateUrl: './login-validation.component.html',
  styleUrls: ['./login-validation.component.scss']
})
export class LoginValidationComponent implements OnInit {
  param1: string;
  param2: string;
  constructor(private route: ActivatedRoute) {
    console.log('>LoginValidation');
    this.route.queryParams.subscribe(params => {
      this.param1 = params['code'];
      this.param2 = params['state'];
    });
    console.log('Parameters: '+'code='+this.param1);
    console.log('Parameters: '+'state='+this.param2);
  }

  ngOnInit() {
    // Read the request parameters and check the caller is verified.

  }

}
