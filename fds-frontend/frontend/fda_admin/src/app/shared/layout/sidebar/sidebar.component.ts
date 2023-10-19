import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NavigationService } from '../../services/navigation.service';
import { config } from 'src/app/shared/config/config';
import { AuthService } from 'src/app/views/session/auth.service';
import { UtilityService } from '../../services/utility.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent implements OnInit {

  navbarList: any = [];
  sessionUser: any;
  config: any = config;

  constructor(
    public _authService: AuthService,
    private _router: Router,
    private _navigationService: NavigationService,
    public _utilityService: UtilityService
  ) {
    this.sessionUser = _authService.getAuthUser();
    _navigationService.menuItems.subscribe((response: any) => {
      this.navbarList = response;
    })
  }

  ngOnInit() {
  }

  onClickNavBar(navbar: any) {
    let element = document.getElementById("toggleButton");
    if (element)
      element.click();
    this._router.navigateByUrl(navbar);
  }

}
