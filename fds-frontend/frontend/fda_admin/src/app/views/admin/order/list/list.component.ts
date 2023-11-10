import { Component } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Pagination } from 'src/app/shared/interfaces/pagination.interface';
import { UtilityService } from 'src/app/shared/services/utility.service';
import { AuthService } from 'src/app/views/session/auth.service';
import { UserService } from '../../user/user.service';
import { OrderService } from '../order.service';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent {
  displayedColumns: string[] = [];
  dataSource: any;
  pagination: Pagination;
  pageNumber: any = 1;
  sessionUser: any;

  constructor(
    public _utilityService: UtilityService,
    private _authService: AuthService,
    private _orderService: OrderService,
  ) {
    this.pagination = _utilityService.pagination;
    this.sessionUser = this._authService.getAuthUser();
    this.getDataList();
  }
  
  /**
  * Get next page data
  * 
  * @param page 
  */
  getNextPageData(page: any) {
    this.pageNumber = page.pageIndex + 1;
    this.pagination.currentPage = page.pageIndex + 1;
    this.getDataList();
  }

  /**
   * Get Data List with pagination
   */
  getDataList() {
    this._orderService.getAllOrderWithPaginationByRestaurantId().then((response: any) => {
      this.dataSource = new MatTableDataSource(response.body.data);
    })
  }

}
