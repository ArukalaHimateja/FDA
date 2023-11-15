import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UtilityService } from 'src/app/shared/services/utility.service';
import { UserService } from '../../user/User.service';
import { SessionService } from '../session.service';

@Component({
  selector: 'app-become-apartner',
  templateUrl: './become-apartner.component.html',
  styleUrls: ['./become-apartner.component.scss']
})
export class BecomeAPartnerComponent {
  restaurantRegisterForm!: FormGroup;
  constructor(
    private _userService: UserService,
    private formBuilder: FormBuilder,
    private _utilityService: UtilityService,
    private _sessionService: SessionService,
    private _router: Router
  ) {
    this.restaurantRegisterForm = this._sessionService.createRestaurantForm();
  }

  onSubmit() {
    const signupData = this.restaurantRegisterForm.value;
    this._userService.addRestaurant(signupData).then((response: any) => {
      if (response && response.body.status === 'OK') {
        this._utilityService.successMessage(response.body.message, response.body.status);
        this._router.navigateByUrl(`/session/signin`);
      } else {
        this._utilityService.successMessage(response.body.message, response.body.status);
      }
    }, (error: any) => {

    })
  }
}
