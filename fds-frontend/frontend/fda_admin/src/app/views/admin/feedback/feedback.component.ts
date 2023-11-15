import { Component } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Pagination } from 'src/app/shared/interfaces/pagination.interface';
import { UtilityService } from 'src/app/shared/services/utility.service';
import { AuthService } from '../../session/auth.service';
import { OrderService } from '../order/order.service';
import { FeedbackService } from './feedback.service';

@Component({
  selector: 'app-feedback',
  templateUrl: './feedback.component.html',
  styleUrls: ['./feedback.component.scss']
})
export class FeedbackComponent {
  displayedColumns: string[] = [];
  dataSource: any;
  pagination: Pagination;
  pageNumber: any = 1;
  sessionUser: any;

  constructor(
    public _utilityService: UtilityService,
    private _authService: AuthService,
    private _feedbackService: FeedbackService,
  ) {
    this.displayedColumns = this._feedbackService.displayedColumns;
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
    let json = {
      pagination: this.pagination,
    }
    this._feedbackService.getFeedbackListByFilterWithPagination(json).then((response: any) => {
      this.dataSource = new MatTableDataSource(response.body.data.data);
    })
  }

}
