import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { HttpErrorResponse, HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { ApiService } from './api.service';
import { config } from '../config/config';

@Injectable({
  providedIn: 'root'
})
export class FileUploadService {

  defaultUserImage: string = '/assets/images/dummy/user.png';
  defaultLogoImage: string = '/assets/images/dummy/logo.png';
  defaultBannerImage: string = '/assets/images/dummy/product.png';

  constructor(
    private _httpClient: HttpClient,
    private _apiService: ApiService
  ) { }

  /**
   * Upload Image
   * 
   * @param formData 
   */
  uploadImage(formData: any): Observable<any> {
    return this._httpClient.post(`${environment.apiUrl}file/upload`, formData, {
      reportProgress: true,
      observe: 'events'
    }).pipe(
      catchError(this.errorMgmt)
    )
  }

  /**
   * Error management
   * 
   * @param error 
   */
  errorMgmt(error: HttpErrorResponse) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // Get client-side error
      errorMessage = error.error.message;
    } else {
      // Get server-side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    console.log(errorMessage);
    return throwError(errorMessage);
  }

  /**
   * Delete File
   * 
   * */
  delete(file: any) {
    return new Promise((resolve, reject) => {
      this._httpClient.delete(`${environment.apiUrl}file/delete/${file}`)
        .subscribe((response: any) => {
          resolve(response);
        }, reject);
    });
  }

  /**
   * Upload multiple files
   * 
   * @param data 
   */
  uploadMultiple(data: any) {
    return this._apiService.post(data, 'file/uploadMultiple');
  }


  /**
   * Get image
   * 
   * @param type 
   * @param file 
   */
  getImage(type: any, file: any) {
    return this._apiService.get(`file/getImage/${type}/${file}`);
  }

  /**
   * Download file
   * 
   * @param type 
   * @param file 
   */
  downloadFile(type: any, file: any) {
    return this._apiService.get(`file/downloadFile/${type}/${file}`);
  }

  /**
  * Get File Url
  * 
  * @param file 
  * @param type 
  */
  getFileUrl(file: any, type?: any): any {
    let url = config.apiUrl + 'file/';
    if (file && file !== '') {
      url += file;
    } else if (type && type === 'user') {
      url = this.defaultUserImage;
    } else if (type && type === 'logo') {
      url = this.defaultLogoImage;
    } else if (type && type === 'banner') {
      url = this.defaultBannerImage;
    } else {
      url = this.defaultLogoImage;
    }
    return url;
  }
}
