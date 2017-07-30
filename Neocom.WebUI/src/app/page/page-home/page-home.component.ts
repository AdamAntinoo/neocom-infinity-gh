import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie';

@Component({
  selector: 'neocom-page-home',
  templateUrl: './page-home.component.html',
  styleUrls: ['./page-home.component.css']
})
export class PageHomeComponent implements OnInit {

  constructor(private _cookieService: CookieService) { }

  ngOnInit() {
    // Define the cookie and initialize it to the login predefined value.
    this._cookieService.put("login-id", "default")
  }

}
