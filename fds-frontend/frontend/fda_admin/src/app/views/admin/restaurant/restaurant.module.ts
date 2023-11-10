import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RestaurantRoutingModule } from './restaurant-routing.module';
import { RestaurantComponent } from './restaurant.component';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatToolbarModule } from '@angular/material/toolbar';
import { SharedComponentsModule } from 'src/app/shared/shared-components/shared-components.module';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';


@NgModule({
  declarations: [
    RestaurantComponent
  ],
  imports: [
    CommonModule,
    RestaurantRoutingModule,
    MatInputModule,
    MatIconModule,
    MatMenuModule,
    MatSlideToggleModule,
    MatPaginatorModule,
    MatToolbarModule,
    SharedComponentsModule,
    MatTableModule,
    MatButtonModule,
  ]
})
export class RestaurantModule { }
