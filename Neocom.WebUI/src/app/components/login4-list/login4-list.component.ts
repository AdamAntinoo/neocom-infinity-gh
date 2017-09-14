import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import 'rxjs/add/operator/switchMap';
import { Input } from '@angular/core';

//--- MODELS
import { Login } from '../../models/Login.model';

@Component({
  selector: 'neocom-login4-list',
  templateUrl: './login4-list.component.html',
  styleUrls: ['./login4-list.component.css']
})
export class Login4ListComponent implements OnInit {
  @Input() node: Login;

  constructor() { }

  ngOnInit() {
  }

}
