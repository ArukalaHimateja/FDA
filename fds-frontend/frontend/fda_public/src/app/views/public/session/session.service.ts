import { Injectable } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { BehaviorSubject, Observable, retry } from 'rxjs';
import { ApiService } from 'src/app/shared/services/api.service';
import { UtilityService } from 'src/app/shared/services/utility.service';

@Injectable({
    providedIn: 'root'
})
export class SessionService {

    isLoginSubject = new BehaviorSubject<boolean>(SessionService.hasToken());
    isLoggedIn: Observable<any> = new BehaviorSubject<boolean>(false);

    constructor(
        private _formBuilder: FormBuilder,
        private _apiService: ApiService,
        private _router: Router,
        public _jwtHelper: JwtHelperService,
        private _utilityService: UtilityService
    ) { }

    /**
     * Craete Login Form
     */
    createForm(): FormGroup {
        return this._formBuilder.group({
            username: [null, Validators.required],
            password: [null, Validators.required]
        })
    }
    setLocalStorage(data: any) {
        let user = {
            id: data.id,
            email: data.email,
            name: data.fullName,
            role: data.role,
            profileImage: data.profileImage,
            verify: data.verify,
            mobileNumber: data.mobileNumber
        };
        localStorage.setItem('user', JSON.stringify(user));
    }

    /**
     * Login User
     * 
     * @param data 
     */
    login(data: any) {
        this._apiService.post(data, 'auth/login').then((response: any) => {
            if (response && response.body.status === 'OK') {
                localStorage.setItem('userToken', response.body.data.token);
                this.setLocalStorage(response.body.data.user_details);
                this.isLoginSubject.next(true);
                this._router.navigate(['/']);
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
    public static hasToken(): boolean {
        return !!localStorage.getItem('userToken');
    }

    /**
     * Log out the user then tell all the subscribers about the new status
     */
    logout(): void {
        localStorage.removeItem('userToken');
        localStorage.removeItem('user');
        this.isLoginSubject.next(false);
        this._router.navigateByUrl("/session/signin");
    }

    public isAuthenticated(): boolean {
        const token: any = localStorage.getItem('userToken');
        //Check whether the token is expired and return
        //true or false
        return !this._jwtHelper.isTokenExpired(token);
    }

    createRestaurantForm(): FormGroup {
        return this._formBuilder.group({
            ownerName: [null, [Validators.required]],
            restaurantName: [null, [Validators.required]],
            email: [null, [Validators.required, Validators.email]],
            mobileNumber: [null, [Validators.required, Validators.pattern("^[0-9]{10,}$")]],
            restaurantAddress: [null, [Validators.required]],
            restaurantLicenseNumber: [null, [Validators.required]],
            documents: [],
        })
    }

}
