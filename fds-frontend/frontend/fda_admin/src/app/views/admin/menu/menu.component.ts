import { Component } from '@angular/core';
import { MenuService } from './menu.service';
import { MatTableDataSource } from '@angular/material/table';
import { Pagination } from 'src/app/shared/interfaces/pagination.interface';
import { ConstantService } from 'src/app/shared/services/constant.service';
import { ToastService } from 'src/app/shared/services/toast.service';
import { UtilityService } from 'src/app/shared/services/utility.service';
import { AuthService } from '../../session/auth.service';
import { UserService } from '../user/user.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent {
  displayedColumns: string[] = [];
  dataSource: any;
  pagination: Pagination;
  dialogRef: any;
  sessionUser: any;

  constructor(
    public _utilityService: UtilityService,
    public _menuService: MenuService,
    private _authService: AuthService,
    public _userService: UserService,
  ) {
    this.displayedColumns = _menuService.displayedColumns;
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
      filter: {
        categoryId: 0,
        keyword: "",
        restaurantId: this.sessionUser.id
      },
      pagination: this.pagination
    }
    this._menuService.getMenuListByFilterWithPagination(json).then((response: any) => {
      console.log("response",response.body.data);
      this.pagination = response.body.data;
      this.dataSource = new MatTableDataSource<any>(this.pagination.data);
    })
  }
  }
