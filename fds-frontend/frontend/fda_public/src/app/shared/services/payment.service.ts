import { Injectable } from '@angular/core';
import { ApiService } from '../../shared/services/api.service';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  constructor(
    private _apiUrl: ApiService
  ) { }

  getTipData(): any {
    return this._apiUrl.get('tip/data');
  }

  getGreyhoundData(data: any) {
    return this._apiUrl.post(data, `greyhound/search`)
  }

  userSubscription(data: any) {
    return this._apiUrl.post(data, `user/subscription/apply`)
  }
}