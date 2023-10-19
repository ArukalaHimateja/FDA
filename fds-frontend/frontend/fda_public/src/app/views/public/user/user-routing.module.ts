import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProfileComponent } from './profile/profile.component';
import { OrderHistoryComponent } from './order-history/order-history.component';
import { PhotosComponent } from './photos/photos.component';
import { AddressComponent } from './address/address.component';
import { ReviewsComponent } from './reviews/reviews.component';

const routes: Routes = [
  { path: 'profile', component: ProfileComponent },
  { path: 'reviews', component: ReviewsComponent },
  { path: 'photos', component: PhotosComponent },
  { path: 'order/history', component: OrderHistoryComponent },
  { path: 'address', component: AddressComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }
