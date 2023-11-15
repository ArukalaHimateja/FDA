import { Component } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ContactService } from '../contact.service';

@Component({
  selector: 'app-feedback-controller',
  templateUrl: './feedback-controller.component.html',
  styleUrls: ['./feedback-controller.component.scss']
})
export class FeedbackControllerComponent {
  ContactusForm: FormGroup;
  constructor(
    private _contactUsService: ContactService,
  ) {
    this.ContactusForm = this._contactUsService.createForm();
  }

  onSubmit() {
    console.log(this.ContactusForm.value);

    this._contactUsService.submitFeedback(this.ContactusForm.value);
    this.ContactusForm.reset();
  }
}
