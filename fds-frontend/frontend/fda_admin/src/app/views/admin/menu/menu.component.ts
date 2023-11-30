import { Component } from '@angular/core';
import { MenuService } from './menu.service';
import { MatTableDataSource } from '@angular/material/table';
import { MenuListType, Pagination } from 'src/app/shared/interfaces/pagination.interface';
import { ConstantService } from 'src/app/shared/services/constant.service';
import { ToastService } from 'src/app/shared/services/toast.service';
import { UtilityService } from 'src/app/shared/services/utility.service';
import { AuthService } from '../../session/auth.service';
import { UserService } from '../user/user.service';
import { MatDialog } from '@angular/material/dialog';
import { AddComponent } from './add/add.component';
import { ViewComponent } from './view/view.component';

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
  restaurantId: any;

  constructor(
    public _utilityService: UtilityService,
    public _menuService: MenuService,
    private _authService: AuthService,
    public _userService: UserService,
    private _dialog: MatDialog,
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
        restaurantId: this.sessionUser.restaurantId
      },
      pagination: this.pagination
    }
    this._menuService.getMenuListByFilterWithPagination(json).then((response: any) => {
      console.log("response", response.body.data);
      this.pagination = response.body.data;
      this.dataSource = new MatTableDataSource<any>(this.pagination.data);
    })
  }


  addMenu() {
    const dialogRef = this._dialog.open(AddComponent, { disableClose: true, width: '600px', data: { type: 'add', id: this.sessionUser.restaurantId } });
    dialogRef.afterClosed().subscribe((response: any) => {
      this.getDataList();
    })
  }
  updateMenu(element: any) {
    const dialogRef = this._dialog.open(AddComponent, { disableClose: true, width: '600px', data: { type: 'update', id: element.id, element: element } });
    dialogRef.afterClosed().subscribe((response: any) => {
      this.getDataList();
    })
  }

  updateProductState(element: any, event: any) {
    if (element.active) {
      let result = confirm("Are you sure you want to Approve?");
      if (result) {
        this._menuService.isInactiveProduct(element.id).then((response: any) => {
          if (response.statusText === 'OK') {
            this._utilityService.successMessage(response.body.message, response.body.status);
          } else {
            this._utilityService.errorMessage(response.body.message, response.body.status);
          }
        });
      } else {
        event.checked = !event.checked;
        event.source._checked = event.checked;
      }
    } else {
      let result = confirm("Are you sure you want to Approve?");
      if (result) {
        this._menuService.isActiveProduct(element.id).then((response: any) => {
          if (response.statusText === 'OK') {
            this._utilityService.successMessage(response.body.message, response.body.status);
          } else {
            this._utilityService.errorMessage(response.body.message, response.body.status);
          }
        });
      } else {
        event.checked = !event.checked;
        event.source._checked = event.checked;
      }
    }
  }

  viewProduct(element: any){
    this._dialog.open(ViewComponent, {disableClose: true ,width: '600px', data: {element: element}});
  }
}
