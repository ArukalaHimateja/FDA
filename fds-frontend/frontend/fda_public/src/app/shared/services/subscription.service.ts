// import { Injectable } from '@angular/core';
// import { FormBuilder, FormGroup, Validators } from '@angular/forms';
// import { ApiService } from 'app/shared/services/api.service';
// import { UtilityService } from '../../../shared/services/utility.service';
// import { BehaviorSubject } from 'rxjs';

// @Injectable({
//   providedIn: 'root'
// })
// export class SubscriptionService {

//   isSubscriptionSuccess = new BehaviorSubject<Boolean>(false);
//   activeSubscription = new BehaviorSubject<any>(null);

//   displayedColumns: any[] = ['sno', 'title', 'price', 'planTime', 'lookupKey', 'createdAt', 'status'];
//   constructor(
//     private _formBuilder: FormBuilder,
//     private _apiService: ApiService,
//     private _utilityService: UtilityService
//   ) { }

//   createForm(element: any): FormGroup {
//     return this._formBuilder.group({
//       title: [element ? element.title : null, [Validators.required]],
//       price: [element ? element.price : null, [Validators.required]],
//       planTime: [element ? element.planTime : null, [Validators.required]],
//       lookupKey: [element ? element.lookupKey : null, [Validators.required]],
//     })
//   }

//   /**
//  * Add or save data
//  * 
//  * @param data 
//  * @param type 
//  */
//   addOrUpdateData(data: any, type: any) {
//     return this._apiService.post(data, type === 'add' ? 'subscription/plan/add' : 'user/update');
//   }

//   getAll() {
//     return this._apiService.get(`subscription/get/all`)
//   }

//   /**
//  * Change data status
//  */
//   changeStatus(data: any) {
//     return this._apiService.get(`subscription/plan/activate/and/deactivate?istrue=${data.status}&productId=${data.id}`);
//   }

//   payment(payment: any) {
//     return this._apiService.post(payment, 'subscription/payment');
//   }

//   getSubscriptionByStripePriceId(stripePriceId: any): any {
//     return this._apiService.get(`subscription/get/priceId/${stripePriceId}`);
//   }

//   getSubscriptionImage(subscription: any) {
//     if (subscription && subscription.planTime === 0) {
//       return 'assets/images/subscription/silver.png';
//     } else if (subscription && subscription.planTime === 1) {
//       return 'assets/images/subscription/gold.png';
//     } else if (subscription && subscription.planTime === 2) {
//       return 'assets/images/subscription/premium.png';
//     } else {
//       return 'assets/images/subscription/silver.png';
//     }
//   }
// }