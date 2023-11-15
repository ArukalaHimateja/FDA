import { Component } from '@angular/core';
import { CartService } from 'src/app/shared/services/cart.service';
import { ProductService } from 'src/app/shared/services/product.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent {
  productList: any[] = [];
  displayedColumns: any;
  totalCost: number = 0;
  constructor(
    private _cartService: CartService
  ) {
    this.displayedColumns = this._cartService.displayedColumns;
    this.productList = _cartService.cartProductList.getValue();
    if (localStorage.getItem("cart")) {
      this.productList = JSON.parse(localStorage.getItem("cart") + "");
    }
    this.totalCostCount();
  }

  totalCostCount(){
    this.totalCost = 0;
    if (this.productList.length !== 0) {
      this.productList.map((item) => {
        console.log(item);
        this.totalCost += item.price;
      });
    } else {
      this.totalCost = 0;
    }
  }

  removeItem(index: any){
    this.productList.splice(index,1);
    this.productList = Object.assign([],this.productList);
    localStorage.setItem("cart",JSON.stringify(this.productList));
    this.totalCostCount();
  }

  checkout(data: any){
    let json = {
      productId: data[0].id,
      productQuantity: data[0].quantity
    }
    this._cartService.checkout(json);
    localStorage.removeItem("cart");
    this.productList = Object.assign([],this._cartService.cartProductList.getValue())
    this.totalCostCount();
  }
}
