import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './views/admin/admin.component';
import { SessionComponent } from './views/session/session.component';
import { AuthGuardService } from './views/session/auth/auth-guard.service';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/dashboard',
    pathMatch: 'full'
  },
  {
    path: '',
    component: AdminComponent,
    canActivate: [AuthGuardService],
    loadChildren: () => import('./views/admin/admin.module').then(m => m.AdminModule)
  },
  {
    path: 'session',
    component: SessionComponent,
    loadChildren: () => import('./views/session/session.module').then(m => m.SessionModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
