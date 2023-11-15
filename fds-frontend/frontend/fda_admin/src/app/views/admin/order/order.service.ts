import { Injectable } from '@angular/core';
import { ApiService } from 'src/app/shared/services/api.service';
import { AuthService } from '../../session/auth.service';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  displayedColumns: string[] = ['id','createdAt','customerId','productName','productQuantity','totalPayPrice','payStatus','deliveryAddress', 'action']

  sessionUser: any;

  constructor(
    private _apiService: ApiService,
    private _authService: AuthService,
  ) {
    this.sessionUser = this._authService.getAuthUser();
  }

  getAllOrderWithPaginationByRestaurantId() {
    return this._apiService.get(`get/all/order/restaurant/${this.sessionUser.restaurantId}`);
  }

  getUserDetailById(customerId: any){
    return this._apiService.get(`user/${customerId}`);
  }
}
