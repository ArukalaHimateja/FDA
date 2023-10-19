import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormatDateTimePipe } from './date-time.pipe';

const pipes: any = [
  FormatDateTimePipe
]

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [pipes],
  exports: [pipes]
})
export class SharedPipesModule { }
