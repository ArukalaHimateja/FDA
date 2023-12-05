import { Injectable } from '@angular/core';
import { ApiService } from '../../../shared/services/api.service';
import { environment } from 'src/environments/environment';

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
     * Get Restaurant data by id
     * 
     * @param id 
     */
    getRestaurantDtlByRequestId(id: any) {
        return this._apiService.get(`get/restaurant/request/${id}`);
    }


    downloadDocumentByRestaurantRequestId(id: any) {
        this._apiService.get(`get/restaurant/request/document/${id}`).then((response: any) => {
            // this._apiService.get(`file/download/${response.body.data.document}`);
            const data = response.body.data.document;
            // this.isLoading = false;
            const blob = new Blob([data], { type: 'application/zip' });
            const url = window.URL.createObjectURL(blob);
            const link = document.createElement('a');
            link.href = `${environment.apiUrl}file/download/${data}`;
            link.setAttribute('download', `${environment.apiUrl}/file/download/${data}`);
            document.body.appendChild(link);
            link.click();
        })
    }
}
