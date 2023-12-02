import { Component, Inject } from '@angular/core';
import { OrderService } from '../order.service';
import { OrderProductDetailType, OrderDetailType } from 'src/app/shared/interfaces/pagination.interface';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-view',
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.scss']
})
export class ViewComponent {
  orderId!: number;
  orderProductDetailList!: OrderProductDetailType[];
  orderDetail!: OrderDetailType;
  
  constructor(
    private _orderService: OrderService,
    public dialogRef: MatDialogRef<ViewComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
  ) {
    this._orderService.getSubOrderDetailByOrderId(this.data.orderId).then((response: any) => {
      this.orderProductDetailList = response.body.data;
    })
    this._orderService.getOrderDetailByOrderId(this.data.orderId).then((response: any) => {
      this.orderDetail = response.body.data;
    })
  }
}
