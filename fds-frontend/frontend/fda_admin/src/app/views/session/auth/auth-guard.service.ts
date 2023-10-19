import { Injectable } from '@angular/core';
import { Router, CanActivate } from '@angular/router';
import { AuthService } from '../auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {

  constructor(public _authService: AuthService, public router: Router) { }

  canActivate(): boolean {
    if (!this._authService.isAuthenticated()) {
      this.router.navigateByUrl('/session/signin');
      return false;
    }
    return true;
  }
}