import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FoodDetailRoutingModule } from './food-detail-routing.module';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatSelectModule } from '@angular/material/select';
import { FoodDetailComponent } from './food-detail.component';
import { MatListModule } from '@angular/material/list';
import { FoodByCategoryComponent } from './food-by-category/food-by-category.component';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';


@NgModule({
  declarations: [
    FoodDetailComponent,
    FoodByCategoryComponent
  ],
  imports: [
    CommonModule,
    FoodDetailRoutingModule,
    FlexLayoutModule,
    MatSelectModule,
    MatListModule,
    MatCardModule,
    MatIconModule,
    MatButtonModule,
  ]
})
export class FoodDetailModule { }
