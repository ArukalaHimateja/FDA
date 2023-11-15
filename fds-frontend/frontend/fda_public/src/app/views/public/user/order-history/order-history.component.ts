import { Component } from '@angular/core';
import { OrderHistoryListType } from 'src/app/shared/shared/interfaces/pagination.interface';
import { UserService } from '../User.service';

@Component({
  selector: 'app-order-history',
  templateUrl: './order-history.component.html',
  styleUrls: ['./order-history.component.scss']
})
export class OrderHistoryComponent {
  orderHistoryList: OrderHistoryListType[]= [];
  constructor(
    private _userService: UserService,
  ){
    this.getOrderHistoryList();
  }
  getOrderHistoryList(){
    this._userService.getAllOrderHistoryListByUserId().then((response: any)=> {
      this.orderHistoryList = response.body.data;
    })
  }
}
