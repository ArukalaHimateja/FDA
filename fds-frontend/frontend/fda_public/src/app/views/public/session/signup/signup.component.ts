import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../../user/user.service';
import { UtilityService } from 'src/app/shared/services/utility.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent {
  signupForm: FormGroup;
  constructor(
    private _userService: UserService,
    private formBuilder: FormBuilder,
    private _utilityService: UtilityService,
    private _router: Router
  ) {
    this.signupForm = this.formBuilder.group({
      fullName: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      mobileNumber: ['', [Validators.required]],
      address: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      // conform_password: ['', [Validators.required]],
      role: 0,
    })
  }

  onSubmit() {
    const signupData = this.signupForm.value;
    this._userService.addUser(signupData, 'add').then((response: any) => {
      if (response && response.body.status === 'OK') {
        this._utilityService.successMessage(response.body.message, response.body.status);
        this._router.navigateByUrl(`/login`);
      } else {
        this._utilityService.successMessage(response.body.message, response.body.status);
      }
    }, (error: any) => {

    })
  }
}
