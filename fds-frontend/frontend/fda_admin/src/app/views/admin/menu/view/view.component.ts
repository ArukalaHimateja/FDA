import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { AddComponent } from '../add/add.component';
import { MenuService } from '../menu.service';

@Component({
  selector: 'app-view',
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.scss']
})
export class ViewComponent {

  restaurantDtl: any;
  reviewListForProdct: any = [];
  constructor(
    public _menuService: MenuService,
    public matDialogRef: MatDialogRef<AddComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
  ) {
    this._menuService.getReviewListByProductId(data.element.id).then((response: any) => {
      if(response.body.status === 'OK'){
        this.reviewListForProdct = response.body.data;
      }
    });
  }

}
