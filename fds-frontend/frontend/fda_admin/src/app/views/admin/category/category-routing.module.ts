import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
// import { AddComponent } from './add/add.component';
import { CategoryComponent } from './category.component';
// import { ViewComponent } from './view/view.component';

const routes: Routes = [
  { path: '', component: CategoryComponent },
  // { path: 'view', component: ViewComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MenuRoutingModule { }
