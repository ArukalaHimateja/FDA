import { Component, HostListener } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { UtilityService } from 'src/app/shared/services/utility.service';
import { SessionService } from 'src/app/views/public/session/session.service';
import { ProductService } from '../../services/product.service';
import { FoodListItemType } from '../../shared/interfaces/pagination.interface';
import { UserService } from 'src/app/views/public/user/user.service';
import { map } from 'rxjs';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  isThirdRowFixed = false;
  isLoginPage = false;
  isContactUsPage = false;
  sessionUser: any;

  location = new FormControl('');
  search_bar = new FormControl('');
  filterList: string[] = ['One', 'Two', 'Three'];
  locationList: string[] = ['skr', 'Jpr', 'ch'];
  filteredOptions: any[] = [];
  locationOption: any[] = [];
  isAutocompleterActiveforLocation: boolean = false;
  isProfileIconMenuActive: boolean = false;
  FoodList: FoodListItemType[] = [];
  foodVarietyList: string[] = [];
  constructor(
    private router: Router,
    private _utilityService: UtilityService,
    private _sessionsService: SessionService,
    private _productService: ProductService,
    private _userService: UserService,
  ) {
    router.events.subscribe((url: any) => {
      if (url && url.url) {
        if (url.url.includes("signin") || url.url.includes("signup")) {
          this.isLoginPage = true;
        }
        else if (url.url.includes("contact") || url.url.includes("user/")) {
          this.isLoginPage = false;
          this.isContactUsPage = true;
        } else {
          this.isLoginPage = false;
          this.isContactUsPage = false;
        }
      }
      this.sessionUser = this._utilityService.getAuthUser();
    });
  }

  ngOnInit() {
  //  this._userService.allFoodList().then((response: any) => {
    //  const data: FoodListItemType[] = response.body.data; // Assuming data is an array
  
      // Extract food names and populate foodVarietyList
      //const foodVarieties: string[] = data.map(item => item.foodName);
      //this.foodVarietyList = foodVarieties;
  
 //     console.log('hi2121', this.foodVarietyList);
   // });
  }

  // Scroll event listener
  @HostListener('window:scroll', ['$event'])
  onScroll() {
    if (!this.isLoginPage) {
      const scrollPosition = window.scrollY;
      // Adjust the scroll position where the row becomes fixed
      this.isThirdRowFixed = scrollPosition > 174;
    }
  }

  logout() {
    this._sessionsService.logout();
  }

  onAutocompleteOpenedforLocation() {
    this.isAutocompleterActiveforLocation = true;
  }
  onAutocompleteClosedforLocation() {
    this.isAutocompleterActiveforLocation = false;
  }
  onMenuOpenforProfileMenu() {
    this.isProfileIconMenuActive = true;
  }
  onMenuClosedforProfileMenu() {
    this.isProfileIconMenuActive = false;
  }

  async onInputProduct() {
    if (this.search_bar.value !== '' && this.search_bar.value !== null) {
      await this.searchProductByName(this.search_bar.value);
    } else {
      this._productService.sendData([]);
    }
  }

  /**
   * Search Greyhounds by
   * 
   * @param filterValue 
   */
  async searchProductByName(filterValue: string) {
    await this._productService.searchProduct(filterValue).then((response: any) => {
      if (response && response.body.status === 'OK') {
        this.filteredOptions = response.body.data;
        this._productService.sendData(this.filteredOptions);
      } else {
        this.filteredOptions = [];
        this._productService.sendData([]);
      }
      this.foodVarietyList = this._productService.foodVarietyList;
      console.log(this.foodVarietyList)
    })
  }
}
