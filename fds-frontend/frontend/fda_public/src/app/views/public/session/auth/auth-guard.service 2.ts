import { Injectable } from '@angular/core';
import { Router, CanActivate } from '@angular/router';
import { SessionService } from '../session.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {

  constructor(public _sessionService: SessionService, public router: Router) { }

  canActivate(): boolean {
    if (!this._sessionService.isAuthenticated()) {
      this.router.navigateByUrl('/session/signin');
      return false;
    }
    return true;
  }
}