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
                    link: '/user',
                    type: 'link'
                });
            }
            if (this.sessionUser.role === 0) {
                this.navbar.push({
                    title: 'Restaurant',
                    icon: 'restaurant',
                    link: '/restaurant',
                    type: 'link'
                });
            }
            if (this.sessionUser.role === 0) {
                this.navbar.push({
                    title: 'Promo Code',
                    icon: 'card_giftcard',
                    link: '/promo/code',
                    type: 'link'
                });
            }

            if (this.sessionUser.role === 2) {
                this.navbar.push({
                    title: 'Category',
                    icon: 'restaurant_menu',
                    link: '/category',
                    type: 'link'
                });
            }

            if (this.sessionUser.role === 2) {
                this.navbar.push({
                    title: 'Menu',
                    icon: 'restaurant_menu',
                    link: '/menu',
                    type: 'link'
                });
            }

            if (this.sessionUser.role === 2) {
                this.navbar.push({
                    title: 'Order',
                    icon: 'person',
                    link: '/order',
                    type: 'link'
                });
            }

            // if (this.sessionUser.role === 0) {
            //     this.navbar.push({
            //         title: 'Contact',
            //         icon: 'contacts',
            //         link: '/contact',
            //         type: 'link'
            //     });
            // }

            if (this.sessionUser.role === 0) {
                this.navbar.push({
                    title: 'Feedback',
                    icon: 'feedback',
                    link: '/feedback',
                    type: 'link'
                });
            }

            this.menuItems.next(this.navbar);
        }
    }
}
