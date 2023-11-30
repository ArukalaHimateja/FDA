import { Injectable } from '@angular/core';
import { ApiService } from '../../../shared/services/api.service';
import { FormBuilder, Validators } from '@angular/forms';
import { UtilityService } from 'src/app/shared/services/utility.service';
import { AuthService } from '../../session/auth.service';

@Injectable({
    providedIn: 'root'
})
export class CategoryService {

    displayedColumns: string[] = ['no', 'name', 'description', 'createdAt', 'action'];
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
    getCategoryListByRestaurantId(id: any) {
        return this._apiService.get(`category/list/by/restaurant/${id}`);
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

    createAddCategoryForm(element: any) {
        return this._formBuilder.group({
            name: [element? element.name : null, [Validators.required]],
            description: [element? element.description : null, [Validators.required]],
            image: [element? element.image : null],
        })
    }

    addCategory(data: any) {
        return this._apiService.post(data, 'category/add');
    }
    updateCategory(data: any, id: any) {
        return this._apiService.post(data, `category/update/${id}`);
    }
}
