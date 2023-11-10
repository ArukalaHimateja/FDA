import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FoodDetailComponent } from './food-detail.component';
import { FoodByCategoryComponent } from './food-by-category/food-by-category.component';

const routes: Routes = [
  { path: 'foodByCategory/:categoryId', component: FoodByCategoryComponent}  
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FoodDetailRoutingModule { }
