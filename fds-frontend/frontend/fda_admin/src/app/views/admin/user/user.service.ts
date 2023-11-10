import { Injectable } from '@angular/core';
import { UtilityService } from '../../../shared/services/utility.service';
import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';
import { BehaviorSubject, Observable } from 'rxjs';
import { ApiService } from '../../../shared/services/api.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoadingService } from '../../../shared/services/loading.service';
import { Pagination } from '../../../shared/interfaces/pagination.interface';
import { MustMatch } from '../../../shared/validators/must-match';
import { ConstantService } from '../../../shared/services/constant.service';
import { UserRequest } from './user-request.model';

@Injectable({
    providedIn: 'root'
})
export class UserService implements Resolve<any>{

    onDataChanged: BehaviorSubject<any>;
    onDataListChanged: BehaviorSubject<Pagination>;
    data: any = null;
    routeParams: any = null;
    state: string = "";
    displayedColumns: string[] = ["id","fullName", "email","mobileNumber","role","createdAt", "active" ,"verify"];
    customerDisplayedColumns: string[] = ['no', 'image', 'name', 'email', 'mobileNumber', 'createdAt'];

    REQUEST_STATUS: any[] = [
        { key: 0, value: 'Pending', color: 'primary' },
        { key: 1, value: 'Accepted', color: 'warn' },
        { key: 2, value: 'Rejected', color: 'acent' }
    ]
    /**
     * Constructor
     *
     * @param {UtilityService} _utilityService
     * @param {ApiService} _apiService
     */
    constructor(
        public _utilityService: UtilityService,
        private _apiService: ApiService,
        private _formBuilder: FormBuilder,
        private _loadingService: LoadingService,
        private _router: Router,
        private _constantService: ConstantService
    ) {
        // Set the defaults
        this.onDataChanged = new BehaviorSubject({});
        this.onDataListChanged = new BehaviorSubject<Pagination>(_utilityService.pagination);
    }

    /**
      * Resolver
      *
      * @param {ActivatedRouteSnapshot} route
      * @param {RouterStateSnapshot} state
      * @returns {Observable<any> | Promise<any> | any}
      */
    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {
        this.routeParams = route.params;
        this.state = state.url;

        return new Promise((resolve, reject) => {

            Promise.all([
                this.getUserForResolve()
            ]).then(
                () => {
                    resolve(0);
                },
                reject
            );
        });
    }

    getUserForResolve(): Promise<any> {
        return new Promise((resolve, reject) => {
            if (this.state.includes('add')) {
                this.data = undefined;
                this.onDataChanged.next(false);
                resolve(false);
            }
            else if (this.state.includes('edit') && this.routeParams.id) {
                this.getDataById(this.routeParams.id).then((response: any) => {
                    this.data = response.body.data;
                    this.onDataChanged.next(this.data);
                    resolve(response);
                }, reject);
            }
            else if (this.state.includes('view') && this.routeParams.id) {
                this.getDataById(this.routeParams.id).then((response: any) => {
                    this.data = response.body.data;
                    this.onDataChanged.next(this.data);
                    resolve(response);
                }, reject);
            }
        });
    }

    /**
     * Create form
     * 
     * @param element 
     */
    createForm(element: UserRequest): FormGroup {
        return this._formBuilder.group({
            name: [element ? element.name : null, [Validators.required]],
            email: [element ? element.email : null, [Validators.required, Validators.email]],
            mobileNumber: [element ? element.mobileNumber : null, [Validators.required, Validators.pattern("^((\\+91-?)|0)?[0-9]{10}$")]],
            businessName: [element ? element.businessName : null, [Validators.required]],
            businessAddress: [element ? element.businessAddress : null, [Validators.required]],
            businessEmail: [element ? element.businessEmail : null, [Validators.required, Validators.email]],
            businessPhone: [element ? element.businessPhone : null, [Validators.required, Validators.pattern("^((\\+91-?)|0)?[0-9]{10}$")]],
        })
    }

    /**
     * Create form
     * 
     * @param element 
     */
    createUserForm(element?: UserRequest): FormGroup {
        return this._formBuilder.group({
            name: [element ? element.name : null, [Validators.required]],
            email: [element ? element.email : null, [Validators.required, Validators.email]],
            mobileNumber: [element ? element.mobileNumber : null, [Validators.required, Validators.pattern("^((\\+91-?)|0)?[0-9]{10}$")]],
        })
    }

    /**
     * Change Password Form
     */
    createChangePasswordForm(): FormGroup {
        return this._formBuilder.group({
            oldPassword: [null, [Validators.required]],
            password: [null, [Validators.required, Validators.pattern("(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}")]],
            confirmPassword: [null, [Validators.required]]
        }, {
            validator: MustMatch('password', 'confirmPassword')
        })
    }

    /**
     * Add or save data
     * 
     * @param data 
     * @param type 
     */
    addOrUpdateData(data: any, type: any) {
        this._loadingService.loading.next(true);
        this._apiService.post(data, type === 'add' ? 'admin/retailer/add' : 'admin/customer/update').then((response: any) => {
            if (response && response.body.status === 'OK') {
                this._loadingService.loading.next(false);
                this._utilityService.successMessage(response.body.message, response.body.status);
                this._router.navigateByUrl("/admin/retailer/view/" + response.body.data.retailer.id);
            } else {
                this._loadingService.loading.next(false);
                this._utilityService.errorMessage(response.body.message, response.body.status);
            }
        }, error => {
            this._loadingService.loading.next(false);
            console.log(error);
            if (error.status === 0) {
                this._utilityService.errorMessage('Internal Server Error! Please try again after some time', 'ERROR');
            } else {
                this._utilityService.errorMessage(error.error.message, error.statusText);
            }
        });
    }

    /**
     * Add or save data
     * 
     * @param data 
     * @param type 
     */
    addUserData(data: any) {
        return this._apiService.post(data, 'customer/add');
    }

    /**
     * Change password
     * 
     * @param formData 
     * @param userId 
     */
    changePassword(formData: FormData, userId: any) {
        return this._apiService.post(formData, `user/changePassword/${userId}`);
    }

    /**
     * Get data list with pagination
     * 
     * @param data 
     */
    // getListWithPagination(data: any) {
    //     return this._apiService.post(data, '/customer/pagination/filter');
    // }

    /**
     * Get data list by filter with pagination
     * 
     * @param data 
     */
    // getListByFilterWithPagination(data: any) {
    //     return this._apiService.post(data, 'retailer/pagination/filter');
    // }

    /**
     * Get data list by filter with pagination
     * 
     * @param data 
     */
    getCustomerListByFilterWithPagination(data: any) {
        return this._apiService.post(data, 'customer/pagination/filter');
    }

    /**
     * Get data list by filter with pagination
     * 
     * @param data 
     */
    getListByFilter(data: any) {
        this._loadingService.loading.next(true);
        this._apiService.post(data, 'user/getListByFilter').then((response: any) => {
            this._loadingService.loading.next(false);
            if (response && response.body.status === 'OK') {
            } else {
                this._utilityService.errorMessage(response.body.message, response.body.status);
            }
        }, error => {
            this._loadingService.loading.next(false);
            console.log(error);
            if (error.status === 0) {
                this._utilityService.errorMessage('Internal Server Error! Please try again after some time', 'ERROR');
            } else {
                this._utilityService.errorMessage(error.error.message, error.statusText);
            }
        });;
    }

    /**
     * Change data status
     */
    changeStatus(formData: any, json: any) {
        this._loadingService.loading.next(true);
        this._apiService.post(formData, `user/changeStatus`).then((response: any) => {
            this._loadingService.loading.next(false);
            if (response && response.statusText === 'OK') {
                this.getCustomerListByFilterWithPagination(json);
                this._utilityService.successMessage(response.body.message, response.status);
            } else {
                this._utilityService.errorMessage(response.body.message, response.status);
            }
        }, error => {
            this._loadingService.loading.next(false);
            console.log(error);
            if (error.status === 0) {
                this._utilityService.errorMessage('Internal Server Error! Please try again after some time', 'ERROR');
            } else {
                this._utilityService.errorMessage(error.error.message, error.statusText);
            }
        });
    }

    /**
     * Get data list by filter
     * 
     * @param data 
     */
    getUserListByFilter(data: any) {
        return this._apiService.post(data, 'user/getUserListByFilter');
    }

    /**
     * Check data email exist
     * 
     * @param emailId 
     */
    checkUserExistByEmail(emailId: any) {
        return this._apiService.get(`user/checkUserExistByEmail${emailId}`);
    }

    /**
     * Get All Data
     */
    getAll() {
        return this._apiService.get(`user/getAll`);
    }

    /**
     * Forgot password
     * 
     * @param emailId 
     */
    forgotPassword(emailId: any) {
        return this._apiService.get(`user/forgotPassword${emailId}`);
    }

    /**
     * Get data by id
     * 
     * @param id 
     */
    getDataById(id: any) {
        return this._apiService.get(`user/getById/${id}`);
    }

    /**
     * Get user details by id
     * 
     * @param userId 
     */
    getUserDetailsById(userId: any) {
        return this._apiService.get(`user/getUserDetailsById/${userId}`);
    }

    /**
     * get user list
     */
    getUserList() {
        return this._apiService.get('user/getUserList');
    }

    /**
     * Delete  by id
     * 
     * @param id 
     */
    deleteById(id: string, json: any) {
        this._loadingService.loading.next(true);
        this._apiService.delete(`user/deleteById/${id}`).then((response: any) => {
            this._loadingService.loading.next(false);
            if (response && response.body.status === 'OK') {
                this.getCustomerListByFilterWithPagination(json);
                this._utilityService.successMessage(response.body.message, response.body.status);
            } else {
                this._utilityService.errorMessage(response.body.message, response.body.status);
            }
        }, error => {
            this._loadingService.loading.next(false);
            console.log(error);
            if (error.status === 0) {
                this._utilityService.errorMessage('Internal Server Error! Please try again after some time', 'ERROR');
            } else {
                this._utilityService.errorMessage(error.error.message, error.statusText);
            }
        });
    }



    /**
    * Get User Role
    * 
    * @param role 
    */
    getUserRole(role: any) {
        let element = this._constantService.USER_ROLES.find(item => item.key === role);
        if (element) {
            return element.value;
        } else {
            return 'User';
        }
    }


    getRequestColor(key: any) {
        if (key === 0) {
            return 'blue';
        } else if (key === 1) {
            return 'green';
        } else if (key === 2) {
            return 'red';
        } else {
            return 'blue';
        }
    }

    /**
     * Get Color
     * 
     * @param key 
     */
    getStatus(key: any) {
        let element = this.REQUEST_STATUS.find(item => item.key === key);
        if (element) {
            return element.value;
        } else {
            return 'Pending';
        }
    }


    /**
     * Get Color
     * 
     * @param key 
     */
    getColor(key: any) {
        let element = this._constantService.COLORS.find(item => item.key === key);
        if (element) {
            return element.value;
        } else {
            return 'primary';
        }
    }

    /**
     * Approvel Request
     * 
     * @param id 
     * @returns 
     */
    approveRequest(id: any) {
        return this._apiService.get(`admin/Approved/restaurant/request/${id}`);
    }

    /**
     * Reject Request
     * 
     * @param id
     * @returns 
     */
    rejectRequest(id: any) {
        return this._apiService.get(`admin/reject/restaurant/request/${id}`);
    }

    /**
     * Active
     * 
     * @param id 
     * @returns 
     */
    active(id: any) {
        return this._apiService.post(null, `user/${id}/active`);
    }

    /**
     * Inactive
     * 
     * @param id 
     * @returns 
     */
    inactive(id: any) {
        return this._apiService.post(null, `user/${id}/inactive`);
    }
}
