// - CORE
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { Routes } from '@angular/router';

// - GUARDS
// import { AuthCommonGuard } from '@app/modules/authorization/auth-common.guard';
// import { AuthManagerGuard } from '@app/modules/authorization/auth-manager.guard';
// import { AuthAdminGuard } from '@app/modules/authorization/auth-admin.guard';

// - PAGES
import { LoginValidationPageComponent } from '@app/pages/login-validation-page/login-validation-page.component';
// import { NotFoundPage } from '@app/pages/not-found-page/not-found-page.component';
// import { LoginPageComponent } from '@app/modules/login/pages/login-page/login-page.component';
// import { DashboardPageComponent } from '@app/modules/login/pages/dashboard-page/dashboard-page.component';
// - CITACIONES - MODULE


const routes: Routes = [
  {
    path: '',
    redirectTo: '/loginValidation',
    pathMatch: 'full'
  },
  // - LOGIN PAGES
  { path: 'loginValidation', component: LoginValidationPageComponent },
  // --- REDIRECT NOT FOUND PAGES
  // { path: '**', component: NotFoundPage }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { enableTracing: false })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
