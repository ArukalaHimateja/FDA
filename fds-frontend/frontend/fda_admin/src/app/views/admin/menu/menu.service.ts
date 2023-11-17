import { Injectable } from '@angular/core';
import { ApiService } from '../../../shared/services/api.service';
import { FormBuilder, Validators } from '@angular/forms';
import { UtilityService } from 'src/app/shared/services/utility.service';
import { AuthService } from '../../session/auth.service';

@Injectable({
    providedIn: 'root'
})
export class MenuService {

    displayedColumns: string[] = ['no', 'productName', 'categoryName', 'price', 'createdAt', 'productSize', 'status', "action"];
    sessionUser: any;

    /**
     * Constructor
     *
     * @param {ApiService} _apiService
     */
    constructor(
        private _apiService: ApiService,
        private _formBuilder: FormBuilder,
        private _utilityService: UtilityService,
        private _authService: AuthService,
    ) {
        this.sessionUser = this._authService.getAuthUser();
    }

    /**
     * Get data list by filter with pagination
     * 
     * @param data 
     */
    getMenuListByFilterWithPagination(data: any) {
        return this._apiService.post(data, 'product/pagination/filter');
    }

    /**
     * Get data by id
     * 
     * @param id 
     */
    getDataById(id: any) {
        return this._apiService.get(`properties/${id}`);
    }

    getRestaurantByUserId(userId: any) {
        return this._apiService.get(`get/user/restaurant/${userId}`);
    }

    createAddMenuForm() {
        return this._formBuilder.group({
            categoryId: [null, [Validators.required]],
            description: [null, [Validators.required]],
            price: [null, [Validators.required]],
            productImage: [null],
            productName: [null, [Validators.required]],
            productSize: [null, [Validators.required]],
            restaurantId: [this.sessionUser.restaurantId],
        })
    }
    createUpdateMenuForm(element: any) {
        return this._formBuilder.group({
            description: [element ? element.description : null, [Validators.required]],
            price: [element ? element.price : null, [Validators.required]],
            productImage: [element ? element.productImage : null],
            productName: [element ? element.productName : null, [Validators.required]],
            productSize: [element ? element.productSize : null, [Validators.required]],
            restaurantId: element ? element.restaurantId : [this.sessionUser.restaurantId],
        })
    }

    getCategoryListByRestaurantId(id: any) {
        return this._apiService.get(`category/list/by/restaurant/${id}`);
    }

    addProduct(data: any) {
        return this._apiService.post(data, 'product/add')
    }

    updateProduct(data: any, id: any) {
        return this._apiService.post(data, `product/update?id=${id}`);
    }
    isActiveProduct(id: any) {
        return this._apiService.post('', `product/active?id=${id}`)
    }
    isInactiveProduct(id: any) {
        return this._apiService.post('', `product/inactive?id=${id}`)
    }
}
