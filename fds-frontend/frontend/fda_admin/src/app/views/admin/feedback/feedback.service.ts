import { Injectable } from "@angular/core";
import { ApiService } from "src/app/shared/services/api.service";

@Injectable({
    providedIn: 'root'
})
export class FeedbackService {

    displayedColumns = ['userId', 'userName', 'feedback', 'createdAt'];
    constructor(
        private _apiService: ApiService,
    ) { }
    getFeedbackListByFilterWithPagination(data: any) {
        return this._apiService.post(data, 'feedback/pagination/filter');
    }
}