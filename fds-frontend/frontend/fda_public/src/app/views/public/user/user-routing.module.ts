import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProfileComponent } from './profile/profile.component';
import { OrderHistoryComponent } from './order-history/order-history.component';
import { PhotosComponent } from './photos/photos.component';
import { AddressComponent } from './address/address.component';
import { ReviewsComponent } from './reviews/reviews.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { OrderDetailComponent } from './order-detail/order-detail.component';

const routes: Routes = [
  { path: 'profile', component: ProfileComponent },
  { path: 'reviews', component: ReviewsComponent },
  { path: 'photos', component: PhotosComponent },
  { path: 'order/history', component: OrderHistoryComponent },
  { path: 'address', component: AddressComponent },
  { path: 'change/password', component: ChangePasswordComponent },
  { path: 'order/detail/:orderId', component: OrderDetailComponent },
  { path: 'order/detail/:orderId/:type', component: OrderDetailComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { 
}
