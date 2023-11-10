import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MenuRoutingModule } from './menu-routing.module';
import { MenuComponent } from './menu.component';
import { AddComponent } from './add/add.component';
import { ViewComponent } from './view/view.component';
import { ListComponent } from './list/list.component';
import { MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginatorModule } from '@angular/material/paginator';
import { SharedComponentsModule } from 'src/app/shared/shared-components/shared-components.module';
import { MatButtonModule } from '@angular/material/button';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { FormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    MenuComponent,
    AddComponent,
    ViewComponent,
    ListComponent
  ],
  imports: [
    CommonModule,
    MenuRoutingModule,
    MatToolbarModule,
    MatTableModule,
    MatIconModule,
    MatPaginatorModule,
    SharedComponentsModule,
    MatButtonModule,
    MatSlideToggleModule,
    FormsModule
  ]
})
export class MenuModule { }
