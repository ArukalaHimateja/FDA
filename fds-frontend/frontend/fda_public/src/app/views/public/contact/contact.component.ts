import { Component } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ContactService } from './contact.service';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.scss']
})
export class ContactComponent {
  ContactusForm: FormGroup;
  queryList = [
    'I need help with my Zomato online order.',
    'I found incorrect/outdated information on a page.',
    'There is a photo/review that is bothering me and I would like to report it.',
    'The website/app are not working the way they should.',
    'I would like to give feedback/suggestions.',
    'I need some help with my blog.'
  ]
  constructor(
    private _contactUsService: ContactService,
  ) {
    this.ContactusForm = this._contactUsService.createForm();
  }

  onSubmit() {
    console.log(this.ContactusForm.value);
    this.ContactusForm.reset();
  }
}
