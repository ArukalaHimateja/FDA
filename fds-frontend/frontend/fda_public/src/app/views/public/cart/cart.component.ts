import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router, ActivatedRoute } from '@angular/router';
import { CartService } from 'src/app/shared/services/cart.service';
import { ProductService } from 'src/app/shared/services/product.service';
import { UserService } from '../user/User.service';
import { PaymentService } from 'src/app/shared/services/payment.service';
// import { SubscriptionService } from 'src/app/shared/services/subscription.service';
import { UtilityService } from 'src/app/shared/services/utility.service';
import { loadStripe } from '@stripe/stripe-js';
import { config } from 'src/config';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent {
  productList: any[] = [];
  displayedColumns: any;
  totalAmountWithoutPromocode: number = 0;
  promocode: FormControl = new FormControl('', [Validators.required]);
  totalAmountWithPromocode: number = 0;
  isPromocodeApplyed: boolean = false;
  stripePromise = loadStripe(config.stripePublicKey);
  constructor(
    private _cartService: CartService,
    private _snackbar: MatSnackBar,
    private _utilityService: UtilityService,
  ) {
    this.displayedColumns = this._cartService.displayedColumns;
    _cartService.cartProductList.subscribe((response: any) => {
      this.productList = response;
      this.totalCostCount();
    })
    this.totalAmountWithPromocode = 0;
    this.isPromocodeApplyed = false;
  }

  totalCostCount() {
    this.totalAmountWithoutPromocode = 0;
    if (this.productList.length !== 0) {
      this.productList.map((item) => {
        console.log(item);
        this.totalAmountWithoutPromocode += item.price * item.quantity;
      });
    } else {
      this.totalAmountWithoutPromocode = 0;
    }
  }

  removeItem(index: any) {
    this.productList.splice(index, 1);
    this.productList = Object.assign([], this.productList);
    localStorage.setItem("cart", JSON.stringify(this.productList));
    this.totalCostCount();
  }

  applyPromocode() {
    console.log(this.promocode.value);
    this._cartService.addPromocode(this.promocode.value).then((response: any) => {
      if (response.body.status === 'OK') {
        this.totalAmountWithPromocode = this.totalAmountWithoutPromocode - response.body.data.value;
        this.isPromocodeApplyed = true;
        this._snackbar.open(response.body.message, 'OK', { duration: 5000, verticalPosition: 'top' });
      } else {
        this._snackbar.open(response.body.message, 'OK', { duration: 5000, verticalPosition: 'top' });
      }
    })
  }

  updateQuantity(id: any, num: number) {
    this._cartService.updateQuantity(id, num);
  }



  checkout(data: any) {
    let orderlist: {
      productId: number,
      productQuantity: number
    }[] = []; // Initialize an empty array

    data.map((item: any) => {
      orderlist.push({
        productId: item.id,
        productQuantity: item.quantity
      });
    });

    if (orderlist.length !== 0) {
      let json = {
        orderListDto: orderlist,
        payPriceWithPromoCode: this.totalAmountWithPromocode,
        payPriceWithoutPromoCode: this.totalAmountWithoutPromocode,
        promoCode: this.promocode.value,
        subTotalPrice: this.totalAmountWithPromocode === 0 ? this.totalAmountWithoutPromocode : this.totalAmountWithPromocode
      }

      this._cartService.checkout(json).then((response: any) => {
        if (response && response.body.status === 'OK') {
          // const stripe = await this.stripePromise;
          console.log("response", response.body.data);
          const payJson = {
            cancelUrl: 'http://localhost:4200/',
            orderId: response.body.data.id,
            successUrl: `http://localhost:4200/user/order/detail/${response.body.data.orderId}/success`
          }
          this._cartService.payment(payJson).then((payRresponse: any) => {
            if (payRresponse.body.status === 'OK') {
              console.log('payresponse', payRresponse.body.data);
              window.open(payRresponse.body.data);
            } else {
              // this._utilityService.errorMessage(response.body.message, response.statusText);
            }
            this._utilityService.successMessage(response.body.message, response.statusText);
          });
        } else {
          this._utilityService.errorMessage(response.body.message, response.statusText);
        };
      });
      localStorage.removeItem("cart");
      this.productList = Object.assign([], this._cartService.cartProductList.getValue())
      this.totalCostCount();
    }
  }

  // subscriptionList: any[] = [];
  // user: any;
  // subscriptionId: any;
  // isSuccess: boolean = false;
  // isFailure: boolean = false;
  // type: any;
  // subscription: any;
  // sessionUser: any;

  ngOnInit(): void {
  }

  // subscribe(element: any) {
  // }

  async pay(element: any): Promise<void> {
    // here we create a payment object
    const payment = {
      // name: this.user.name,
      name: 'Hi',
      currency: 'GBP',
      // amount on cents *10 => to be on dollar
      amount: element.subTotalPrice,
      quantity: '1',
      cancelUrl: 'http://localhost:4200/cancel',
      successUrl: 'http://localhost:4200/success',
      priceid: element.stripePriceId,
      // stripeUserId: this.sessionUser.stripeUserId,
    };

    // // const payment = {
    // //   cancelUrl: config.domainUrl + "subscription/payment/cancel",
    // //   name: this.user.name,
    // //   priceid: element.stripePriceId,
    // //   stripeUserId: this.user.stripeUserId,
    // //   successUrl: `${config.domainUrl}subscription/payment/success/${this.user.id}/${this.user.stripeUserId}/${element.stripePriceId}`
    // // }

    // const stripe: any = await this.stripePromise;

    // // this is a normal http calls for a backend api

    // this._cartService.payment(payment).then((response: any) => {
    //   stripe.redirectToCheckout({
    //     sessionId: response.body.data,
    //   });
    // })
  }

}