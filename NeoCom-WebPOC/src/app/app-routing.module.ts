import { NgModule } from '@angular/core';
import { Routes } from '@angular/router';
import { RouterModule } from '@angular/router';
//--- PAGES
import { SpinnerTestPageComponent } from './pages/spinner-test-page/spinner-test-page.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/testspinner',
    pathMatch: 'full'
  },
  { path: 'testspinner', component: SpinnerTestPageComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
