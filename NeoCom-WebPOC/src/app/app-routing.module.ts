import { NgModule } from '@angular/core';
import { Routes } from '@angular/router';
import { RouterModule } from '@angular/router';
//--- PAGES
import { PageSelectorComponent } from './pages/page-selector/page-selector.component';
import { SpinnerTestPageComponent } from './pages/spinner-test-page/spinner-test-page.component';
import { CustomSerializationPageComponent } from './pages/custom-serialization-page/custom-serialization-page.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/pageselector',
    pathMatch: 'full'
  },
  { path: 'pageselector', component: PageSelectorComponent },
  { path: 'testspinner', component: SpinnerTestPageComponent },
  { path: 'testserialization', component: CustomSerializationPageComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
