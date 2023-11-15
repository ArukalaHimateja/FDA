import { Injectable } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable } from 'rxjs';
import { ApiService } from '../../shared/services/api.service';
import { JwtHelperService } from '@auth0/angular-jwt';
import { UtilityService } from '../../shared/services/utility.service';
import { NavigationService } from '../../shared/services/navigation.service';
import { config } from 'src/app/shared/config/config';
import { MustMatch } from 'src/app/shared/validators/must-match';

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    isLoginSubject = new BehaviorSubject<boolean>(AuthService.hasToken());
    isLoggedIn: Observable<any> = new BehaviorSubject<boolean>(false);

    constructor(
        private _formBuilder: FormBuilder,
        private _apiService: ApiService,
        private _router: Router,
        public _jwtHelper: JwtHelperService,
        private _utilityService: UtilityService,
        private _navigationService: NavigationService
    ) { }

    /**
     * Craete Login Form
     */
    createUserForm(): FormGroup {
        return this._formBuilder.group({
            username: [null, [Validators.required, Validators.pattern("^((\\+91-?)|0)?[0-9]{10}$")]]
        })
    }

    /**
     * Craete Login Form
     */
    createAdminForm(): FormGroup {
        return this._formBuilder.group({
            username: [null, Validators.required],
            password: [null, Validators.required]
        })
    }

    login(data: any) {
        this._apiService.post(data, 'auth/login').then((response: any) => {
            if (response && response.body.status === "OK") {
                localStorage.setItem(`${config.appShortName}UserToken`, response.body.data.token);
                let user = {
                    id: response.body.data.user_details.id,
                    email: response.body.data.user_details.email,
                    name: response.body.data.user_details.fullName,
                    role: response.body.data.user_details.role,
                    restaurantId: response.body.data.user_details.restaurantId,
                    profileImage: null
                };
                localStorage.setItem(`${config.appShortName}User`, JSON.stringify(user));
                this.isLoginSubject.next(true);
                this._navigationService.setNavigation();
                this._router.navigate(['/dashboard']);
            } else {
                this._utilityService.errorMessage(response.body.message, response.statusText);
            }
        }, error => {
            if (error.status !== 0)
                this._utilityService.errorMessage(error.error.message, error.statusText);
            else
                this._utilityService.errorMessage("Server Error", "Try Again");
        })
    }

    verifyLogin(data: any) {
        this._apiService.post(data, 'auth/otp/verify').then((response: any) => {
            if (response && response.status === 200) {
                localStorage.setItem('userToken', response.body.data.headers['Set-Cookie'][0]);
                let user = {
                    id: response.body.data.body.id,
                    email: response.body.data.body.email,
                    name: response.body.data.body.name,
                    role: response.body.data.body.role,
                    restaurantId: response.body.data.user_details.restaurantId,
                    profileImage: null
                };
                localStorage.setItem('user', JSON.stringify(user));
                this.isLoginSubject.next(true);
                this._navigationService.setNavigation();
                this._router.navigate(['/admin/dashboard']);
            } else {
                this._utilityService.errorMessage(response.body.message, response.statusText);
            }
        }, error => {
            if (error.status !== 0)
                this._utilityService.errorMessage(error.error.message, error.statusText);
            else
                this._utilityService.errorMessage("Server Error", "Try Again");
        })
    }

    /**
     * Method For has token
     */
    private static hasToken(): boolean {
        return !!localStorage.getItem(`${config.appShortName}UserToken`);
    }

    /**
     * Log out the user then tell all the subscribers about the new status
     */
    logout(): void {
        let authUser = Object.assign({}, this.getAuthUser());
        localStorage.removeItem(`${config.appShortName}UserToken`);
        localStorage.removeItem(`${config.appShortName}User`);
        this.isLoginSubject.next(false);
        this._router.navigateByUrl("session/signin");
    }

    getAuthUser(): any {
        let user = localStorage.getItem(`${config.appShortName}User`);
        return user ? JSON.parse(user) : null;
    }

    getAuthToken(): any {
        return localStorage.getItem(`${config.appShortName}UserToken`);
    }

    /**
     * Is Authenticated
     */
    public isAuthenticated(): boolean {
        const token: any = this.getAuthToken();
        //Check whether the token is expired and return
        //true or false
        return !this._jwtHelper.isTokenExpired(token);
    }



    /**
     * Create Forgot Password Form
     * 
     * @returns 
     */
    createFrogotPasswordFrom(){
        return this._formBuilder.group({
            username: [null, [Validators.required, Validators.email]]
        })
    }

    /**
     * Forgot Password
     * 
     * @param email 
     * @returns 
     */
    forgotPassword(email: any){
        return this._apiService.post(email, 'user/password/forgot').then((response: any) => {
            if (response && response.body.status === 'OK') {
                this._utilityService.successMessage(response.body.message, response.statusText);
                this._router.navigate(['/session/signin']);
            } else {
                this._utilityService.errorMessage(response.body.message, response.statusText);
            };
        })
    }

    /**
    * Change Password Form
    */
  createChangePasswordForm(): FormGroup {
    return this._formBuilder.group({
      oldPassword: [null, [Validators.required]],
      password: [null, [Validators.required, Validators.pattern("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*\\W).{8,}$")]],
      confirmPassword: [null, [Validators.required]]
    }, {
      validator: MustMatch('password', 'confirmPassword')
    })
  }

  changePassword(formData: any) {
    return this._apiService.post(formData, 'changePassword').then((response: any) => {
      console.log(response, response.body.message, response.body.data);
      if (response.body.status === 'OK') {
        this._utilityService.successMessage(response.body.message, response.body.status);
      } else {
        this._utilityService.errorMessage(response.body.message, response.body.status);
      }
    })
  }
}
