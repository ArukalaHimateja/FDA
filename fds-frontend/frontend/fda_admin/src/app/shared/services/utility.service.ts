import { Injectable } from '@angular/core';
import { formatDate } from '@angular/common';
import { environment } from '../../../environments/environment.prod';
import { ConstantService } from './constant.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Pagination } from '../interfaces/pagination.interface';

@Injectable({
    providedIn: 'root'
})
export class UtilityService {

    apiUrl: string = environment.apiUrl;
    pagination: Pagination = { totalPages: 0, totalCount: 0, currentPage: 1, perPage: 25, data: [] };
    defaultUrlImage: string = '/assets/images/dummy/user.png';
    defaultUrlLogo: string = '/assets/images/dummy/logo.png';
    defaultUrlProduct: string = '/assets/images/dummy/product.png';

    constructor(
        private _constantService: ConstantService,
        private _matSnackBar: MatSnackBar
    ) { }

    /**
     * Get Formated Date Time
     * 
     * @param date 
     * 
     * @returns formated date
     */
    getFormatedDateTime(date: any): any {
        return date ? formatDate(date, 'MMM d, yyyy, HH:mm', 'en-US', '+0530') : '';
    }

    /**
     * Get Formated Date
     * 
     * @param date
     * 
     * @returns formated date 
     */
    getFormatedDate(date: any): string {
        return date ? formatDate(date, 'MMM d,yyyy', 'en-US', '+0530') : '';
    }

    /**
     * Success toastr
     * 
     * @param message 
     * @param status 
     */
    successMessage(message: string, status: any) {
        this._matSnackBar.open(message, status, {
            duration: 4000,
            verticalPosition: 'top',
            horizontalPosition: 'center',
        });
    }

    /**
    * Error toastr
    * 
    * @param message 
    * @param status 
    */
    errorMessage(message: string, status: any) {
        this._matSnackBar.open(message, status, {
            duration: 4000,
            verticalPosition: 'top',
            horizontalPosition: 'center',
        });
    }

    /**
       * Get File Url
       * 
       * @param file 
       * @param type 
       */
    getFileUrl(file: any, type?: any): any {
        let url = this.apiUrl + 'file/get/';
        if (file && file !== '') {
            url += file;
        } else if (type && type === 'user') {
            url = this.defaultUrlImage;
        } else if (type && type === 'logo') {
            url = this.defaultUrlLogo;
        } else if (type && type === 'product') {
            url = this.defaultUrlProduct;
        } else {
            url = this.defaultUrlImage;
        }
        return url;
    }
}
