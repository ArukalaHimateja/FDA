import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../views/session/auth.service';
import { UtilityService } from '../../services/utility.service';

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
    public _utilityService: UtilityService
  ) {
    this.sessionUser = _authService.getAuthUser();
  }

  ngOnInit() {
  }

  logout() {
    this._authService.logout();
  }
}
