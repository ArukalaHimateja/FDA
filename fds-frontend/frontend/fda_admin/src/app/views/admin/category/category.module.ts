import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MenuRoutingModule } from './category-routing.module';
import { CategoryComponent } from './category.component';
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
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import {FlexLayoutModule} from '@angular/flex-layout'


@NgModule({
  declarations: [
    CategoryComponent,
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
    FormsModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatInputModule,
    FlexLayoutModule
  ]
})
export class CategoryModule { }
