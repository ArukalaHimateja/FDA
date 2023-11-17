import { Component } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { SessionService } from '../session.service';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.scss']
})
export class SigninComponent {
  signinForm: FormGroup;
  captcha!: string;
  isEmailVerify: boolean = false;

  constructor(
    private _sessionService: SessionService,
    private _activatedRoute: ActivatedRoute
  ) {
    if (_activatedRoute.snapshot.params['type'] && _activatedRoute.snapshot.params['verify']) {
      this.isEmailVerify = true;
    }
    this.signinForm = _sessionService.createForm();
  }

  ngOnInit() {
  }

  resolved(captchaRespose: string) {
    this.captcha = captchaRespose;
  }

  signin() {
    const data = this.signinForm.getRawValue();
    this._sessionService.login(data);
  }

  closeEmailNotification() {
    let htmlElement = document.getElementById("email-notification") as HTMLElement;
    if (htmlElement) {
      htmlElement.style.display = "none";
    }
  }

  forgotPassword(){
    if(this.signinForm.controls['username'].valid){
      const formData = new FormData();
      formData.append('email', this.signinForm.controls['username'].value);
      this._sessionService.forgotPassword(formData);
    }
  }
}
