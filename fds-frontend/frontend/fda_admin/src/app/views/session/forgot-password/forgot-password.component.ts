import { Component } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.scss']
})
export class ForgotPasswordComponent {
  forgotPasswordForm: FormGroup;
  constructor(
    private _authService: AuthService,
  ) {
    this.forgotPasswordForm = this._authService.createFrogotPasswordFrom();
  }

  forgotPassword() {
    const formData = new FormData();
    formData.append('email', this.forgotPasswordForm.controls['username'].value);
    this._authService.forgotPassword(formData);
  }
}
