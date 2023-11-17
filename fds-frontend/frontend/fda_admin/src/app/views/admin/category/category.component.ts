import { Component } from '@angular/core';
import { CategoryService } from './category.service';
import { MatTableDataSource } from '@angular/material/table';
import { MenuListType, Pagination } from 'src/app/shared/interfaces/pagination.interface';
import { ConstantService } from 'src/app/shared/services/constant.service';
import { ToastService } from 'src/app/shared/services/toast.service';
import { UtilityService } from 'src/app/shared/services/utility.service';
import { AuthService } from '../../session/auth.service';
import { UserService } from '../user/user.service';
import { MatDialog } from '@angular/material/dialog';
import { AddComponent } from './add/add.component';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.scss']
})
export class CategoryComponent {
  displayedColumns: string[] = [];
  dataSource: any;
  pagination: Pagination;
  sessionUser: any;
  restaurantId: any;

  constructor(
    public _utilityService: UtilityService,
    public _categoryService: CategoryService,
    private _authService: AuthService,
    public _userService: UserService,
    private _dialog: MatDialog,
  ) {
    this.displayedColumns = _categoryService.displayedColumns;
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
  // getNextPageData(page: any) {
  //   this.pagination.currentPage = page.pageIndex + 1;
  //   this.getDataList();
  // }

  /**
   * Get Data List with pagination
   */
  getDataList() {
    this._categoryService.getCategoryListByRestaurantId(this.sessionUser.restaurantId).then((response: any) => {
      console.log("response", response.body.data);
      // this.pagination = response.body.data;
      this.dataSource = new MatTableDataSource<any>(response.body.data);
    })
  }

  addCategory() {
    const dialogRef = this._dialog.open(AddComponent, { disableClose: true, width: '600px', data: { type: 'add' } });
    dialogRef.afterClosed().subscribe((response: any) => {
      this.getDataList();
    })
  }
  updateCategory(element: any) {
    const dialogRef = this._dialog.open(AddComponent, { disableClose: true, width: '600px', data: { type: 'update', id: element.id, element: element} });
    dialogRef.afterClosed().subscribe((response: any) => {
      this.getDataList();
    })
  }
}
