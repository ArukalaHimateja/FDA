import { Injectable } from '@angular/core';
import { UtilityService } from '../../../shared/services/utility.service';
import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';
import { BehaviorSubject, Observable } from 'rxjs';
import { ApiService } from '../../../shared/services/api.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoadingService } from '../../../shared/services/loading.service';
import { Pagination } from '../../../shared/interfaces/pagination.interface';
import { ConstantService } from '../../../shared/services/constant.service';
import { PromoCodeRequest } from './promo-code-request.model';
import { ToastService } from '../../../shared/services/toast.service';

@Injectable({
    providedIn: 'root'
})
export class PromoCodeService implements Resolve<any>{

    onDataChanged: BehaviorSubject<any>;
    onDataListChanged: BehaviorSubject<Pagination>;
    data: any = null;
    routeParams: any = null;
    state: string = "";
    displayedColumns: string[] = ['no', 'promoCode', 'startDate', 'endDate', 'discount', 'createdAt', 'status', 'action'];

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
        private _constantService: ConstantService,
        private _toastService: ToastService
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
                    this.data = response.data;
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
    createForm(element: PromoCodeRequest): FormGroup {
        return this._formBuilder.group({
            propertyId: [element ? element.propertyId : null],
            promoCode: [element ? element.promoCode : null, [Validators.required]],
            startDate: [element ? element.startDate : null, [Validators.required]],
            endDate: [element ? element.endDate : null, [Validators.required]],
            discount: [element ? element.discount : null, [Validators.required]],
            description: [element ? element.description : null, [Validators.required]]
        })
    }

    /**
     * Add or save data
     * 
     * @param data 
     * @param type 
     */
    addOrUpdateData(data: any, id: any) {
        this._loadingService.loading.next(true);
        this._apiService.post(data, !id ? 'promoCode/add' : `promoCode/${id}/update`).then((response: any) => {
            if (response && response.status === 'OK') {
                this._loadingService.loading.next(false);
                this._toastService.success(response.message);
                this._router.navigateByUrl("/admin/promo/code/list");
            } else {
                this._loadingService.loading.next(false);
                this._toastService.error(response.message);
            }
        }, error => {
            this._loadingService.loading.next(false);
            console.log(error);
            if (error.status === 0) {
                this._toastService.error('Internal Server Error! Please try again after some time');
            } else {
                this._toastService.error(error.error.message);
            }
        });;
    }

    /**
     * Get data list with pagination
     * 
     * @param data 
     */
    getListWithPagination(data: any) {
        return this._apiService.post(data, 'user/getUserListWithPagination');
    }

    /**
     * Get data list by filter with pagination
     * 
     * @param data 
     */
    getListByFilterWithPagination(data: any) {
        return this._apiService.get('get/all/promoCodes');
    }

    /**
    * Get data list by filter with pagination
    * 
    * @param data 
    */
    search(data: any, limit: any, page: any) {
        return this._apiService.post(data, `promocode/search?limit=${limit}&page=${page}`);
    }

    /**
     * Get data list by filter with pagination
     * 
     * @param data 
     */
    getListByFilter(data: any) {
        this._loadingService.loading.next(true);
        this._apiService.get('user/getListByFilter').then((response: any) => {
            this._loadingService.loading.next(false);
            if (response && response.body.status === 'OK') {
            } else {
                this._toastService.error(response.body.message);
            }
        }, error => {
            this._loadingService.loading.next(false);
            console.log(error);
            if (error.status === 0) {
                this._toastService.error('Internal Server Error! Please try again after some time');
            } else {
                this._toastService.error(error.error.message);
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
                this.getListByFilterWithPagination(json);
                this._toastService.success(response.body.message);
            } else {
                this._toastService.error(response.body.message);
            }
        }, error => {
            this._loadingService.loading.next(false);
            console.log(error);
            if (error.status === 0) {
                this._toastService.error('Internal Server Error! Please try again after some time');
            } else {
                this._toastService.error(error.error.message);
            }
        });
    }

    /**
     * Get All Data
     */
    getAll() {
        return this._apiService.get(`user/getAll`);
    }

    /**
     * Get data by id
     * 
     * @param id 
     */
    getDataById(id: any) {
        return this._apiService.get(`promoCode/${id}`);
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
                this.getListByFilterWithPagination(json);
                this._toastService.success(response.body.message);
            } else {
                this._toastService.error(response.body.message);
            }
        }, error => {
            this._loadingService.loading.next(false);
            console.log(error);
            if (error.status === 0) {
                this._toastService.error('Internal Server Error! Please try again after some time');
            } else {
                this._toastService.error(error.error.message);
            }
        });
    }
}
