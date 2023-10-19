import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { config } from '../config/config';

@Injectable({
    providedIn: 'root'
})
export class NavigationService {
    sessionUser: any;
    menuItems = new BehaviorSubject<any[]>([]);
    navbar: any[] = [];

    constructor(
    ) {
        this.setNavigation();
    }

    public setNavigation() {
        let user = localStorage.getItem(`${config.appShortName}User`);
        if (user) {
            this.sessionUser = JSON.parse(user);
            this.navbar = [];
            this.navbar.push({
                title: 'Dashboard',
                icon: 'dashboard',
                link: '/dashboard',
                type: 'link'
            });

            if (this.sessionUser.role === 0) {
                this.navbar.push({
                    title: 'Users',
                    icon: 'person',
                    link: '/user/list',
                    type: 'link'
                });
            }

            if (this.sessionUser.role === 0) {
                this.navbar.push({
                    title: 'Menu',
                    icon: 'person',
                    link: '/menu/list',
                    type: 'link'
                });
            }

            if (this.sessionUser.role === 0) {
                this.navbar.push({
                    title: 'Order',
                    icon: 'person',
                    link: '/order/list',
                    type: 'link'
                });
            }

            if (this.sessionUser.role === 0) {
                this.navbar.push({
                    title: 'Contact',
                    icon: 'person',
                    link: '/contact/list',
                    type: 'link'
                });
            }

            if (this.sessionUser.role === 0) {
                this.navbar.push({
                    title: 'Feedback',
                    icon: 'person',
                    link: '/feedback/list',
                    type: 'link'
                });
            }

            this.menuItems.next(this.navbar);
        }
    }
}
