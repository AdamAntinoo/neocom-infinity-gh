// - CORE
import { Component } from '@angular/core';
import { Routes } from '@angular/router';

/**
 * This is an empty component to be pointed with valid routes.
 *
 * @export
 * @class HomeComponent
 */
@Component({
    template: `Home`
})
export class RouteMockUpComponent {}
export const routes: Routes = [
    { path: '', redirectTo: 'loginValidation', pathMatch: 'full' },
    { path: 'loginValidation', component: RouteMockUpComponent },
    {
        path: 'loginValidation4?code=-ANY-CODE-&state=LU5FT0NPTS5JTkZJTklUWS1ERVZFTE9QTUVOVC1WQUxJRCBTVEFURSBTVFJJTkct',
        component: RouteMockUpComponent
    },
    { path: 'dashboard', component: RouteMockUpComponent },
    { path: 'dashboard/home', component: RouteMockUpComponent },
];
// - E N D   O F   R O U T I N G   C O M P O N E N T
