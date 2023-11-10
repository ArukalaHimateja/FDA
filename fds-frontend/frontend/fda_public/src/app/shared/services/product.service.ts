import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { BehaviorSubject, map } from 'rxjs';
import { FoodListItemType } from '../shared/interfaces/pagination.interface';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  foodList = new BehaviorSubject<FoodListItemType[]>([]);
  searchKeyword = new BehaviorSubject<String | null>(null);

  constructor(
    private _apiService: ApiService,
  ) {}

  searchProduct(data: any) {
    return this._apiService.post(data,`product/search`)
  }
  getAllCategories(data: any){
    return this._apiService.post(data, `category/pagination/filter`);
  }

  getProductByCategoryId(data: any){
    return this._apiService.post(data, 'product/pagination/filter');
  }
}
