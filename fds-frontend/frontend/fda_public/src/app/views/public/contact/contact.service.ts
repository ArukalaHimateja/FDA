import { Injectable } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ApiService } from 'src/app/shared/services/api.service';
import { UtilityService } from 'src/app/shared/services/utility.service';

@Injectable({
  providedIn: 'root'
})
export class ContactService {
  sessionUser: any;
  constructor(
    private _formBuilder: FormBuilder,
    private _apiService: ApiService,
    private _utilityService: UtilityService,
  ) {
    this.sessionUser = _utilityService.getAuthUser();
  }

  createForm(): FormGroup {
    return this._formBuilder.group({
      // query: ['', Validators.required],
      // fullName: ['', Validators.required],
      // email: ['', [Validators.required, Validators.email]],
      // mobileNumber: ['', [Validators.required, Validators.pattern('[0-9]*')]],
      text: ['', Validators.required],
    })
  }

  submitFeedback(data: any) {
    let json = {
      feedback: data.text,
      userId: this.sessionUser.id
    }
    this._apiService.post(json, 'feedback/add').then((response: any) => {
      if (response && response.body.status === 'OK') {
        this._utilityService.successMessage(response.body.message, response.body.status);
      } else {
        this._utilityService.errorMessage(response.body.message, response.body.status);
      }
    });
  }

  createReportForm(): FormGroup {
    return this._formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      name: ['', Validators.required],
      mobileNumber: ['', [Validators.required, Validators.pattern('[0-9]*')]],
      subject: ['', Validators.required],
      message: ['', Validators.required],
    })
  }

  submitReport(data: any){
    let json = {
      email: data.email,
      name: data.name,
      mobileNumber: data.mobileNumber,
      subject: data.subject,
      message: data.message
    }
    this._apiService.post(json, 'report/add').then((response: any) => {
      if (response && response.body.status === 'OK') {
        this._utilityService.successMessage(response.body.message, response.body.status);
      } else {
        this._utilityService.errorMessage(response.body.message, response.body.status);
      }
    });
    
  }
}
