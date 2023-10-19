import { Component, OnInit } from '@angular/core';
import { FoodListItemType } from 'src/app/shared/shared/interfaces/pagination.interface';
import { UserService } from '../user/user.service';
import { ProductService } from 'src/app/shared/services/product.service';

@Component({
  selector: 'app-popular-food',
  templateUrl: './popular-food.component.html',
  styleUrls: ['./popular-food.component.scss']
})
export class PopularFoodComponent implements OnInit {
  constructor(
    private _userService: UserService,
    private _productService: ProductService,
  ) {

  }
  image = "assets/images/sweets.jpg"
  offers = "45% off"
  rating = '4.3'
  timing = '34 min'
  FoodList: FoodListItemType[] = []

  ngOnInit() {
    this._productService.foodData$.subscribe((data: any) => {
      console.log('data', data.length)
      if (data.length !== 0) {
        this.FoodList = data;
        console.log('Data is available:', this.FoodList);
      } else {
        // Handle the case where data is null
        this._userService.allFoodList().then((response: any) => {
          this.FoodList = response.body.data;
          this._productService.sendData(this.FoodList);
        })
        console.log('Data is null');
      }
    });
  }

}
