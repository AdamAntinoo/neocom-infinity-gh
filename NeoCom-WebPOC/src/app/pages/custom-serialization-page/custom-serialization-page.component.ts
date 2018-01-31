import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-custom-serialization-page',
  templateUrl: './custom-serialization-page.component.html',
  styleUrls: ['./custom-serialization-page.component.css']
})
export class CustomSerializationPageComponent implements OnInit {

  constructor() { }

  /** During the initialization we should use an special Service. Services are always
   available from application start so they are the natural place to store
   application global data. So we can use the Service to retrieve from the database
   that list or to return the already cached list if that operation was already done.
   */
  ngOnInit() {
    console.log(">>[LoginPageComponent.ngOnInit]");
    this.refreshViewPort();
    console.log("<<[LoginPageComponent.ngOnInit]");
  }

}
