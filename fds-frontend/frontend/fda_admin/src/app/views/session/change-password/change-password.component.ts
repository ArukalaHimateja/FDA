import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { UtilityService } from 'src/app/shared/services/utility.service';
import { AuthService } from '../auth.service';
// import { UserService } from '../../../views/admin/user/user.service';
// import { UtilityService } from '../../services/utility.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss']
})
export class ChangePasswordComponent implements OnInit {

  formGroup: FormGroup;
  sessionUser: any;

  constructor(
    private _utilityService: UtilityService,
    private _authService: AuthService,
    private _dialogRef: MatDialogRef<ChangePasswordComponent>
  ) {
    this.sessionUser = this._utilityService.getAuthUser();
    //Create Form
    this.formGroup = this._authService.createChangePasswordForm();
  }

  ngOnInit() {
  }

  /**
   * Submit
   */
  submit() {
    const data = this.formGroup.getRawValue();
    let formData = new FormData();
    formData.append("oldPassword", data.oldPassword);
    formData.append("newPassword", data.password);
    formData.append("userId", this.sessionUser.id);
    this._authService.changePassword(formData);
    this.formGroup.reset();
  }
  closeDialog(){
    this._dialogRef.close();
  }
}
