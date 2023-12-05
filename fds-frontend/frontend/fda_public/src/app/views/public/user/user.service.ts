import { Injectable } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ApiService } from 'src/app/shared/services/api.service';
import { UtilityService } from 'src/app/shared/services/utility.service';
import { MustMatch } from 'src/app/shared/validators/must-match';
import { SessionService } from '../session/session.service';
import { config } from 'src/config';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  sessionUser: any;

  constructor(
    private _apiService: ApiService,
    private _formBuilder: FormBuilder,
    private _utilityService: UtilityService,
    private _sessionService: SessionService,
  ) {
    this.sessionUser = this._utilityService.getAuthUser();
  }


  addUser(data: any, type: any) {
    return this._apiService.post(data, type === 'add' ? 'user/add' : 'user/update');
  }

  addRestaurant(data: any) {
    return this._apiService.post(data, 'restaurant/add/request');
  }
  allFoodList() {
    return this._apiService.get('get/all/product/list')
  }
  getProductById(id: string) {
    return this._apiService.get(`get/product/${id}`);
  }
  getImage(name: string) {
    return `${config.apiUrl}file/${name}`;
  }
  getAllCategoryByRestaurantId(id: number) {
    return this._apiService.get(`category/list/by/restaurant/${id}`);
  }
  getProductByCategoryId(data: any) {
    return this._apiService.post(data, `product/pagination/filter`);
  }

  /**
    * Change Password Form
    */
  createChangePasswordForm(): FormGroup {
    return this._formBuilder.group({
      oldPassword: [null, [Validators.required]],
      password: [null, [Validators.required, Validators.pattern("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*\\W).{8,}$")]],
      confirmPassword: [null, [Validators.required]]
    }, {
      validator: MustMatch('password', 'confirmPassword')
    })
  }

  changePassword(formData: any) {
    return this._apiService.post(formData, 'changePassword').then((response: any) => {
      console.log(response, response.body.message, response.body.data);
      if (response.body.status === 'OK') {
        this._utilityService.successMessage(response.body.message, response.body.status);
      } else {
        this._utilityService.errorMessage(response.body.message, response.body.status);
      }
    })
  }

  // update profile
  createUpdateProfileForm(user: any): FormGroup {
    return this._formBuilder.group({
      fullName: [user ? user.fullName : null, [Validators.required]],
      email: [user ? user.email : null, [Validators.required, Validators.email]],
      mobileNumber: [user ? user.mobileNumber : null, [Validators.required, Validators.pattern("^[0-9]{10,}$")]]
    })
  }

  getUserDtl() {
    console.log(`user/${this.sessionUser.id}`)
    return this._apiService.get(`user/${this.sessionUser.id}`);
  }
  updateProfile(data: any, id: any) {
    return this._apiService.post(data, `customer/update/${id}`).then((response) => {
      if (response.body.status === 'OK') {
        this._utilityService.successMessage(response.body.message, response.body.status);
        this._sessionService.setLocalStorage(response.body.data);
      } else {
        this._utilityService.errorMessage(response.body.message, response.body.status);
      }
    })
  }

  // update Address
  createAddressFrom(user: any): FormGroup {
    return this._formBuilder.group({
      address: [user ? user.address : null, [Validators.required]]
    })
  }

  updateAddress(data: any) {
    return this._apiService.post(data, `user/address/update`).then((response) => {
      if (response.body.status === 'OK') {
        this._utilityService.successMessage(response.body.message, response.body.status);
      } else {
        this._utilityService.errorMessage(response.body.message, response.body.status);
      }
    })
  }


  /**
   * Get all order history list by user id
   * 
   * @param userId 
   * @returns 
   */
  getAllOrderHistoryListByUserId() {
    return this._apiService.get(`get/all/order/user/${this.sessionUser.id}`);
  }
  getOrderDetailByOrderId(orderId: number) {
    return this._apiService.get(`get/order/${orderId}`);
  }
  getSubOrderDetailByOrderId(orderId: number) {
    return this._apiService.get(`get/sub/order/${orderId}`)
  }

}
