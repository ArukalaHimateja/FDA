import { Component, HostListener } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { UtilityService } from 'src/app/shared/services/utility.service';
import { SessionService } from 'src/app/views/public/session/session.service';
import { ProductService } from '../../services/product.service';
import { FoodListItemType, Pagination } from '../../shared/interfaces/pagination.interface';
import { UserService } from 'src/app/views/public/user/User.service';
import { MatDialog } from '@angular/material/dialog';
import { FilterDialogBoxComponent } from 'src/app/views/public/filter-dialog-box/filter-dialog-box.component';
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
  isAutocompleterActiveforLocation: boolean = false;
  isProfileIconMenuActive: boolean = false;
  FoodList: FoodListItemType[] = [];
  foodVarietyList: string[] = [];
  pagination!: Pagination;
  locationOption!: any

  constructor(
    public router: Router,
    private _utilityService: UtilityService,
    private _sessionsService: SessionService,
    private _productService: ProductService,
    private _userService: UserService,
    private _dialog: MatDialog,
  ) {
    this.pagination = _utilityService.pagination;
    router.events.subscribe((url: any) => {
      if (url && url.url) {
        if (url.url.includes("signin") || url.url.includes("signup") || url.url.includes("partner")) {
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
    this._productService.foodList.subscribe((response: any) => {
      this.foodVarietyList = response;
    })
    this._userService.allFoodList().then((response: any) => {
      const data: FoodListItemType[] = response.body.data; // Assuming data is an array
      // Extract food names and populate foodVarietyList
      const foodVarieties: string[] = data.map(item => item.productName);
      this.foodVarietyList = foodVarieties;
    });
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
      await this.searchProductByName(this.search_bar.value);
  }

  /**
 * Search Food by
 * 
 * @param filterValue 
 */
  async searchProductByName(filterValue: string | null) {
    this._productService.searchKeyword.next(filterValue);
    const filterData = {
      filter: { keyword: filterValue },
      pagination: this.pagination,
    }

    await this._productService.searchProduct(filterData).then((response: any) => {
      this.pagination = response.body.data;
      this.FoodList = this.pagination.data;
    });
  }
  openDialogForFilter() {
    const dialogRef = this._dialog.open(FilterDialogBoxComponent, {
      data: {}, height: '526px', width: '700px',
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log("result", result)
    })
  }
}
