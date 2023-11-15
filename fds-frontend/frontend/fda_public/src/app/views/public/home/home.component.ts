import { Component } from '@angular/core';
import { ProductService } from 'src/app/shared/services/product.service';
import { UserService } from '../user/User.service';
import { Pagination, FoodCategoryType } from 'src/app/shared/shared/interfaces/pagination.interface';
import { UtilityService } from 'src/app/shared/services/utility.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {

  foodCategories!: FoodCategoryType[]; 
  // [
  //   {
  //     name: 'thali',
  //     path: 'thali.png'
  //   },
  //   {
  //     name: 'pizza',
  //     path: 'pizza.png'
  //   },
  //   {
  //     name: 'burger',
  //     path: 'burger.png'
  //   },
  //   {
  //     name: 'cake',
  //     path: 'cake.png'
  //   },
  //   {
  //     name: 'paneer',
  //     path: 'paneer.png'
  //   },
  //   {
  //     name: 'north indian',
  //     path: 'north_indian_thali.png'
  //   },
  //   {
  //     name: '1 cake',
  //     path: 'cake.png'
  //   },
  //   {
  //     name: '1 thali',
  //     path: 'thali.png'
  //   },
  //   {
  //     name: '1 burger',
  //     path: 'burger.png'
  //   },
  // ]
  slidePosition = 0;
  FoodList!: any;
  image = "assets/images/sweets.jpg"
  rating = '4.3'
  timing = '34 min'
  pagination!: Pagination;
  constructor(
    private _productService: ProductService,
    public _userService: UserService,
    private _utilityService: UtilityService,
  ) {
    this.pagination = this._utilityService.pagination;
    this.products("");
    this.getCategories();

    _productService.searchKeyword.subscribe((response:any)=>{
      this.products(response);
    })
  }

  nextSlide() {
    this.slidePosition -= 1;
    this.foodCategories?.push(this.foodCategories.shift() as FoodCategoryType);
  }

  previousSlide() {
    this.slidePosition += 1;
    this.foodCategories?.unshift(this.foodCategories.pop() as FoodCategoryType);
  }

  products(keyword:any) {
    const ListData = {
      filter: {
        keyword: keyword
      },
      pagination: this.pagination
    }
    this._productService.searchProduct(ListData).then((response: any)=>{
      this.pagination = response.body.data;
      this.FoodList = this.pagination.data; 
    });
  }
  getCategories() {
    const ListData = {
      filter: {
        keyword: ""
      },
      pagination: this.pagination
    }
    this._productService.getAllCategories(ListData).then((response: any)=>{
      this.pagination = response.body.data;
      this.foodCategories = this.pagination.data; 
    });
  }

  getProductByCategoryId(categoryId: any){
    console.log("Hi")
    const data = {
      filter: {
        categoryId: categoryId,
        keyword: "",
        restaurantId: 0
      },
      pagination: this.pagination
    }

    this._productService.getProductByCategoryId(data).then((response: any)=> {
      this.pagination = response.body.data;
      this.FoodList = this.pagination.data;
    });
  }

}
