import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ContactComponent } from './contact.component';
import { FeedbackControllerComponent } from './feedback-controller/feedback-controller.component';
import { ReportControllerComponent } from './report-controller/report-controller.component';

const routes: Routes = [
  { path: '', component: ContactComponent },
  { path: 'feedback', component: FeedbackControllerComponent},
  { path: 'report', component: ReportControllerComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ContactRoutingModule { }
