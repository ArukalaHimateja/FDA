import { Injectable } from "@angular/core";
import { ApiService } from "./api.service";

@Injectable({
    providedIn: 'root'
})
export class DashboardService {
    constructor (
        private _apiService: ApiService,
    ){
    }
    getDashboard(){
        return this._apiService.get('dashboard');
    }

}