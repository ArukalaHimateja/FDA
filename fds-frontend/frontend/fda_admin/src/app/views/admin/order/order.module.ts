import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { OrderRoutingModule } from './order-routing.module';
import { OrderComponent } from './order.component';
import { ViewComponent } from './view/view.component';
import { TrackComponent } from './track/track.component';
import { ListComponent } from './list/list.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginatorModule } from '@angular/material/paginator';
import { SharedComponentsModule } from 'src/app/shared/shared-components/shared-components.module';
import { MatTableModule } from '@angular/material/table';
import { MatMenuModule } from '@angular/material/menu';
import { MatButtonModule } from '@angular/material/button';


@NgModule({
  declarations: [
    OrderComponent,
    ViewComponent,
    TrackComponent,
    ListComponent
  ],
  imports: [
    CommonModule,
    OrderRoutingModule,
    MatToolbarModule,
    MatIconModule,
    MatPaginatorModule,
    SharedComponentsModule,
    MatTableModule,
    MatButtonModule
  ]
})
export class OrderModule { }
