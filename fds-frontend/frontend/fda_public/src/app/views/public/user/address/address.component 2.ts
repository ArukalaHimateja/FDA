import { Component } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { UserService } from '../User.service';

@Component({
  selector: 'app-address',
  templateUrl: './address.component.html',
  styleUrls: ['./address.component.scss']
})
export class AddressComponent {
  addressForm! : FormGroup;
  user: any;
  constructor(
    private _userService: UserService,
  ){
    this.addressForm = this._userService.createAddressFrom(null);
    _userService.getUserDtl().then((response) => {
      this.user = response.body.data;
      if(response.body.status === 'OK'){
        this.addressForm = this._userService.createAddressFrom(this.user);
      }
    })
  }
  updateAddress(){
    let data = this.addressForm.getRawValue();
    const formData = new FormData();
    formData.append('address', data.address);
    formData.append('userId', this.user.userId);
    console.log('form',data, formData)
    this._userService.updateAddress(formData);
  }
}
