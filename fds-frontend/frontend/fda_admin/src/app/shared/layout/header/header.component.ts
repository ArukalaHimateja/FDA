import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../views/session/auth.service';
import { UtilityService } from '../../services/utility.service';
import { MatDialog } from '@angular/material/dialog';
import { ChangePasswordComponent } from 'src/app/views/session/change-password/change-password.component';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  dialogRef: any;
  sessionUser: any;

  constructor(
    private _authService: AuthService,
    public _utilityService: UtilityService,
    private _matDialog: MatDialog
  ) {
    this.sessionUser = _authService.getAuthUser();
  }

  ngOnInit() {
  }

  logout() {
    this._authService.logout();
  }

  changePassword(){
    this._matDialog.open(ChangePasswordComponent, {disableClose:true, width: '600px'});
  }
}
