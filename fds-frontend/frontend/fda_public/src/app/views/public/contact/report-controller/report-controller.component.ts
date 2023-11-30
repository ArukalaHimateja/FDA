import { Component } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ContactService } from '../contact.service';

@Component({
  selector: 'app-report-controller',
  templateUrl: './report-controller.component.html',
  styleUrls: ['./report-controller.component.scss']
})
export class ReportControllerComponent {
  reportForm: FormGroup;
  queryList = [
    'I need help with my Zomato online order.',
    'I found incorrect/outdated information on a page.',
    'There is a photo/review that is bothering me and I would like to report it.',
    'The website/app are not working the way they should.',
    'I would like to give feedback/suggestions.',
    'I need some help with my blog.'
  ]
  constructor(
    private _contactService: ContactService,
  ){
    this.reportForm = _contactService.createReportForm();
  }

  onSubmit(){
    this._contactService.submitReport(this.reportForm.value);    
  }

}
