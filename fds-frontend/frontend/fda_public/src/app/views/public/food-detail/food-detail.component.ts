import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FoodListItemType, RestroResponseType } from 'src/app/shared/shared/interfaces/pagination.interface';
import { UserService } from '../user/User.service';
import { ProductService } from 'src/app/shared/services/product.service';
import { CartService } from 'src/app/shared/services/cart.service';

@Component({
  selector: 'app-food-detail',
  templateUrl: './food-detail.component.html',
  styleUrls: ['./food-detail.component.scss']
})
export class FoodDetailComponent {
  itemDetail = {
    productId: '',
    price: '',
    productSize: '',
    restaurantId: '',
    restaurantName: '',
    restaurantAddress: '',
    active: '',
    categoryId: '',
    categoryName: '',
    productName: '',
    productImage: '',
    description: '',
  }
  categoryList!: RestroResponseType[];
  itemId: any = null;
  selectedOptionValue!: number;
  image = "assets/images/sweets.jpg";

  constructor(
    public router: Router,
    public _activatedRoute: ActivatedRoute,
    public _userService: UserService,
    public _productService: ProductService,
    private _cartService: CartService
  ) {
    this.itemId = _activatedRoute.snapshot.params['id'];
    if (this.itemId) {
      this._userService.getProductById(this.itemId).then((MenuResponse) => {
        console.log('Item Detail', MenuResponse.body.data)
        this.itemDetail = MenuResponse.body.data;
        _userService.getAllCategoryByRestaurantId(MenuResponse.body.data.restaurantId).then((RestroResponse) => {
          this.categoryList = RestroResponse.body.data;
          if (this.categoryList.length > 0) {
            router.navigateByUrl(`foodDetail/${this.itemId}/foodByCategory/${this.categoryList[0].id}`);
            this.selectedOptionValue = this.categoryList[0].id;
          }
          console.log(RestroResponse.body.data)
        })
      })
    }
  }

  onSelectionChange(event: any) {
    if (event.source.selectedOptions.selected.length > 0) {
      this.selectedOptionValue = event.source.selectedOptions.selected[0].value;
      console.log(this.selectedOptionValue)
    } else {
      this.selectedOptionValue = 0;
    }
  }

  addToCart(element: any) {
    let json = {
      id: element.productId,
      name: element.productName,
      image: element.productImage,
      restaurantName: element.restaurantName,
      quantity: 1,
      price: element.price
    }
    this._cartService.addToCart(json);
  }
}
