import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UserRoutingModule } from './user-routing.module';
import { UserComponent } from './user.component';
import { ProfileComponent } from './profile/profile.component';
import { OrderHistoryComponent } from './order-history/order-history.component';
import { MatIconModule } from '@angular/material/icon';
import { FlexLayoutModule } from '@angular/flex-layout';
import {MatListModule} from '@angular/material/list'
import { MatCardModule } from '@angular/material/card';
import { AddressComponent } from './address/address.component';
import { PhotosComponent } from './photos/photos.component';
import { ReviewsComponent } from './reviews/reviews.component';
import { MatButtonModule } from '@angular/material/button';
@NgModule({
  declarations: [
    UserComponent,
    ProfileComponent,
    OrderHistoryComponent,
    AddressComponent,
    PhotosComponent,
    ReviewsComponent,
  ],
  imports: [
    CommonModule,
    UserRoutingModule,
    MatIconModule,
    FlexLayoutModule,
    MatListModule,
    MatCardModule,
    MatButtonModule,
  ]
})
export class UserModule { }
