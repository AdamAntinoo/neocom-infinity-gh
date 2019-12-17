// - CORE
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { Routes } from '@angular/router';

// - GUARDS
import { TokenAuthorizationGuard } from './security/token-authorization.guard';

// - PAGES
import { LoginValidationPageComponent } from './pages/login-validation-page/login-validation-page.component';
import { DashboardHomePageComponent } from './pages/dashboard-home-page/dashboard-home-page.component';
import { ExceptionInformationPageComponent } from './pages/exception-information-page/exception-information-page.component';

const routes: Routes = [
    {
        path: '',
        redirectTo: '/loginValidation',
        pathMatch: 'full'
    },
    // - LOGIN PAGES
    { path: 'loginValidation', component: LoginValidationPageComponent },
    { path: 'exceptionInfo', component: ExceptionInformationPageComponent },
    { path: 'dashboard', component: DashboardHomePageComponent },
    // { path: 'dashboard', component: DashboardHomePageComponent, canActivate: [TokenAuthorizationGuard] },
    // - D A S H B O A R D
    // {
    //     path: 'dashboard',
    //     loadChildren: './modules/shared/shared.module#SharedModule',
    //     canActivate: [TokenAuthorizationGuard]
    // },
    // - REDIRECT NOT FOUND PAGES
    // { path: '**', component: NotFoundPage }
];

@NgModule({
    imports: [RouterModule.forRoot(routes, { enableTracing: false })],
    exports: [RouterModule]
})
export class AppRoutingModule { }
