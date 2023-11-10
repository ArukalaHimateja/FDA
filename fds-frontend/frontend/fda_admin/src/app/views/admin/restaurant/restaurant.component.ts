import { Component } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Pagination } from 'src/app/shared/interfaces/pagination.interface';
import { ConstantService } from 'src/app/shared/services/constant.service';
import { UtilityService } from 'src/app/shared/services/utility.service';
import { AuthService } from '../../session/auth.service';
import { UserService } from '../user/user.service';
import { RestaurantService } from './restaurant.service';
import { ToastService } from 'src/app/shared/services/toast.service';

@Component({
  selector: 'app-restaurant',
  templateUrl: './restaurant.component.html',
  styleUrls: ['./restaurant.component.scss']
})
export class RestaurantComponent {
  displayedColumns: string[] = [];
  dataSource: any;
  pagination: Pagination;
  dialogRef: any;
  sessionUser: any;

  constructor(
    public _utilityService: UtilityService,
    public _restaurantService: RestaurantService,
    public _constantService: ConstantService,
    private _authService: AuthService,
    public _userService: UserService,
    private _toastService: ToastService,
  ) {
    this.displayedColumns = _restaurantService.displayedColumns;
    this.pagination = _utilityService.pagination;
    this.sessionUser = this._authService.getAuthUser();
    this.getDataList();
  }

  ngOnInit() {
  }

  /**
  * Get next page data
  * 
  * @param page 
  */
  getNextPageData(page: any) {
    this.pagination.currentPage = page.pageIndex + 1;
    this.getDataList();
  }

  /**
   * Get Data List with pagination
   */
  getDataList() {
    let json = {
      restaurantRequestFilterDto: {
        status: 0
      },
      pagination: this.pagination
    }
    this._restaurantService.getListByFilterWithPagination(json).then((response: any) => {
      console.log(response.body.data)
      this.pagination = response.body.data;
      this.dataSource = new MatTableDataSource<any>(this.pagination.data);
    })
  }

  /**
   * Change status
   * 
   * @param userStatus 
   */
  changeStatus(status: Boolean, id: string) {
    let result = confirm("Are you sure you want to Change Status?");
    if (result == true) {
      if (status) {
        this._userService.active(id).then((response: any) => {
          if (response && response.status === 'OK') {
            this._toastService.successMessage(response.body.message, response.body.status);
            this.getDataList();
          } else {
            this._toastService.errorMessage(response.body.message, response.body.status);
          }
        })
      } else {
        this._userService.inactive(id).then((response: any) => {
          if (response && response.status === 'OK') {
            this._toastService.successMessage(response.body.message, response.body.message);
            this.getDataList();
          } else {
            this._toastService.errorMessage(response.body.message, response.body.status);
          }
        })
      }
    }
  }

  approveRequest(id: any) {
    let result = confirm("Are you sure you want to Approve?");
    if (result == true) {
      this._userService.approveRequest(id).then((response: any) => {
        if (response && response.status === 'OK') {
          this._toastService.successMessage(response.body.message, response.body.message);
          this.getDataList();
        } else {
          this._toastService.errorMessage(response.body.message, response.body.status);
        }
      })
    }
  }

  rejectRequest(id: any) {
    let result = confirm("Are you sure you want to Reject?");
    if (result == true) {
      this._userService.rejectRequest(id).then((response: any) => {
        if (response && response.status === 'OK') {
          this._toastService.successMessage(response.body.message, response.body.message);
          this.getDataList();
        } else {
          this._toastService.errorMessage(response.body.message, response.body.status);
        }
      })
    }
  }

}
