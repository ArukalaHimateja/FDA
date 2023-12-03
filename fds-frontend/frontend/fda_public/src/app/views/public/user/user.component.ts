import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { UtilityService } from 'src/app/shared/services/utility.service';
import { ProfileComponent } from './profile/profile.component';
import { style } from '@angular/animations';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent {
  sessionUser: any;
  selectedOptionValue: string = 'order-history';
  constructor(
    private _utilityService: UtilityService,
    private dialog: MatDialog,
    private router: Router,
    private _activatedRoute: ActivatedRoute,
  ) {
    // if(Object.keys(_activatedRoute.snapshot.params).length === 1){
    //   router.navigateByUrl('user/order/history');
    // }
    this.sessionUser = this._utilityService.getAuthUser();
  }


  onSelectionChange(event: any) {
    if (event.source.selectedOptions.selected.length > 0) {
      this.selectedOptionValue = event.source.selectedOptions.selected[0].value;
      console.log(this.selectedOptionValue)
    } else {
      this.selectedOptionValue = 'None';
    }
  }
  editProfile() {
    this.dialog.open(ProfileComponent, { height: '100vh', width: '500px' });
  }
}
