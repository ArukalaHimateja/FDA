import { Injectable } from '@angular/core';
import { ApiService } from 'src/app/shared/services/api.service';
import { AuthService } from '../../session/auth.service';
import { ConstantService } from 'src/app/shared/services/constant.service';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  displayedColumns: string[] = ["id", "customerId", "promoCode", "payStatus", "deliveryAddress", "subTotalPrice", "createdAt", 'view', 'action']




  sessionUser: any;
  REQUEST_STATUS: any[] = [
    { key: 0, value: 'Pending', color: 'primary' },
    { key: 1, value: 'Accepted', color: 'warn' },
    { key: 2, value: 'Cancel', color: 'warn' },
    { key: 3, value: 'Delivered', color: 'acent' }
  ]

  constructor(
    private _apiService: ApiService,
    private _authService: AuthService,
    private _constantService: ConstantService,
  ) {
    this.sessionUser = this._authService.getAuthUser();
  }



  getRequestColor(key: any) {
    if (key === 0) {
      return 'blue';
    } else if (key === 1) {
      return 'green';
    } else if (key === 2) {
      return 'red';
    } else {
      return 'blue';
    }
  }

  /**
   * Get Color
   * 
   * @param key 
   */
  getStatus(key: any) {
    let element = this.REQUEST_STATUS.find(item => item.key === key);
    if (element) {
      return element.value;
    } else {
      return 'Pending';
    }
  }


  /**
   * Get Color
   * 
   * @param key 
   */
  getColor(key: any) {
    let element = this._constantService.COLORS.find(item => item.key === key);
    if (element) {
      return element.value;
    } else {
      return 'primary';
    }
  }


  getAllOrderWithPaginationByRestaurantId() {
    return this._apiService.get(`get/all/order/restaurant/${this.sessionUser.restaurantId}`);
  }

  getOrderListByFilterWithPagination(data: any) {
    return this._apiService.post(data, `combine/order/pagination/filter`);
  }
  getUserDetailById(customerId: any) {
    return this._apiService.get(`get/user/all/details/${customerId}`);
  }

  orderAcceptedStatusByOrderId(orderId: any) {
    return this._apiService.get(`order/accepted/status/${orderId}`);
  }

  orderCancelStatusByOrderId(orderId: any) {
    return this._apiService.get(`order/cancel/status/${orderId}`);
  }

  orderDeliveredStatusByOrderId(orderId: any) {
    return this._apiService.get(`order/delivered/status/${orderId}`);
  }

  getOrderDetailByOrderId(orderId: number) {
    return this._apiService.get(`get/order/${orderId}`);
  }
  getSubOrderDetailByOrderId(orderId: number) {
    return this._apiService.get(`get/sub/order/${orderId}`)
  }
  /**
 * Get Restaurant data by id
 * 
 * @param id 
 */
  getRestaurantDtlByRestaurantId(RestaurantId: any) {
    return this._apiService.get(`get/restaurant/${RestaurantId}`);
  }
}
