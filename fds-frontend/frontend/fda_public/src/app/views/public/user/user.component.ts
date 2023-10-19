import { Component } from '@angular/core';
import { UtilityService } from 'src/app/shared/services/utility.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent {
  sessionUser: any;
  constructor(
    private _utilityService: UtilityService,
  ){
    this.sessionUser = this._utilityService.getAuthUser();
  }
  ngOnInit(){
  }

  onClick(){
    console.log("hi");
  }
  selectedOptionValue!: string;

  onSelectionChange(event: any) {
    // Handle the selection change here
    // You can access the selected option using event.source.selectedOptions.selected[0].value
    if (event.source.selectedOptions.selected.length > 0) {
      this.selectedOptionValue = event.source.selectedOptions.selected[0].value;
      console.log(this.selectedOptionValue)
    } else {
      this.selectedOptionValue = 'None';
    }
  }
}
