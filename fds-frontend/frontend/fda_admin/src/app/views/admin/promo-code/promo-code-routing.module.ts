import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListComponent } from './list/list.component';
import { AddComponent } from './add/add.component';
import { PromoCodeService } from './promo-code.service';

const routes: Routes = [
  { path: '', component: ListComponent },
  { path: 'list', component: ListComponent },
  { path: 'add', component: AddComponent, resolve: { data: PromoCodeService } },
  { path: 'edit/:id', component: AddComponent, resolve: { data: PromoCodeService } }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PromoCodeRoutingModule { }
