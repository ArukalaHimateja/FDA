import { Injectable } from '@angular/core';
import { ApiService } from '../../../shared/services/api.service';

@Injectable({
    providedIn: 'root'
})
export class RestaurantService {

    displayedColumns: string[] = ['no', 'restaurantName', 'restaurantEmail', 'restaurantAddress', 'restaurantMobileNumber', 'createdAt', 'status', 'action'];

    /**
     * Constructor
     *
     * @param {ApiService} _apiService
     */
    constructor(
        private _apiService: ApiService
    ) { }

    /**
     * Get data list by filter with pagination
     * 
     * @param data 
     */
    getListByFilterWithPagination(data: any): any {
        return this._apiService.post(data, 'get/all/admin/restaurant/request/pagination/filter');
    }

    /**
     * Get data by id
     * 
     * @param id 
     */
    getDataById(id: any) {
        return this._apiService.get(`properties/${id}`);
    }

}
