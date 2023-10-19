import { Injectable } from '@angular/core';
import { ApiService } from 'src/app/shared/services/api.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(
    private _apiService: ApiService
  ) { }


  addUser(data: any, type: any) {
    return this._apiService.post(data, type === 'add' ? 'user/add' : 'user/update');
  }
  allFoodList(){
    return this._apiService.get('get/all/menu/list')
  }
}
