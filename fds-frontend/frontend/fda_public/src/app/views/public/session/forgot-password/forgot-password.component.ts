import { Component } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { SessionService } from '../session.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.scss']
})
export class ForgotPasswordComponent {
  forgotPasswordForm: FormGroup;
  constructor(
    private _sessionService: SessionService,
  ) {
    this.forgotPasswordForm = this._sessionService.createFrogotPasswordFrom();
  }

  forgotPassword() {
    const formData = new FormData();
    formData.append('email', this.forgotPasswordForm.controls['username'].value);
    this._sessionService.forgotPassword(formData);
  }
}
