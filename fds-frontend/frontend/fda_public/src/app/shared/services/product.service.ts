import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { BehaviorSubject, map } from 'rxjs';
import { FoodListItemType } from '../shared/interfaces/pagination.interface';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  foodVarietyList: string[] = [];
  constructor(
    private _apiUrl: ApiService,
  ) {
    this.handleFoodDataChange(this.searchedFoodList)
   }

  handleFoodDataChange(data: BehaviorSubject<FoodListItemType[]>): void {
    this.foodData$.pipe(
      map((data: FoodListItemType[]) => data.map(item => item.foodName))
    ).subscribe((foodVarieties: string[]) => {
      this.foodVarietyList = foodVarieties;
      console.log('hi', this.foodVarietyList);
    });
  }


  searchProduct(keyword: any) {
    return this._apiUrl.get(`menu/search/${keyword}`)
  }


  // share search data with popular-food
  private searchedFoodList = new BehaviorSubject<FoodListItemType[]>([]);
  foodData$ = this.searchedFoodList.asObservable();

  sendData(data: FoodListItemType[]) {
    this.searchedFoodList.next(data);
    this.handleFoodDataChange(this.searchedFoodList);
  }
}
