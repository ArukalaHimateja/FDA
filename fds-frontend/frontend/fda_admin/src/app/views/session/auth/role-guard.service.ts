import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot } from '@angular/router';
import { AuthService } from '../auth.service';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable()
export class RoleGuardService implements CanActivate {

    constructor(public _authService: AuthService, public router: Router, public jwtHelper: JwtHelperService) {
    }

    canActivate(route: ActivatedRouteSnapshot): boolean {

        // this will be passed from the route config
        // on the data property
        const expectedRole = route.data.expectedRole;

        const token = this._authService.getAuthToken();

        // decode the token to get its payload
        const tokenPayload = this.jwtHelper.decodeToken(token);

        if (!this._authService.isAuthenticated() || tokenPayload.role !== expectedRole) {
            this.router.navigate(['/session/signin']);
            return false;
        }
        return true;
    }

}