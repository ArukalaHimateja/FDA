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
import { FilterDialogBoxComponent } from './filter-dialog-box/filter-dialog-box.component';
import { MatListModule } from '@angular/material/list';
import {MatSidenavModule} from '@angular/material/sidenav'
import { MatButtonModule } from '@angular/material/button';
import {MatSliderModule} from '@angular/material/slider';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatDialogModule } from '@angular/material/dialog';
import { CartComponent } from './cart/cart.component';
import {MatTableModule} from '@angular/material/table';
import { MatInputModule } from '@angular/material/input';
@NgModule({
  declarations: [
    PublicComponent,
    HomeComponent,
    PopularFoodComponent,
    FoodComponent,
    FilterDialogBoxComponent,
    CartComponent,
  ],
  imports: [
    CommonModule,
    PublicRoutingModule,
    LayoutModule,
    MatToolbarModule,
    MatIconModule,
    MatCardModule,
    FlexLayoutModule,
    MatListModule,
    MatSidenavModule,
    MatButtonModule,
    MatSliderModule,
    MatFormFieldModule,
    FormsModule,
    ReactiveFormsModule,
    MatDialogModule,  
    MatTableModule,
    MatInputModule
  ]
})
export class PublicModule { }
