import { Component } from '@angular/core';
import { UserService } from '../../user/User.service';
import { ProductService } from 'src/app/shared/services/product.service';
import { FoodListItemType, Pagination } from 'src/app/shared/shared/interfaces/pagination.interface';
import { ActivatedRoute, Router } from '@angular/router';
import { UtilityService } from 'src/app/shared/services/utility.service';

@Component({
  selector: 'app-food-by-category',
  templateUrl: './food-by-category.component.html',
  styleUrls: ['./food-by-category.component.scss']
})
export class FoodByCategoryComponent {
  image = "assets/images/sweets.jpg"
  offers = "45% off"
  rating = '4.3'
  timing = '34 min'
  FoodList!: FoodListItemType[];
  categoryId: any = null;
  pagination!: Pagination;
  constructor(
    public router: Router,
    public _activatedRoute: ActivatedRoute,
    public _userService: UserService,
    public _productService: ProductService,
    public _utilityService: UtilityService,
  ) {
    this.pagination = this._utilityService.pagination;
    this._activatedRoute.params.subscribe(routeParams => {
      this.categoryId = routeParams['categoryId'];
      this.getProdctByCategoryId(this.categoryId);
    });
  }

  getProdctByCategoryId(categoryId: any){
    const filterData = {
      filter: {
        categoryId: categoryId,
        keyword: "",
        restaurantId: 0
      },
      pagination: this.pagination
    }
    this._userService.getProductByCategoryId(filterData).then((response: any)=> {
      this.pagination = response.body.data;
      this.FoodList = this.pagination.data;
    })

  }
}
