import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { UserComponent } from './user/user.component';
import { AuthGuardService } from './session/auth/auth-guard.service';
import { FoodDetailComponent } from './food-detail/food-detail.component';
import { CartComponent } from './cart/cart.component';
import { ContactComponent } from './contact/contact.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  {
    path: 'contact',
    component: ContactComponent,
    loadChildren: () => import('./contact/contact.module').then(m => m.ContactModule)
  },
  { path: 'session', loadChildren: () => import('./session/session.module').then(m => m.SessionModule) },
  {
    path: 'user',
    component: UserComponent,
    canActivate: [AuthGuardService],
    loadChildren: () => import('./user/user.module').then(m => m.UserModule)
  },
  {
    path: 'foodDetail/:id',
    component: FoodDetailComponent,
    loadChildren: () => import('./food-detail/food-detail.module').then(m => m.FoodDetailModule)
  },
  { path: 'cart', component: CartComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PublicRoutingModule { }
