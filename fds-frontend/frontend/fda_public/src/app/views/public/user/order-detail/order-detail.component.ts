import { Component } from '@angular/core';
import { UserService } from '../User.service';
import { ActivatedRoute } from '@angular/router';
import { OrderDetailType } from 'src/app/shared/shared/interfaces/pagination.interface';

@Component({
  selector: 'app-order-detail',
  templateUrl: './order-detail.component.html',
  styleUrls: ['./order-detail.component.scss']
})
export class OrderDetailComponent {
  orderId!: number;
  orderDetail!: OrderDetailType;
  constructor(
    private _userService: UserService,
    private _activatedRoute: ActivatedRoute,
  ){
    this.orderId = _activatedRoute.snapshot.params['orderId'];
    this._userService.getOrderDetailByOrderId(this.orderId).then((response: any)=> {
      this.orderDetail = response.body.data;
    })
  }
}
