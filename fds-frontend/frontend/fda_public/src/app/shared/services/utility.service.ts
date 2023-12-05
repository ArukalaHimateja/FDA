import { Injectable } from '@angular/core';
import { formatDate } from '@angular/common';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Pagination } from '../shared/interfaces/pagination.interface';

@Injectable({
    providedIn: 'root'
})
export class UtilityService {
    pagination: Pagination = { totalPages: 0, totalCount: 0, currentPage: 1, perPage: 25, data: [] };
    defaultUrlImage: string = '/assets/images/dummy/user.png';
    defaultUrlLogo: string = '/assets/images/dummy/logo.png';
    defaultUrlProduct: string = '/assets/images/dummy/product.png';

    constructor(
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

    getAuthUser(): any {
        let user = localStorage.getItem("user");
        return user ? JSON.parse(user) : null;
    }

    success(message: any) {
        this._matSnackBar.open(message, 'OK', { duration: 5000, verticalPosition: 'top' });
    }
    error(message: any) {
        this._matSnackBar.open(message, 'OK', { duration: 5000, verticalPosition: 'top' });
    }
}
