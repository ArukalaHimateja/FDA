import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MenuComponent } from './menu.component';
import { AddComponent } from './add/add.component';

const routes: Routes = [
  { path: '', component: MenuComponent },
  {path: 'add', component: AddComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MenuRoutingModule { }
