import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { BehaviorSubject } from 'rxjs';
import { UtilityService } from './utility.service';
import { LocationStrategy } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  cartProductList = new BehaviorSubject<any[]>([]);
  displayedColumns = ['productName', 'restaurantName', 'quantity', 'price', 'action']

  constructor(
    private _apiService: ApiService,
    private _utilityService: UtilityService,
  ) {
    const cartData = localStorage.getItem("cart");
    const cartArray = cartData ? JSON.parse(cartData) : [];
    this.cartProductList.next(cartArray);
  }

  addToCart(element: any) {
    let productList = this.cartProductList.getValue();
    let currentProduct = productList.find(item => item.id === element.id);
    if (!currentProduct) {
      productList.push(element);
      this.cartProductList.next(productList);
    } else {
      currentProduct.quantity += 1;
    }
    localStorage.setItem("cart", JSON.stringify(productList));
    this.cartProductList.next(productList);
  }


  updateQuantity(id: any, num: number) {
    let productList = this.cartProductList.getValue();
    let currentProduct = productList.find(item => item.id === id);
    currentProduct.quantity = currentProduct.quantity + num;
    localStorage.setItem("cart", JSON.stringify(productList));
    this.cartProductList.next(productList);
  }

  checkout(data: any) {
    return this._apiService.post(data, 'order/add');
  }

  addPromocode(data: any) {
    return this._apiService.post('', `promoCode/isValid?promoCode=${data}`);
  }

  payment(payment: any) {
    return this._apiService.post(payment, 'stripe/payment');
  }

  orderPayStatusConfirmedByOrderId(id: any) {
    return this._apiService.get(`order/pay/status/confirmed/${id}`)
  }

}
