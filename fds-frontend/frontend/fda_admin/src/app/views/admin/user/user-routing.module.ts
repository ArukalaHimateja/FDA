import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListComponent } from './list/list.component';
import { AddComponent } from './add/add.component';
import { ViewComponent } from './view/view.component';
import { UserService } from './user.service';

const routes: Routes = [
  { path: '', component: ListComponent },
  { path: 'list', component: ListComponent },
  { path: 'add', component: AddComponent, resolve: { data: UserService } },
  { path: 'edit/:id', component: AddComponent, resolve: { data: UserService } },
  { path: 'view/:id', component: ViewComponent, resolve: { data: UserService } }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }
