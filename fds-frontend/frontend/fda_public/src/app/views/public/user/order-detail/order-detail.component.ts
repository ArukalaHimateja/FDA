import { Component } from '@angular/core';
import { UserService } from '../User.service';
import { ActivatedRoute, Router } from '@angular/router';
import { OrderDetailType, OrderProductDetailType } from 'src/app/shared/shared/interfaces/pagination.interface';
import { CartService } from 'src/app/shared/services/cart.service';
import { UtilityService } from 'src/app/shared/services/utility.service';

@Component({
  selector: 'app-order-detail',
  templateUrl: './order-detail.component.html',
  styleUrls: ['./order-detail.component.scss']
})
export class OrderDetailComponent {
  orderId!: number;
  orderProductDetailList!: OrderProductDetailType[];
  orderDetail!: OrderDetailType;
  constructor(
    private _userService: UserService,
    private _activatedRoute: ActivatedRoute,
    private _router: Router,
    private _cartService: CartService,
    private _utilityService: UtilityService,
  ) {
    this.orderId = _activatedRoute.snapshot.params['orderId'];
    this._userService.getSubOrderDetailByOrderId(this.orderId).then((response: any) => {
      this.orderProductDetailList = response.body.data;
    })
    this._userService.getOrderDetailByOrderId(this.orderId).then((response: any) => {
      this.orderDetail = response.body.data;
    })
    if (_activatedRoute.snapshot.params['type'] === 'success') {
      this._cartService.orderPayStatusConfirmedByOrderId(this.orderId).then((response: any) => {
        if(response.body.status === 'OK'){
          this._utilityService.success(response.body.message);
        }else{
          this._utilityService.error(response.body.message);
        }
      });
    }
  }
}

