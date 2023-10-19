import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PublicRoutingModule } from './public-routing.module';
import { PublicComponent } from './public.component';
import { HomeComponent } from './home/home.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { FlexLayoutModule } from '@angular/flex-layout';
import { LayoutModule } from 'src/app/shared/layout/layout.module';
import { PopularFoodComponent } from './popular-food/popular-food.component';
import { FoodComponent } from './food/food.component';

@NgModule({
  declarations: [
    PublicComponent,
    HomeComponent,
    PopularFoodComponent,
    FoodComponent,
  ],
  imports: [
    CommonModule,
    PublicRoutingModule,
    LayoutModule,
    MatToolbarModule,
    MatIconModule,
    MatCardModule,
    FlexLayoutModule
  ]
})
export class PublicModule { }
