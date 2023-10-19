import { Injectable } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class ContactService {

  constructor(
    private _formBuilder: FormBuilder,
  ) { }

  createForm(): FormGroup {
    return this._formBuilder.group({
      query: ['', Validators.required],
      fullName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      mobileNumber: ['', [Validators.required, Validators.pattern('[0-9]*')]],
      text: ['', Validators.required],
    })
  }
}
