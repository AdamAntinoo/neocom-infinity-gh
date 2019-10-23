// - CORE MODULES
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
// - ROUTING
import { RouterModule } from '@angular/router';
import { Routes } from '@angular/router';
import { RouteMockUpComponent } from './RouteMockUp.component';

// - MODULE ROUTES
const routes: Routes = [
];

@NgModule({
  imports: [
    CommonModule,
   //  RouterModule.forChild(routes),
  ],
  declarations: [
    // - PANELS
    RouteMockUpComponent,
  ],
  exports: [
    RouterModule,
    RouteMockUpComponent
  ]
})
export class TestingModule { }
