import { Component, Inject } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { UtilityService } from 'src/app/shared/services/utility.service';
import { UserService } from '../User.service';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent {
  formGroup!: FormGroup;
  user: any;

  constructor(
    private _userService: UserService,
    public dialogRef: MatDialogRef<ProfileComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.formGroup = this._userService.createUpdateProfileForm(null);
    _userService.getUserDtl().then((response: any) => {
      this.user = response.body.data;
      console.log(response.body.status)
      if (response.body.status === 'OK') {
        this.formGroup = this._userService.createUpdateProfileForm(this.user);
      }
    })
  }
  closeDialog() {
    this.dialogRef.close()
  }
  updateProfile() {
    const data = this.formGroup.getRawValue();
    this._userService.updateProfile(data,this.user.userId);
    this.dialogRef.close();
  }
}
