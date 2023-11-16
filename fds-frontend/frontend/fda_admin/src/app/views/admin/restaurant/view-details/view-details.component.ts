import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { RestaurantService } from '../restaurant.service';

@Component({
  selector: 'app-view-details',
  templateUrl: './view-details.component.html',
  styleUrls: ['./view-details.component.scss']
})
export class ViewDetailsComponent {
  restaurantDtl: any;

  constructor(
    public matDialogRef: MatDialogRef<ViewDetailsComponent>,
    @Inject(MAT_DIALOG_DATA) public id: any,
    private _restaurantService: RestaurantService,
  ) {
    if (id) {
      this.getRestaurantDetailsById(id);
    } else {
      console.log("No Data");
    }
  }

  getRestaurantDetailsById(id: any) {
    this._restaurantService.getRestaurantDtlByRequestId(id).then((response: any) => {
      this.restaurantDtl = response.body.data;
      console.log(this.restaurantDtl);
    })
  }
  viewDocument(){
    this._restaurantService.downloadDocumentByRestaurantRequestId(this.id);
  }
}
