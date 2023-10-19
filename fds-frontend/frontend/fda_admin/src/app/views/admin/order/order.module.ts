import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { OrderRoutingModule } from './order-routing.module';
import { OrderComponent } from './order.component';
import { ViewComponent } from './view/view.component';
import { TrackComponent } from './track/track.component';


@NgModule({
  declarations: [
    OrderComponent,
    ViewComponent,
    TrackComponent
  ],
  imports: [
    CommonModule,
    OrderRoutingModule
  ]
})
export class OrderModule { }
