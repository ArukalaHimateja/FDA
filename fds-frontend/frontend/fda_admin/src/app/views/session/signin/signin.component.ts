import { Component } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.scss']
})
export class SigninComponent {

  formGroup: FormGroup;

  constructor(
    private _authService: AuthService
  ) {
    this.formGroup = _authService.createAdminForm();
  }

  ngOnInit(): void {
  }

  submit() {
    const data = this.formGroup.getRawValue();
    this._authService.login(data);
  }
}
