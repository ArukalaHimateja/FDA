import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Pagination } from '../../../../shared/interfaces/pagination.interface';
import { ConstantService } from '../../../../shared/services/constant.service';
import { UtilityService } from '../../../../shared/services/utility.service';
import { AuthService } from '../../../session/auth.service';
import { PromoCodeService } from '../promo-code.service';
// import { ApiResponse } from '../../../../shared/interfaces/api-response.interface';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent implements OnInit {

  displayedColumns: string[] = [];
  dataSource: any;
  pagination: Pagination;
  dialogRef: any;
  sessionUser: any;
  pageNumber: any = 1;
  filterModel: any = {
    role: 'all',
    status: 'all',
    filter: null
  }

  constructor(
    public _utilityService: UtilityService,
    public _promoCodeService: PromoCodeService,
    public _constantService: ConstantService,
    private _authService: AuthService
  ) {
    this.displayedColumns = _promoCodeService.displayedColumns;
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
    this.pageNumber = page.pageIndex + 1;
    this.pagination.currentPage = page.pageIndex + 1;
    this.getDataList();
  }

  /**
   * Get Data List with pagination
   */
  getDataList() {
    let json: any = {
      pagination: this.pagination
    }
    this._promoCodeService.search(json).then((response: any) => {
      this.pagination = response.body.data;
      this.dataSource = new MatTableDataSource<any>(this.pagination.data);
    })
  }

  /**
   * Change status
   * 
   * @param userStatus 
   */
  changeStatus(status: any, id: string) {
    let result = confirm("Are you sure you want to Change Status?");
    if (result == true) {
      let json = {
        id: id,
        status: status
      }
      let filter = {
        filter: this.filterModel,
        pagination: this.pagination
      }
      this._promoCodeService.changeStatus(json, filter);
    }
  }

  /**
   * Delete by id
   * 
   * @param id 
   */
  deleteById(id: any) {
    let result = confirm("Are you sure you want to delete?");
    if (result == true) {
      let filter = {
        filter: this.filterModel,
        pagination: this.pagination
      }
      this._promoCodeService.deleteById(id, filter);
    }
  }
}
  