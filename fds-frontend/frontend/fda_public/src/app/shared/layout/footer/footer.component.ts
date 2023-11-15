import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent {
  isLoginPage: boolean = false;
  constructor(
    public router: Router,
  ) {
    router.events.subscribe((url: any) => {
      if (url && url.url) {
        //if (url.url.includes("signin") || url.url.includes("signup") || url.url.includes("partner")) {
        if (url.url.includes("session")) {
          this.isLoginPage = true;
        } else {
          this.isLoginPage = false;
        }
      }
    });
  }
}
