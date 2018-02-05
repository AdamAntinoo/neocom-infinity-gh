import { NgModule } from '@angular/core';
import { Routes } from '@angular/router';
import { RouterModule } from '@angular/router';
//--- PAGES
import { SpinnerTestPageComponent } from './pages/spinner-test-page/spinner-test-page.component';
import { PlanetaryOptimizationPageComponentMock } from './pages/planetary-optimization-page/planetary-optimization-page-mock.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/testspinner',
    pathMatch: 'full'
  },
  { path: 'testspinner', component: SpinnerTestPageComponent },
  { path: 'testplanetaryoptimization', component: PlanetaryOptimizationPageComponentMock },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
